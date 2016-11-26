package ar.edu.itba.persistence;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import ar.edu.itba.model.Army;
import org.springframework.stereotype.Repository;

import ar.edu.itba.interfaces.AlertDao;
import ar.edu.itba.model.Alert;
import ar.edu.itba.model.User;

import java.util.Date;
import java.util.List;

@Repository
public class AlertHibernateDao implements AlertDao{
	
	@PersistenceContext
    private EntityManager em;

	@Override
	public Alert findById(int id) {
		return em.find(Alert.class, id);
	}

	@Override
	public Alert createAlert(User user, String message,Date d) {
		Alert a = new Alert(user,message,d);
		em.persist(a);
		return a;
	}


	@Override
	public void removeAlert(Alert a) {
		final Query query = em.createQuery("delete Alert where idAlert = :idAlert");
		query.setParameter("idAlert", a.getId());
		query.executeUpdate();
	}

	@Override
	public List<Alert> getByUser(User u) {
		final TypedQuery<Alert> query = em.createQuery("from Alert as a where a.userAlert = :u",Alert.class);
		query.setParameter("u",u);
		final List<Alert> list = query.getResultList();
		return list;
	}
}
