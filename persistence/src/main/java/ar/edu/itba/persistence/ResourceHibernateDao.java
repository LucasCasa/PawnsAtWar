package ar.edu.itba.persistence;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

<<<<<<< HEAD
import org.springframework.stereotype.Repository;
=======
import org.springframework.beans.factory.annotation.Autowired;
>>>>>>> d9433c89c73caca8960c804bdb6b8b63df0fe4cf

import ar.edu.itba.interfaces.ResourceDao;
import ar.edu.itba.model.Resource;
import ar.edu.itba.model.User;

public class ResourceHibernateDao implements ResourceDao {
	
	@PersistenceContext
    private EntityManager em;


	@Override
<<<<<<< HEAD
	public void addAmount(User u, int type, int value) {
		Resource amount = getResource(u,type);
		amount.setQuantity(value + amount.getQuantity());
=======
	public void addAmount(int idPlayer, int type, int value) {
		Resource amount = getResource(idPlayer,type);
		final Query query = em.createQuery("update Resource set amount = :amount where idPlayer = :idPlayer and type = :type");
		query.setParameter("amount", amount.getQuantity() + value);
		query.setParameter("idPlayer", idPlayer);
		query.setParameter("type", type);
		query.executeUpdate();
>>>>>>> d9433c89c73caca8960c804bdb6b8b63df0fe4cf

	}

	@Override
	public void subtractAmount(User u, int type, int value) {
		Resource amount = getResource(u,type);
		int cant = amount.getQuantity() <= value ? 0 : amount.getQuantity() - value;
<<<<<<< HEAD
		amount.setQuantity(cant);
=======
		final Query query = em.createQuery("update Resource set amount = :amount where idPlayer = :idPlayer and type = :type");
		query.setParameter("amount", cant);
		query.setParameter("idPlayer", idPlayer);
		query.setParameter("type", type);
		query.executeUpdate();
>>>>>>> d9433c89c73caca8960c804bdb6b8b63df0fe4cf

	}

	@Override
	public Resource addResource(int idPlayer, int type, int amount) {
		User u = ud.findById(idPlayer);
		Resource r = new Resource(type,u,amount);
		em.persist(r);
		return r;
	}

	@Override
<<<<<<< HEAD
	public Resource addResource(User u, int type) {
		return addResource(u,type,0);
	}

	@Override
	public List<Resource> getResources(User u) {
		return u.getResources();
	}

	@Override
	public Resource getResource(User u, int type) {
		final TypedQuery<Resource> query = em.createQuery("from Resource as r where r.userResource = :u and r.type = :type",Resource.class);
		query.setParameter("u", u);
=======
	public Resource addResource(int idPlayer, int type) {
		return addResource(idPlayer,type,0);
	}

	@Override
	public List<Resource> getResources(int idPlayer) {
		final TypedQuery<Resource> query = em.createQuery("from Resource as r where r.idPlayer = :idPlayer", Resource.class);
		query.setParameter("idPlayer", idPlayer);
		final List<Resource> list = query.getResultList();
		return list;
	}

	@Override
	public Resource getResource(int idPlayer, int type) {
		final TypedQuery<Resource> query = em.createQuery("from Resource as r where idPlayer = :idPlayer and type = :type",Resource.class);
		query.setParameter("idPlayer", idPlayer);
>>>>>>> d9433c89c73caca8960c804bdb6b8b63df0fe4cf
		query.setParameter("type", type);
		final List<Resource> list = query.getResultList();
		return list.isEmpty() ? null : list.get(0);
	}

	@Override
<<<<<<< HEAD
	public void deleteResource(User u, int type) {
		final Query query = em.createQuery("delete Resource where userResource = :u and type = :t");
		query.setParameter("u", u);
		query.setParameter("t", type);
=======
	public void setAmount(int idPlayer, int type, int value) {
		final Query query = em.createQuery("update Resource set amount = :amount where idPlayer = :idPlayer and type = :type");
		query.setParameter("amount", value);
		query.setParameter("idPlayer", idPlayer);
		query.setParameter("type", type);
>>>>>>> d9433c89c73caca8960c804bdb6b8b63df0fe4cf
		query.executeUpdate();
		
	}


}
