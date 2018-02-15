package ar.edu.itba.service;

import java.util.*;

import ar.edu.itba.interfaces.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.itba.model.Point;
import ar.edu.itba.model.Sector;
import ar.edu.itba.model.User;

@Service
@Transactional
public class SectorServiceImpl implements SectorService {

  private List<Point> availableSpots;
  private static final int RANGE = 6;
  private static final int MAXVALUE = 99;

  static final int CASTLE = 1;
  static final int EMPTY = 0;
  static final int ARCHERY = 2;
  static final int BARRAKS = 3;
  static final int GOLD = 4;
  static final int TERR_GOLD = 5;
  static final int MILL = 6;
  static final int BLACKSMITH = 7;

  private static final int maxX = 99;
  private static final int maxY = 99;

  static final int initRange = 2;

  private static final int buildingPrice = 1000;
  private static final int castlePriceScaleFactor = 25000;

  @Autowired
  private BuildingDao bd;

  @Autowired
  private EmpireService es;

  @Override
  public List<List<Sector>> getSector(Point p, int range) {
    int size = range * 2 + 1;
    Sector[][] aux = new Sector[size][size];
    List<List<Sector>> sectorList = new ArrayList<>(size);
    List<Sector> buildingList = bd.getBuildings(p, range);

    for (Sector s : buildingList) {
      aux[s.getPosition().getY() - (p.getY() - range)][s.getPosition().getX() - (p.getX() - range)] = s;
    }
    for (int i = 0; i < size; i++) {
      sectorList.add(new ArrayList<>(size));
      for (int j = 0; j < size; j++) {
        sectorList.get(i).add(aux[i][j]);
      }
    }
    return sectorList;
  }

  @Override
  public Sector getSector(Point p) {
    if (p.getX() > maxX || p.getY() > maxY) {
      return null;
    }
    return bd.getBuilding(p);

  }

  @Override
  public void deleteBuilding(Point p) {
    if (p.getX() > maxX || p.getY() > maxY) {
      return;
    }
    Sector b = bd.getBuilding(p);
    if (b == null) {
      return;
    }
    User owner = b.getUser();
    b.setLevel(0);
    if (b.getType() == CASTLE) {
      b.setType(EMPTY);
      updateTerrain(p, null, initRange);
      if (es.getBuilding(owner, CASTLE).size() <= 0)
        es.deleteUser(owner);
    } else if (b.getType() == GOLD) {
      b.setType(TERR_GOLD);
    } else {
      b.setType(EMPTY);
    }
  }

  public void updateTerrain(Point p, User u, int range) {
    List<Sector> listSector = bd.getBuildings(p, range);
    for (Sector s : listSector) {
      s.setUser(u);
    }

  }

  @Override
  public boolean isCastleAlone(Point p, int range) {
    return range > 0 && bd.isCastleAlone(p, range);
  }


  @Override
  public boolean createCastle(User u) {
    Point p = addCastle(u);
    if (p == null) {
      return false;
    }
    updateTerrain(p, u, initRange);
    return true;
  }

  public void LoadSpots() {
    availableSpots = new ArrayList<>();
    boolean[][] aux = new boolean[MAXVALUE + 1][MAXVALUE + 1];
    List<Point> castles = bd.getAllCastles();
    for (Point p : castles) {
      int minX = p.getX() - RANGE < 0 ? 0 : p.getX() - RANGE;
      int minY = p.getY() - RANGE < 0 ? 0 : p.getY() - RANGE;
      int maxX = p.getX() + RANGE > MAXVALUE ? MAXVALUE : p.getX() + RANGE;
      int maxY = p.getY() + RANGE > MAXVALUE ? MAXVALUE : p.getY() + RANGE;
      for (int i = minX; i <= maxX; i++) {
        for (int j = minY; j <= maxY; j++) {
          aux[i][j] = true;
        }
      }
    }
    for (int i = RANGE / 2; i <= MAXVALUE - RANGE / 2; i++) {
      for (int j = RANGE / 2; j <= MAXVALUE - RANGE / 2; j++) {
        if (!aux[i][j]) {
          availableSpots.add(new Point(i, j));
        }
      }
    }
  }

  @Override
  public Integer getIdPlayer(Point p) {
    return bd.getIdPlayer(p);
  }

  @Override
  public void setIdPlayer(Point p, User u) {
    bd.setIdPlayer(p, u);
  }

  @Override
  public Point getCastle(User u) {
    return bd.getCastle(u);
  }

  @Override
  public List<Point> getAllCastles(User u) {
    return bd.getAllCastles(u);
  }

  @Override
  public void levelUp(Point p) {
    if (bd.getLevel(p) < 20) {
      bd.setLevel(p, bd.getLevel(p) + 1);
    }
  }

  @Override
  public Point addCastle(User u) {
    LoadSpots();
    Random random = new Random();
    if (availableSpots.size() == 0) {
      return null;
    }
    int n = random.nextInt(availableSpots.size());
    Point p = availableSpots.get(n);
    addBuilding(p, u, 1);
    return p;
  }

  @Override
  public List<Sector> getAllBuildings(User u) {
    return bd.getBuildings(u);
  }

  @Override
  public void addBuilding(Point p, User u, int type) {
    Sector s = bd.getBuilding(p);
    if (s.getType() != 5 || s.getType() != 0) {
      s.setLevel(1);
    }
    s.setUser(u);
    s.setType(type);
  }

  @Override
  public int getPrice(User u) {
    return buildingPrice;
  }

  public User getPlayer(Point pos) {
    return bd.getPlayer(pos);
  }

  public int getLevel(Point p) {
    return bd.getBuilding(p).getLevel();
  }

  @Override
  public int getCastlePrice(User u) {
    int num = bd.getBuildings(u, CASTLE).size();
    return num * castlePriceScaleFactor;
  }

  @Override
  public List<Point> getAvailableSpots() {
    LoadSpots();
    return availableSpots;
  }
}
