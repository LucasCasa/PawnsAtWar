package ar.edu.itba.persistence;

import java.sql.Timestamp;
import java.util.List;

import ar.edu.itba.interfaces.EmpireDao;
import ar.edu.itba.model.Building;
import ar.edu.itba.model.Resource;

public class EmpireHibernateDao implements EmpireDao {

	@Override
	public Timestamp getLastTimeUpdate(int userid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setLastTimeUpdate(int userid, Timestamp t) {
		// TODO Auto-generated method stub

	}

	@Override
	public Resource getResource(int userid, int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setResource(int userid, int id, int amount) {
		// TODO Auto-generated method stub

	}

	@Override
	public void addAmount(int userID, int id, int amount) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Resource> getResources(int userid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void substractAmount(int userid, int id, int amount) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Building> getBuilding(int userid, int type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void createEmpire(int userid, Timestamp timestamp) {
		// TODO Auto-generated method stub

	}

	@Override
	public void createResource(int userid, int type, int amount) {
		// TODO Auto-generated method stub

	}

}
