package ar.edu.itba.service;

import java.util.ArrayList;
import java.util.List;

import ar.edu.itba.model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.itba.interfaces.BuildingDao;
import ar.edu.itba.interfaces.BuildingService;
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
	TerrainDao td;
	
	@Autowired
	BuildingService bs;
	
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
		Sector building = bd.getBuilding(p);
		Sector terrain = td.getTerrain(p);
		if(building == null && terrain == null){
			return new Sector(p,0,new User(0,null,null,null));
		}else if(building == null){
			return terrain;
		}

		return building;
	}
	
	@Override
	public void deleteBuilding(Point p){
		if(p.getX() > maxX || p.getY() > maxY){
			return;
		}
		Sector b = bd.getBuilding(p);
		bd.deleteBuilding(p);
		if(b.getType() == CASTLE){
			//td.addTerrain(p);
			updateTerrain(p,null,0);
			updateTerrain(p,null,3);
		}else if(b.getType() == GOLD){
			//td.addTerrain(p, 1, b.getUser().getId(),TERR_GOLD);

		}else{
			//td.addTerrain(p, 1, b.getUser().getId(), EMPTY);

		}
	}
	
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
		}
		
	}

	@Override
	public boolean isCastleAlone(Point p, int range) {
		if(range <= 0) 
			return false;
		return bd.isCastleAlone(p, range);
	}
	
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
		if(p == null){
			return false;
		}
		updateTerrain(p,userid,initRange);
		return true;
	}
}
