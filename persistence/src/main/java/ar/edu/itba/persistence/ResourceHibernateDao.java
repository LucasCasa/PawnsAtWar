package ar.edu.itba.persistence;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ar.edu.itba.interfaces.ResourceDao;
import ar.edu.itba.interfaces.UserDao;
import ar.edu.itba.model.Resource;
import ar.edu.itba.model.User;

@Repository
public class ResourceHibernateDao implements ResourceDao {
	
	@PersistenceContext
    private EntityManager em;
	
	@Autowired
	private UserDao ud;

	@Override
	public void addAmount(int idPlayer, int type, int value) {
		Resource amount = getResource(idPlayer,type);
		final Query query = em.createQuery("update Resource set amount = :amount where user.id = :idPlayer and type = :type");
		query.setParameter("amount", amount.getQuantity() + value);
		query.setParameter("idPlayer", idPlayer);
		query.setParameter("type", type);
		query.executeUpdate();

	}

	@Override
	public void subtractAmount(int idPlayer, int type, int value) {
		Resource amount = getResource(idPlayer,type);
		int cant = amount.getQuantity() <= value ? 0 : amount.getQuantity() - value;
		final Query query = em.createQuery("update Resource set amount = :amount where user.id = :idPlayer and type = :type");
		query.setParameter("amount", cant);
		query.setParameter("idPlayer", idPlayer);
		query.setParameter("type", type);
		query.executeUpdate();

	}

	@Override
	public Resource addResource(User u, int type, int amount) {
		Resource r = new Resource(type,u,amount);
		em.persist(r);
		return r;
	}

	@Override
	public Resource addResource(int idPlayer, int type) {
		return addResource(ud.findById(idPlayer),type,0);
	}

	@Override
	public List<Resource> getResources(int idPlayer) {
		final TypedQuery<Resource> query = em.createQuery("from Resource as r where r.user.id = :idPlayer", Resource.class);
		query.setParameter("idPlayer", idPlayer);
		final List<Resource> list = query.getResultList();
		return list;
	}

	@Override
	public Resource getResource(int idPlayer, int type) {
		final TypedQuery<Resource> query = em.createQuery("from Resource as r where r.user.id = :idPlayer and r.type = :type",Resource.class);
		query.setParameter("idPlayer", idPlayer);
		query.setParameter("type", type);
		final List<Resource> list = query.getResultList();
		return list.isEmpty() ? null : list.get(0);
	}

	@Override
	public void setAmount(int idPlayer, int type, int value) {
		final Query query = em.createQuery("update Resource set amount = :amount where user.id = :idPlayer and type = :type");
		query.setParameter("amount", value);
		query.setParameter("idPlayer", idPlayer);
		query.setParameter("type", type);
		query.executeUpdate();

	}

}
