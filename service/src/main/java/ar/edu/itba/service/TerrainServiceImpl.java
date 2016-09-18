package ar.edu.itba.service;


import org.springframework.beans.factory.annotation.Autowired;

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
