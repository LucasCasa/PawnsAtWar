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
	public Timestamp getLastTimeUpdate(int userid) {
		final TypedQuery<Empire> query = em.createQuery("from Empire where userEmpire.id = :idPlayer",Empire.class);
		query.setParameter("idPlayer", userid);
		final List<Empire> list = query.getResultList();
		return list.isEmpty() ? null : list.get(0).getLastUpdate();
	}

	@Override
	public void setLastTimeUpdate(int userid, Timestamp t) {
		final Query query = em.createQuery("update Empire set lastUpdate = :lastUpdate where userEmpire.id = :idPlayer");
		query.setParameter("lastUpdate", t);
		query.setParameter("idPlayer", userid);
		query.executeUpdate();
	}

	@Override
	public Resource getResource(int userid, int id) {
		return rd.getResource(userid, id);
	}

	@Override
	public void setResource(int userid, int id, int amount) {
		rd.setAmount(userid, id, amount);
	}
	
	@Override
	public List<Resource> getResources(int userid){
		return rd.getResources(userid);
	}

	@Override
	public void substractAmount(int userid, int id, int amount) {
		rd.subtractAmount(userid, id, amount);
	}

	@Override
	public List<Sector> getBuilding(int userid, int type) {
		return bd.getBuildings(userid,type);
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
	public void addAmount(int userID, int id, int amount) {
		rd.addAmount(userID, id, amount);
		
	}

}
