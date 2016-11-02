package ar.edu.itba.persistence;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;

import ar.edu.itba.interfaces.BuildingDao;
import ar.edu.itba.interfaces.CommerceDao;
import ar.edu.itba.interfaces.EmpireDao;
import ar.edu.itba.interfaces.ResourceDao;
import ar.edu.itba.interfaces.UserDao;
import ar.edu.itba.model.Building;
import ar.edu.itba.model.Empire;
import ar.edu.itba.model.Point;
import ar.edu.itba.model.Resource;
import ar.edu.itba.model.User;

public class EmpireHibernateDao implements EmpireDao {

	@PersistenceContext
    private EntityManager em;
	
	@Autowired
	private ResourceDao rd;
	
	@Autowired
	private BuildingDao bd;
	
	@Autowired
<<<<<<< HEAD
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
=======
	private UserDao ud;
	
	@Override
	public Timestamp getLastTimeUpdate(int userid) {
		final TypedQuery<Empire> query = em.createQuery("from Empire as e where e.idPlayer = :idPlayer",Empire.class);
		query.setParameter("idPlayer", userid);
		final List<Empire> list = query.getResultList();
		return list.isEmpty() ? null : list.get(0).getLastUpdate();
	}

	@Override
	public void setLastTimeUpdate(int userid, Timestamp t) {
		final Query query = em.createQuery("update Empire as e set e.lastUpdate = :lastUpdate where e.idPlayer = :idPlayer");
		query.setParameter("lastUpdate", t);
		query.setParameter("idPlayer", userid);
		query.executeUpdate();

>>>>>>> d9433c89c73caca8960c804bdb6b8b63df0fe4cf
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
<<<<<<< HEAD
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
		
=======
	public List<Building> getBuilding(int userid, int type) {
		return bd.getBuildings(userid,type);
	}

	@Override
	public void createEmpire(int userid, Timestamp timestamp) {
		User u = ud.findById(userid);
		Empire e = new Empire(u, timestamp);
		em.persist(e);

	}

	@Override
	public void createResource(int userid, int type, int amount) {
		rd.addResource(userid, type, amount);

>>>>>>> d9433c89c73caca8960c804bdb6b8b63df0fe4cf
	}

	@Override
	public void deleteOffers(User u) {
		cd.deleteOffers(u);
		
	}



}
