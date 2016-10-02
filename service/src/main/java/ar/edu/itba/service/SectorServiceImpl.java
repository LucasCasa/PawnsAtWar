package ar.edu.itba.service;

import java.util.ArrayList;
import java.util.List;

import ar.edu.itba.model.Terrain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.itba.interfaces.BuildingDao;
import ar.edu.itba.interfaces.SectorService;
import ar.edu.itba.interfaces.TerrainDao;
import ar.edu.itba.model.Point;
import ar.edu.itba.model.Sector;

@Service
public class SectorServiceImpl implements SectorService {
	
	public static final int maxX = 99;
	public static final int  maxY = 99;
	
	@Autowired
	BuildingDao bd;
	
	@Autowired
	TerrainDao td;
	
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
		Sector building = bd.getBuilding(p);
		Sector terrain = td.getTerrain(p);
		if(p.getX()> maxX || p.getY()> maxY){
			return null;
		}
		if(building == null && terrain == null){
			return new Sector(p,0,0);
		}else if(building == null){
			return terrain;
		}

		return building;
	}
	@Override
	public void deleteBuilding(Point p){
		bd.deleteBuilding(p);
	}
}
