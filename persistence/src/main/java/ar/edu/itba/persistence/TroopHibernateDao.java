package ar.edu.itba.persistence;

import java.util.List;

import ar.edu.itba.interfaces.TroopDao;
import ar.edu.itba.model.Troop;

public class TroopHibernateDao implements TroopDao {

	@Override
	public int getAmount(int idArmy, int type) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<Troop> getAllTroop(int idArmy) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void changeAmount(int idArmy, int type, int amount) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteTroop(int idArmy, int type) {
		// TODO Auto-generated method stub

	}

	@Override
	public Troop addTroop(int idArmy, int type, int amount) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean exists(int idArmy, int type) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Troop getTroop(int idArmy, int type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getAmountTroops(int idArmy) {
		// TODO Auto-generated method stub
		return 0;
	}

}
