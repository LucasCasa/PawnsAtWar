package ar.edu.itba.service;

import java.util.ArrayList;
import java.util.List;

import ar.edu.itba.model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.itba.interfaces.BuildingDao;
<<<<<<< HEAD
import ar.edu.itba.interfaces.EmpireService;
=======
import ar.edu.itba.interfaces.BuildingService;
>>>>>>> d9433c89c73caca8960c804bdb6b8b63df0fe4cf
import ar.edu.itba.interfaces.SectorService;
import ar.edu.itba.interfaces.TerrainDao;
import ar.edu.itba.model.Point;
import ar.edu.itba.model.Sector;

@Service
public class SectorServiceImpl implements SectorService {
	
	public static final int CASTLE = 1;
	public static final int EMPTY = 0;
	public static final int ARCHERY = 2;
	public static final int BARRAKS = 3;
	public static final int GOLD =4;
	public static final int TERR_GOLD = 5;
	public static final int MILL = 6;
	public static final int BLACKSMITH = 7;
	
	public static final int maxX = 99;
	public static final int maxY = 99;
	
	public static final int initRange = 2;
	
	@Autowired
	BuildingDao bd;
	
	@Autowired
	TerrainDao td;
	
	@Autowired
	BuildingService bs;
	
	@Autowired
	EmpireService es;
	
	@Override
	public List<List<Sector>> getSector(Point p, int range) {
		int size = range*2 +1;
		Sector[][] aux = new Sector[size][size];
		List<List<Sector>> sectorList = new ArrayList<>(size);
		List<Sector> buildingList = bd.getBuildings(p, range);
		List<Sector> terrainList = td.getTerrain(p, range);

		for(Sector s:terrainList){
			if(s.getUser() == null){
				s.setUser(new User(-1,null,null,null));
			}
			aux[s.getPosition().getY() - (p.getY() - range)][s.getPosition().getX() - (p.getX() - range)] = s;
		}
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
<<<<<<< HEAD
		return bd.getBuilding(p);
=======
		Sector building = bd.getBuilding(p);
		Sector terrain = td.getTerrain(p);
		if(building == null && terrain == null){
			return new Sector(p,0,new User(0,null,null,null));
		}else if(building == null){
			return terrain;
		}
>>>>>>> d9433c89c73caca8960c804bdb6b8b63df0fe4cf

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
			es.deleteUser(b.getUser());
		}else if(b.getType() == GOLD){
			b.setType(TERR_GOLD);
		}else{
			b.setType(EMPTY);
		}
	}
	
<<<<<<< HEAD
	public void updateTerrain(Point p, User u,int range){
		List<Sector> listSector = bd.getBuildings(p, range);
		for(Sector s: listSector){
			s.setUser(u);
=======
	public void addBuilding(Point p, int idPlayer, int type){
		if(td.getId(p) == idPlayer){
			//td.deleteTerrain(p);
			bs.addBuilding(p, idPlayer, type);
		}
	}
	
	private void updateTerrain(Point p, Integer newOwner,int range){
		List<Sector> listSector = td.getTerrain(p, range);
		for(Sector s: listSector){
			td.setIdPlayer(s.getPosition(),newOwner);
>>>>>>> d9433c89c73caca8960c804bdb6b8b63df0fe4cf
		}
		
	}

	@Override
	public boolean isCastleAlone(Point p, int range) {
		if(range <= 0) 
			return false;
		return bd.isCastleAlone(p, range);
	}
<<<<<<< HEAD


	@Override
	public boolean createCastle(User u) {
		Point p = addCastle(u);
=======
	
	public void buildSector(Point p, int idPlayer, int type){
		if(type == EMPTY || type == TERR_GOLD){
			td.addTerrain(p, 0, idPlayer, type);
		}else{
			bs.addBuilding(p, idPlayer, type);
		}
	}

	@Override
	public boolean createCastle(int userid) {
		Point p = bs.addCastle(userid);
>>>>>>> d9433c89c73caca8960c804bdb6b8b63df0fe4cf
		if(p == null){
			return false;
		}
		updateTerrain(p,u,initRange);
		return true;
	}
<<<<<<< HEAD
	
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
		addBuilding(p,u,1);
		return p;
	}

	@Override
	public List<Sector> getAllBuildings(User u) {
		return bd.getBuildings(u);
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
	public int getPrice(Point point,User u) {
		return 1000-10*(getLevel(getCastle(u))-1);
	}

	
	public int getLevel(Point p) {
		return bd.getBuilding(p).getLevel();
	}
=======
>>>>>>> d9433c89c73caca8960c804bdb6b8b63df0fe4cf
}
