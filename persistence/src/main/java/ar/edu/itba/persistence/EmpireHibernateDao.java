package ar.edu.itba.persistence;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ar.edu.itba.interfaces.BuildingDao;
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
	
	@Override
	public Timestamp getLastTimeUpdate(User u) {
		final TypedQuery<Empire> query = em.createQuery("from Empire where userEmpire = :user",Empire.class);
		query.setParameter("user", u);
		final List<Empire> list = query.getResultList();
		return list.isEmpty() ? null : list.get(0).getLastUpdate();
	}

	@Override
	public void setLastTimeUpdate(User u, Timestamp t) {
		final Query query = em.createQuery("update Empire set lastUpdate = :lastUpdate where userEmpire = :user");
		query.setParameter("lastUpdate", t);
		query.setParameter("user", u);
		query.executeUpdate();
	}

	@Override
	public Resource getResource(User u, int id) {
		return rd.getResource(u, id);
	}

	@Override
	public void setResource(User u, int id, int amount) {
		rd.setAmount(u, id, amount);
	}
	
	@Override
	public List<Resource> getResources(User u){
		return u.getResources();
	}

	@Override
	public void substractAmount(User u, int id, int amount) {
		rd.subtractAmount(u, id, amount);
	}

	@Override
	public List<Sector> getBuilding(Point p, int type) {
		return bd.getBuildings(p,type);
	}

	@Override
	public void createEmpire(User u, Timestamp timestamp) {
		Empire e = new Empire(u, timestamp);
		em.persist(e);

	}

	@Override
	public void createResource(User user, int type, int amount) {
		rd.addResource(user, type, amount);

	}

	@Override
	public void addAmount(User u, int id, int amount) {
		rd.addAmount(u, id, amount);
		
	}

	@Override
	public List<Sector> getBuilding(User u, int type) {
		return bd.getBuildings(u, type);
	}


}
