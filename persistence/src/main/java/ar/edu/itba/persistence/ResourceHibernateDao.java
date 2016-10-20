package ar.edu.itba.persistence;

import java.util.List;

import ar.edu.itba.interfaces.ResourceDao;
import ar.edu.itba.model.Resource;

public class ResourceHibernateDao implements ResourceDao {

	@Override
	public void addAmount(int idPlayer, int type, int value) {
		// TODO Auto-generated method stub

	}

	@Override
	public void subtractAmount(int idPlayer, int type, int value) {
		// TODO Auto-generated method stub

	}

	@Override
	public Resource addResource(int idPlayer, int type, int amount) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Resource addResource(int idPlayer, int type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Resource> getResources(int idPlayer) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Resource getResource(int idPlayer, int type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setAmount(int idPlayer, int type, int value) {
		// TODO Auto-generated method stub

	}

}
