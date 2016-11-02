package ar.edu.itba.persistence;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import ar.edu.itba.interfaces.AlertDao;
import ar.edu.itba.model.Alert;
import ar.edu.itba.model.User;

@Repository
public class AlertHibernateDao implements AlertDao{
	
	@PersistenceContext
    private EntityManager em;

	@Override
	public Alert findById(int id) {
		return em.find(Alert.class, id);
	}

	@Override
	public Alert createAlert(User user, String message) {
		Alert a = new Alert(user,message);
		em.persist(a);
		return a;
	}

}
