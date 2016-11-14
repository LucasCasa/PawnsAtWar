package ar.edu.itba.persistence;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import ar.edu.itba.interfaces.ArmyDao;

import ar.edu.itba.model.Army;
import ar.edu.itba.model.Point;
import ar.edu.itba.model.User;

@Repository
public class ArmyHibernateDao implements ArmyDao {

	@PersistenceContext
    private EntityManager em;

	
	@Override
	public Army addArmy(Point position, User u, boolean available) {
		Army a = new Army(position, u, available);
		em.persist(a);
		return a;
	}

	@Override
	public Army addArmy(Point position, User u) {
		return addArmy(position,u,true);
	}

	@Override
	public List<Army> getArmiesByUserId(User u) {
		final TypedQuery<Army> query = em.createQuery("from Army as a where a.userArmy = :u",Army.class); 
		query.setParameter("u",u);
		final List<Army> list = query.getResultList();
		return list;
	}

	@Override
	public Army getArmyById(int idArmy) {
		return em.find(Army.class, idArmy);
	}

	@Override
	public boolean isAvailable(Point p) {
		final TypedQuery<Army> query = em.createQuery("from Army as a where a.position = :p",Army.class); 
		query.setParameter("p",p);
		final List<Army> list = query.getResultList();
		return list.isEmpty() ? false : list.get(0).getAvailable();
	}

	@Override
	public boolean belongs(User u, int idArmy) {
		final TypedQuery<Army> query = em.createQuery("from Army where userArmy = :u and idArmy = :idArmy",Army.class); 
		query.setParameter("u", u);
		query.setParameter("idArmy", idArmy);
		final List<Army> list = query.getResultList();
		return list.isEmpty() ? false : true;
	}

	@Override
	public boolean exists(Point p, User u) {
		return getArmy(p,u) == null ? false : true;
	}

	@Override
	public Army getArmy(Point p, User u) {
		final TypedQuery<Army> query = em.createQuery("from Army where position = :p and userArmy = :u",Army.class); 
		query.setParameter("p",p);
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
