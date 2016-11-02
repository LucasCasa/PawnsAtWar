package ar.edu.itba.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.itba.interfaces.BuildingDao;
import ar.edu.itba.interfaces.SectorService;
import ar.edu.itba.interfaces.UserDao;
import ar.edu.itba.model.Point;
import ar.edu.itba.model.Sector;
import ar.edu.itba.model.User;

@Service
@Transactional
public class SectorServiceImpl implements SectorService {
	
	List<Point> availableSpots;
	private static final int RANGE = 6;
	private static final int MAXVALUE = 99;
	
	public static final int CASTLE = 1;
	public static final int EMPTY = 0;
	public static final int ARCHERY = 2;
	public static final int BARRAKS = 3;
	public static final int GOLD = 4;
	public static final int TERR_GOLD = 5;
	public static final int MILL = 6;
	public static final int BLACKSMITH = 7;
	
	public static final int maxX = 99;
	public static final int maxY = 99;
	
	public static final int initRange = 2;
	
	@Autowired
	BuildingDao bd;
	
	@Autowired
	UserDao ud;
	
	@Override
	public List<List<Sector>> getSector(Point p, int range) {
		int size = range*2 +1;
		Sector[][] aux = new Sector[size][size];
		List<List<Sector>> sectorList = new ArrayList<>(size);
		List<Sector> buildingList = bd.getBuildings(p, range);

		for(Sector s:buildingList){
				aux[s.getPosition().getY() - (p.getY() - range)][s.getPosition().getX() - (p.getX() - range)] = s;
		}
		for(int i=0; i<size; i++){
			sectorList.add(new ArrayList<>(size));
			for(int j=0; j<size; j++){
				sectorList.get(i).add(aux[i][j]);
			}
		}
		return sectorList;
	}

	@Override
	public Sector getSector(Point p) {
		if(p.getX()> maxX || p.getY()> maxY){
			return null;
		}
		return bd.getBuilding(p);

	}
	
	@Override
	public void deleteBuilding(Point p){
		if(p.getX() > maxX || p.getY() > maxY){
			return;
		}
		Sector b = bd.getBuilding(p);
		if(b == null){
			return;
		}
		b.setLevel(0);
		if(b.getType() == CASTLE){
			updateTerrain(p,null,0);
			updateTerrain(p,null,3);
		}else if(b.getType() == GOLD){
			b.setType(TERR_GOLD);
		}else{
			b.setType(EMPTY);
		}
	}
	
	private void updateTerrain(Point p, User u,int range){
		List<Sector> listSector = bd.getBuildings(p, range);
		for(Sector s: listSector){
			s.setUser(u);
		}
		
	}

	@Override
	public boolean isCastleAlone(Point p, int range) {
		if(range <= 0) 
			return false;
		return bd.isCastleAlone(p, range);
	}


	@Override
	public boolean createCastle(User u) {
		Point p = addCastle(u);
		if(p == null){
			return false;
		}
		updateTerrain(p,u,initRange);
		return true;
	}
	
	public void LoadSpots(){
		availableSpots = new ArrayList<Point>();
		boolean [][] aux = new boolean[MAXVALUE+1][MAXVALUE+1];
		List<Point> castles = bd.getAllCastles();
		for(Point p: castles){
			int minX = p.getX()-RANGE < 0 ? 0 : p.getX()-RANGE;
			int minY = p.getY()-RANGE < 0 ? 0 : p.getY()-RANGE;
			int maxX = p.getX()+RANGE > MAXVALUE ? MAXVALUE : p.getX() + RANGE;
			int maxY = p.getY()+RANGE > MAXVALUE ? MAXVALUE : p.getY() + RANGE;
			for(int i = minX; i <=maxX ; i++ ){
				for (int j = minY ; j<=maxY; j++ ){
					aux[i][j] = true;
				}
			}
		}
		for(int i = RANGE/2 ; i<=MAXVALUE-RANGE/2 ;i++){
			for (int j = RANGE/2 ;j<=MAXVALUE-RANGE/2;j++){
				if(!aux[i][j]){
					availableSpots.add(new Point(i,j));
				}
			}
		}
	}

	@Override
	public Integer getIdPlayer(Point p) {
		return bd.getIdPlayer(p);
	}

	@Override
	public void setIdPlayer(Point p,User u) {
		bd.setIdPlayer(p,u);
	}

	@Override
	public boolean belongsTo(Point p, User u) {
		return bd.belongsTo(p, u);
	}

	@Override
	public Point getCastle(User u){ 
		return bd.getCastle(u);
	}

	@Override
	public void levelUp(Point p) {
		bd.setLevel(p, bd.getLevel(p) + 1);
		
	}

	@Override
	public Point addCastle(User u) {
		LoadSpots();
		Random random = new Random();
		if(availableSpots.size() == 0){
			return null;
		}
		int n = random.nextInt(availableSpots.size());
		Point p = availableSpots.get(n);
		Sector s = bd.getBuilding(p);
		s.setUser(u);
		s.setType(1);
		s.setLevel(1);
		return p;
	}

	@Override
	public List<Sector> getAllBuildings(User u) {
		return u.getSector();
	}

	@Override
	public void addBuilding(Point p,User u, int type) {
		Sector s = bd.getBuilding(p);
		if(s.getType() != 5 || s.getType() != 0){
			s.setLevel(1);
		}
		s.setUser(u);
		s.setType(type);
	}

	@Override
	public int getType(Point p) {
		return bd.getType(p);
	}

	@Override
	public int getPrice(Point point,User u) {
		return 1000-10*(getLevel(getCastle(u))-1);
	}

	
	private int getLevel(Point p) {
		return bd.getBuilding(p).getLevel();
	}
}
