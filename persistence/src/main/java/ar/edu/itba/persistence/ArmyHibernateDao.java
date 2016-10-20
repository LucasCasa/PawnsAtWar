package ar.edu.itba.persistence;

import java.util.List;

import ar.edu.itba.interfaces.ArmyDao;
import ar.edu.itba.model.Army;
import ar.edu.itba.model.Point;

public class ArmyHibernateDao implements ArmyDao {

	@Override
	public Army addArmy(Point position, int idPlayer, boolean available) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Army addArmy(Point position, int idPlayer) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Army> getArmiesByUserId(int userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Army getArmyById(int idArmy) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isAvailable(Point p) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean belongs(int userId, int idArmy) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean exists(Point p, int idPlayer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Army getArmy(Point p, int idPlayer) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setAvailable(int idArmy, boolean available) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteArmy(int idArmy) {
		// TODO Auto-generated method stub

	}

}
