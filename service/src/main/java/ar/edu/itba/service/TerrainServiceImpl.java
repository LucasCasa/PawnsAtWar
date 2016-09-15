package ar.edu.itba.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.itba.interfaces.TerrainDao;
import ar.edu.itba.interfaces.TerrainService;
import ar.edu.itba.model.Point;
import ar.edu.itba.model.Terrain;

@org.springframework.stereotype.Component
public class TerrainServiceImpl implements TerrainService{
	

	@Autowired
	private TerrainDao terrainDao;

	@Override
	public Integer getPower(Point position) {
		return terrainDao.getPower(position);
	}

	@Override
	public Integer getType(Point position) {
		return terrainDao.getType(position);
	}

	@Override
	public List<List<Terrain>> getTerrain(Point p, int range) {
		int size = range*2+1;
		List<Terrain> list = terrainDao.getTerrain(p, range);
		Terrain[][] aux = new Terrain[size][size];

		for(Terrain t: list){
			aux[t.getPosition().getY() - (p.getY() - range)][t.getPosition().getX() - (p.getX() - range)] = t;
		}
		
		List<List<Terrain>> res = new ArrayList<>(size);
		
		
		for(int i=0; i<size; i++){
			res.add(new ArrayList<>(size));
			for(int j=0; j<size; j++){
				res.get(i).add(aux[i][j]);
			}
		}
		return res;
	}

	@Override
	public Terrain getTerrain(Point p) {
		return terrainDao.getTerrain(p);
	}

	@Override
	public Terrain setPower(Point p, int power) {
		return terrainDao.setPower(p, power);
	}

	@Override
	public Terrain setType(Point p, int t) {
		return terrainDao.setType(p, t);
	}

	@Override
	public Terrain addTerrain(Point p, int power, int t) {
		// TODO Auto-generated method stub
		return terrainDao.addTerrain(p, power, t);
	}

	@Override
	public Terrain addTerrain(Point p, int t) {
		// TODO Auto-generated method stub
		return terrainDao.addTerrain(p,t);
	}

	@Override
	public Terrain addTerrain(Point p) {
		// TODO Auto-generated method stub
		return terrainDao.addTerrain(p);
	}
}
