package ar.edu.itba.persistence;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;

import ar.edu.itba.interfaces.ArmyDao;
import ar.edu.itba.interfaces.TroopDao;
import ar.edu.itba.interfaces.UserDao;
import ar.edu.itba.model.Army;
import ar.edu.itba.model.Point;
import ar.edu.itba.model.User;

public class ArmyHibernateDao implements ArmyDao {

	@PersistenceContext
    private EntityManager em;
	
	@Autowired
	private UserDao ud;
	
	@Autowired
	private TroopDao td;
	
	@Override
	public Army addArmy(Point position, int idPlayer, boolean available) {
		User u = ud.findById(idPlayer);
		Army a = new Army(position, u, available, null);
		em.persist(a);
		return a;
	}

	@Override
	public Army addArmy(Point position, int idPlayer) {
		return addArmy(position,idPlayer,true);
	}

	@Override
	public List<Army> getArmiesByUserId(int userId) {
		final TypedQuery<Army> query = em.createQuery("from Army as a where idPlayer = :idPlayer",Army.class); 
		query.setParameter("idPlayer", userId);
		final List<Army> list = query.getResultList();
		return list;
	}

	@Override
	public Army getArmyById(int idArmy) {
		return em.find(Army.class, idArmy);
	}

	@Override
	public boolean isAvailable(Point p) {
		final TypedQuery<Army> query = em.createQuery("from Army as a where x = :x and y = :y",Army.class); 
		query.setParameter("x", p.getX());
		query.setParameter("y", p.getY());
		final List<Army> list = query.getResultList();
		return list.isEmpty() ? false : list.get(0).getAvailable();
	}

	@Override
	public boolean belongs(int userId, int idArmy) {
		final TypedQuery<Army> query = em.createQuery("from Army as a where idPlayer = :idPlayer and idArmy = :idArmy",Army.class); 
		query.setParameter("idPlayer", userId);
		query.setParameter("idArmy", idArmy);
		final List<Army> list = query.getResultList();
		return list.isEmpty() ? false : true;
	}

	@Override
	public boolean exists(Point p, int idPlayer) {
		return getArmy(p,idPlayer) == null ? false : true;
	}

	@Override
	public Army getArmy(Point p, int idPlayer) {
		final TypedQuery<Army> query = em.createQuery("from Army as a where x = :x and y = :y and idPlayer = :idPlayer",Army.class); 
		query.setParameter("x", p.getX());
		query.setParameter("y", p.getY());
		query.setParameter("idPlayer", idPlayer);
		final List<Army> list = query.getResultList();
		return list.isEmpty() ? null : list.get(0);
	}

	@Override
	public void setAvailable(int idArmy, boolean available) {
		final Query query = em.createQuery("update Army set available = :available where idArmy = :idArmy");
		query.setParameter("available", available);
		query.setParameter("idArmy", idArmy);
		query.executeUpdate();
	}

	@Override
	public void deleteArmy(int idArmy) {
		final Query query = em.createQuery("delete Army where idArmy = :idArmy");
		query.setParameter("idArmy", idArmy);
		query.executeUpdate();

	}

}
