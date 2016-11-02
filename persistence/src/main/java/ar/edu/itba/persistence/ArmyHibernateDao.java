package ar.edu.itba.persistence;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

<<<<<<< HEAD
import org.springframework.stereotype.Repository;

import ar.edu.itba.interfaces.ArmyDao;
=======
import org.springframework.beans.factory.annotation.Autowired;

import ar.edu.itba.interfaces.ArmyDao;
import ar.edu.itba.interfaces.TroopDao;
import ar.edu.itba.interfaces.UserDao;
>>>>>>> d9433c89c73caca8960c804bdb6b8b63df0fe4cf
import ar.edu.itba.model.Army;
import ar.edu.itba.model.Point;
import ar.edu.itba.model.User;

public class ArmyHibernateDao implements ArmyDao {

	@PersistenceContext
    private EntityManager em;
<<<<<<< HEAD

	
	@Override
	public Army addArmy(Point position, User u, boolean available) {
		Army a = new Army(position, u, available);
=======
	
	@Autowired
	private UserDao ud;
	
	@Autowired
	private TroopDao td;
	
	@Override
	public Army addArmy(Point position, int idPlayer, boolean available) {
		User u = ud.findById(idPlayer);
		Army a = new Army(position, u, available, null);
>>>>>>> d9433c89c73caca8960c804bdb6b8b63df0fe4cf
		em.persist(a);
		return a;
	}

	@Override
	public Army addArmy(Point position, User u) {
		return addArmy(position,u,true);
	}

	@Override
<<<<<<< HEAD
	public List<Army> getArmiesByUserId(User u) {
		final TypedQuery<Army> query = em.createQuery("from Army as a where a.userArmy = :u",Army.class); 
		query.setParameter("u",u);
=======
	public List<Army> getArmiesByUserId(int userId) {
		final TypedQuery<Army> query = em.createQuery("from Army as a where idPlayer = :idPlayer",Army.class); 
		query.setParameter("idPlayer", userId);
>>>>>>> d9433c89c73caca8960c804bdb6b8b63df0fe4cf
		final List<Army> list = query.getResultList();
		return list;
	}

	@Override
	public Army getArmyById(int idArmy) {
		return em.find(Army.class, idArmy);
	}

	@Override
	public boolean isAvailable(Point p) {
<<<<<<< HEAD
		final TypedQuery<Army> query = em.createQuery("from Army as a where a.position = :p",Army.class); 
		query.setParameter("p",p);
=======
		final TypedQuery<Army> query = em.createQuery("from Army as a where x = :x and y = :y",Army.class); 
		query.setParameter("x", p.getX());
		query.setParameter("y", p.getY());
>>>>>>> d9433c89c73caca8960c804bdb6b8b63df0fe4cf
		final List<Army> list = query.getResultList();
		return list.isEmpty() ? false : list.get(0).getAvailable();
	}

	@Override
<<<<<<< HEAD
	public boolean belongs(User u, int idArmy) {
		final TypedQuery<Army> query = em.createQuery("from Army as a where userArmy = :u and idArmy = :idArmy",Army.class); 
		query.setParameter("u", u);
=======
	public boolean belongs(int userId, int idArmy) {
		final TypedQuery<Army> query = em.createQuery("from Army as a where idPlayer = :idPlayer and idArmy = :idArmy",Army.class); 
		query.setParameter("idPlayer", userId);
>>>>>>> d9433c89c73caca8960c804bdb6b8b63df0fe4cf
		query.setParameter("idArmy", idArmy);
		final List<Army> list = query.getResultList();
		return list.isEmpty() ? false : true;
	}

	@Override
	public boolean exists(Point p, User u) {
		return getArmy(p,u) == null ? false : true;
	}

	@Override
<<<<<<< HEAD
	public Army getArmy(Point p, User u) {
		final TypedQuery<Army> query = em.createQuery("from Army as a where position.x = :x and position.y = :y and userArmy = :u",Army.class); 
=======
	public Army getArmy(Point p, int idPlayer) {
		final TypedQuery<Army> query = em.createQuery("from Army as a where x = :x and y = :y and idPlayer = :idPlayer",Army.class); 
>>>>>>> d9433c89c73caca8960c804bdb6b8b63df0fe4cf
		query.setParameter("x", p.getX());
		query.setParameter("y", p.getY());
		query.setParameter("u", u);
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
