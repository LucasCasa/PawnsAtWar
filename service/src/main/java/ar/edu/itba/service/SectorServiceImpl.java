package ar.edu.itba.service;

import java.util.ArrayList;
import java.util.List;

import ar.edu.itba.model.Terrain;
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
	
	//no esta bien hecho.
	public static final int CASTLE = 1;
	public static final int EMPTY = 0;
	public static final int ARCHERY = 2;
	public static final int BARRAKS = 3;
	public static final int GOLD = 4;
	public static final int TERR_GOLD = 5;
	public static final int MILL = 6;
	public static final int BLACKSMITH = 7;
	
	public static final int maxX = 99;
	public static final int  maxY = 99;
	
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
			aux[s.getPosition().getY() - (p.getY() - range)][s.getPosition().getX() - (p.getX() - range)] = s;
		}
		for(Sector s:buildingList){
				aux[s.getPosition().getY() - (p.getY() - range)][s.getPosition().getX() - (p.getX() - range)] = s;
		}
		for(int i = 0; i< size;i++){
			for(int j = 0; j<size ;j++){
				if(aux[i][j] == null){
					aux[i][j] = new Terrain(new Point(p.getX() - range + j, p.getY() - range + i),0 ,0,0);
				}
			}
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
			return new Sector(p,0,0);
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
			td.addTerrain(p);
			updateTerrain(p);
		}else if(b.getType() == GOLD){
			td.addTerrain(p, 1, b.getIdPlayer(),TERR_GOLD);
		}else{
			td.addTerrain(p, 1, b.getIdPlayer(), EMPTY);
		}
	}
	
	private void updateTerrain(Point p){
		List<Sector> listSector = td.getTerrain(p, 3);
		for(Sector s: listSector){
			td.setIdPlayer(s.getPosition(),null);
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
			bd.addBuilding(p, idPlayer, type);
		}
	}

	@Override
	public void createCastle(int userid) {
		bs.addCastle(userid);
	}
}
