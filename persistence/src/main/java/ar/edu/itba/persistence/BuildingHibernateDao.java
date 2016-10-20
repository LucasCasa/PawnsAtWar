package ar.edu.itba.persistence;

import java.util.List;

import ar.edu.itba.interfaces.BuildingDao;
import ar.edu.itba.model.Building;
import ar.edu.itba.model.Point;
import ar.edu.itba.model.Sector;

public class BuildingHibernateDao implements BuildingDao {

	@Override
	public Integer getLevel(Point p) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setLevel(Point p, int level) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Sector> getBuildings(Point p, int range) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Sector getBuilding(Point p) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer getIdPlayer(Point p) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setIdPlayer(Point p, int idPlayer) {
		// TODO Auto-generated method stub

	}

	@Override
	public Building addBuilding(Point p, int level, int idPlayer, int type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Building addBuilding(Point p, int idPlayer, int type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean belongsTo(Point p, int idPlayer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void deleteBuilding(Point p) {
		// TODO Auto-generated method stub

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
	public Point getCastle(int idPlayer) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isCastleAlone(Point p, int range) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Building> getBuildings(int userId, int type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Point> getAllCastles() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Building> getBuildings(int idPlayer) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getType(Point p) {
		// TODO Auto-generated method stub
		return 0;
	}

}
