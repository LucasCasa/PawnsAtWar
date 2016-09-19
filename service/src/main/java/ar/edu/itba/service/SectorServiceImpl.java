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
	
	@Autowired
	BuildingDao bd;
	
	@Autowired
	TerrainDao td;
	
	@Override
	public List<List<Sector>> getSector(Point p, int range) {
		/*	return getSector(new Point(p.getX()+range,p.getY()+range),range);
		}else if( p.getY()-range < 0 ){
			return getSector(new Point(p.getX(),p.getY()+range),range);
		}else if(p.getX()-range < 0){
			return getSector(new Point(p.getX()+range,p.getY()),range);
		}else if(p.getX()+range > td.getMaxX() && p.getY()+range > td.getMaxY()){
			return getSector(new Point(p.getX()-range,p.getY()-range),range);
		}else if(p.getY()+range > td.getMaxY()){
			return getSector(new Point(p.getX(),p.getY()-range),range);
		}else if(p.getX()+range > td.getMaxX()){
			return getSector(new Point(p.getX()-range,p.getY()),range);
		}
		*/
		int size = range*2 +1;
		Sector[][] aux = new Sector[size][size];
		List<List<Sector>> sectorList = new ArrayList<>(size);
		List<Sector> buildingList = bd.getBuildings(p, range);
		List<Sector> terrainList = td.getTerrain(p, range);
		System.out.println(buildingList);
		System.out.println(terrainList);
		for(Sector s:terrainList){
			aux[s.getPosition().getY() - (p.getY() - range)][s.getPosition().getX() - (p.getX() - range)] = s;
			System.out.println("Cargue terrains");
		}
		for(Sector s:buildingList){
				aux[s.getPosition().getY() - (p.getY() - range)][s.getPosition().getX() - (p.getX() - range)] = s;
				System.out.println("Cargue Building");
		}
		for(int i = 0; i< size;i++){
			for(int j = 0; j<size ;j++){
				if(aux[i][j] == null){
					aux[i][j] = new Terrain(new Point(p.getX() - range + j, p.getY() - range + i),0,0);
				}
			}
		}
		//validar a futuro
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
		
		//Selecciona el maximo valor de las dos tablas
		int maxBuildingY = bd.getMaxY();
		int maxTerrainY = td.getMaxY();
		int max_valueY = maxBuildingY > maxTerrainY ? maxBuildingY : maxTerrainY;
		int maxBuildingX = bd.getMaxX();
		int maxTerrainX = td.getMaxX();
		int max_valueX = maxBuildingX > maxTerrainX ? maxBuildingX : maxTerrainX; 
		if(building == null && terrain == null){
			//A continuacion se muestran todos los casos para poder redirigir si el usuario ingresa algo que no deberia
			if(p.getX() < 0 && p.getY() < 0){
				return getSector(new Point(0,0));
			}else if(p.getX() < 0){
				return getSector(new Point(0,p.getY()));
			}else if(p.getY() < 0){
				return getSector(new Point(p.getX(),0));
			}else if(p.getX() >max_valueX && p.getY() > max_valueY){
				return getSector(new Point(max_valueX,max_valueY));
			}else if(p.getX() > max_valueX){
				return getSector(new Point(max_valueX,p.getY()));
			}else if(p.getY() > max_valueY){
				
				return getSector(new Point(p.getX(),max_valueY));
			}
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
