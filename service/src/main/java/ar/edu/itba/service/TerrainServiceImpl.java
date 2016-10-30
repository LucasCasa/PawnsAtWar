package ar.edu.itba.service;


import org.springframework.beans.factory.annotation.Autowired;

import ar.edu.itba.interfaces.TerrainDao;
import ar.edu.itba.interfaces.TerrainService;
import ar.edu.itba.model.Point;
import ar.edu.itba.model.Terrain;


@org.springframework.stereotype.Component
public class TerrainServiceImpl implements TerrainService{
	

	@Autowired
	private TerrainDao td;

	@Override
	public Integer getPower(Point position) {
		return td.getPower(position);
	}

	@Override
	public Integer getType(Point position) {
		return td.getType(position);
	}

	@Override
	public void setPower(Point p, int power) {
		td.setPower(p, power);
	}

	@Override
	public void setType(Point p, int t) {
		td.setType(p, t);
	}

	@Override
	public Terrain addTerrain(Point p, int power, int t,int idPlayer) {
		if(td.getTerrain(p) != null)
			return null;
		return td.addTerrain(p, power, t,idPlayer);
	}

	@Override
	public Terrain addTerrain(Point p, int t) {
		return addTerrain(p,0,t,-1);
	}

	@Override
	public Terrain addTerrain(Point p) {
		return addTerrain(p,0,0,-1);
	}
	
	public int getUserId(Point p){
		return td.getId(p);
	}
}
