package ar.edu.itba.persistence;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ar.edu.itba.interfaces.BuildingDao;
import ar.edu.itba.interfaces.CommerceDao;
import ar.edu.itba.interfaces.EmpireDao;
import ar.edu.itba.interfaces.ResourceDao;
import ar.edu.itba.model.Empire;
import ar.edu.itba.model.Point;
import ar.edu.itba.model.Resource;
import ar.edu.itba.model.Sector;
import ar.edu.itba.model.User;

@Repository
public class EmpireHibernateDao implements EmpireDao {

	@PersistenceContext
    private EntityManager em;
	
	@Autowired
	private ResourceDao rd;
	
	@Autowired
	private BuildingDao bd;
	
	@Autowired
	private CommerceDao cd;


	@Override
	public Resource getResource(User u, int id) {
		return rd.getResource(u, id);
	}

	@Override
	public void setResource(User u, int id, int amount) {
		Resource r = getResource(u,id);
		r.setQuantity(amount);
		em.merge(r);
	}


	@Override
	public List<Sector> getBuilding(Point p, int type) {
		return bd.getBuildings(p,type);
	}

	@Override
	public Empire createEmpire(User u, Timestamp timestamp) {
		Empire e = new Empire(u, timestamp);
		em.persist(e);
		return e;
	}

	@Override
	public void createResource(User user, int type, int amount) {
		rd.addResource(user, type, amount);

	}

	@Override
	public List<Sector> getBuilding(User u, int type) {
		return bd.getBuildings(u, type);
	}

	@Override
	public void setLastUpdate(User u, Timestamp currentTime) {
		u.getEmpire().setLastUpdate(currentTime);
		em.merge(u.getEmpire());
	}

	@Override
	public Empire getByUser(User u) {
		return u.getEmpire();
	}

	@Override
	public void deleteResource(User u, int type) {
		rd.deleteResource(u,type);
		
	}

	@Override
	public void deleteOffers(User u) {
		cd.deleteOffers(u);
		
	}



}
