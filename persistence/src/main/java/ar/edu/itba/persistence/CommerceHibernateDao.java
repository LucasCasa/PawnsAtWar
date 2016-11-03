package ar.edu.itba.persistence;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;


import ar.edu.itba.interfaces.CommerceDao;
import ar.edu.itba.model.TradeOffer;
import ar.edu.itba.model.User;

@Repository
public class CommerceHibernateDao implements CommerceDao {
	
	@PersistenceContext
    private EntityManager em;
	
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
		final TypedQuery<TradeOffer> query = em.createQuery("from TradeOffer as c where c.owner = :u", TradeOffer.class);
		query.setParameter("u", u);
		final List<TradeOffer> list = query.getResultList();
		return list;
	}

	@Override
	public void createOffer(User u, int giveType, int giveAmount, int getType, int receiveAmount) {
		TradeOffer td = new TradeOffer(u, giveType, giveAmount, getType, receiveAmount);
		em.persist(td);
	}

	@Override
	public void deleteOffers(User u) {
		final Query query = em.createQuery("delete TradeOffer where owner = :u");
		query.setParameter("u", u);
		query.executeUpdate();
		
	}

}
