package ar.edu.itba.persistence;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ar.edu.itba.interfaces.CommerceDao;
import ar.edu.itba.interfaces.ResourceDao;
import ar.edu.itba.model.Resource;
import ar.edu.itba.model.TradeOffer;
import ar.edu.itba.model.User;

@Repository
public class CommerceHibernateDao implements CommerceDao {
	
	@PersistenceContext
    private EntityManager em;
	
	@Autowired
	private ResourceDao rd;
	
	@Override
	public List<TradeOffer> getAllOffers() {
		final TypedQuery<TradeOffer> query = em.createQuery("from TradeOffer as c", TradeOffer.class);
		final List<TradeOffer> list = query.getResultList();
		return list;
	}

	@Override
	public TradeOffer getOffer(int id) {
		return em.find(TradeOffer.class, id);
	}

	@Override
	public void removeOffer(int id) {
		final Query query = em.createQuery("delete TradeOffer where tradeId = :id");
		query.setParameter("id", id);
		query.executeUpdate();

	}

	@Override
	public List<TradeOffer> getAllOffers(User u) {
		final TypedQuery<TradeOffer> query = em.createQuery("from TradeOffer as c where c.ownerId = :u", TradeOffer.class);
		query.setParameter("userId", u);
		final List<TradeOffer> list = query.getResultList();
		return list;
	}

	@Override
	public void createOffer(User u, int giveType, int giveAmount, int getType, int receiveAmount) {
		Resource give = rd.getResource(u, giveAmount);
		Resource receive = rd.getResource(u, receiveAmount);
		TradeOffer td = new TradeOffer(u, give, receive);
		em.persist(td);
	}

}
