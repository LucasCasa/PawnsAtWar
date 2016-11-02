package ar.edu.itba.persistence;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import ar.edu.itba.interfaces.ResourceDao;
import ar.edu.itba.model.Resource;
import ar.edu.itba.model.User;

@Repository
public class ResourceHibernateDao implements ResourceDao {
	
	@PersistenceContext
    private EntityManager em;


	@Override
	public void addAmount(User u, int type, int value) {
		Resource amount = getResource(u,type);
		amount.setQuantity(value + amount.getQuantity());

	}

	@Override
	public void subtractAmount(User u, int type, int value) {
		Resource amount = getResource(u,type);
		int cant = amount.getQuantity() <= value ? 0 : amount.getQuantity() - value;
		amount.setQuantity(cant);

	}

	@Override
	public Resource addResource(User u, int type, int amount) {
		Resource r = new Resource(type,u,amount);
		em.persist(r);
		return r;
	}

	@Override
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
		query.setParameter("type", type);
		final List<Resource> list = query.getResultList();
		return list.isEmpty() ? null : list.get(0);
	}


}
