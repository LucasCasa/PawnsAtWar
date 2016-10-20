package ar.edu.itba.persistence;

import java.util.List;

import ar.edu.itba.interfaces.TerrainDao;
import ar.edu.itba.model.Point;
import ar.edu.itba.model.Sector;
import ar.edu.itba.model.Terrain;

public class TerrainHibernateDao implements TerrainDao {

	@Override
	public Integer getPower(Point position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer getType(Point position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Sector> getTerrain(Point p, int range) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Sector getTerrain(Point p) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setPower(Point p, int power) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setType(Point p, int t) {
		// TODO Auto-generated method stub

	}

	@Override
	public Terrain addTerrain(Point p, int power, int idPlayer, int t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Terrain addTerrain(Point p, int t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Terrain addTerrain(Point p) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getMaxX() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getMaxY() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Integer getId(Point p) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setIdPlayer(Point p, Integer idPlayer) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteTerrain(Point p) {
		// TODO Auto-generated method stub

	}

}
