package ar.edu.itba.persistence;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import ar.edu.itba.model.*;
import org.springframework.stereotype.Repository;

import ar.edu.itba.interfaces.AlertDao;

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
	public Alert createAlert(User user, String message, Date d, String type, Point p, Integer param1, Integer param2) {
		if(user == null || message == null || d == null || type == null || p == null){
			return null;
		}
		Alert a = new Alert(user,message,d,type,p,param1,param2);
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
	@Override
	public List<Alert> getAllAlerts() {
		final TypedQuery<Alert> query = em.createQuery("from Alert as a",Alert.class);
		final List<Alert> list = query.getResultList();
		return list;
	}

	@Override
	public Alert getAlertByPoint(Point p) {
		final TypedQuery<Alert> query = em.createQuery("from Alert as a where a.p = :p and (a.type = :b or a.type = :u or a.type = :t)",Alert.class);
		query.setParameter("p",p);
		query.setParameter("b", AlertType.BUILD.toString());
		query.setParameter("u", AlertType.UPGRADE.toString());
		query.setParameter("t", AlertType.RECRUIT.toString());
		final List<Alert> list = query.getResultList();
		return list.isEmpty()?null:list.get(0);
	}

	@Override
	public List<Alert> getBuildingConstructed(Point p) {
		final int range = 4;
		final TypedQuery<Alert> query = em.createQuery("from Alert as a where a.p.x<= :xmax and a.p.y<= :ymax and a.p.x>= :xmin and a.p.y>= :ymin and a.type= :type and a.param1= 1",Alert.class);
		query.setParameter("xmax",p.getX()+range);
		query.setParameter("xmin",p.getX()-range);
		query.setParameter("ymax",p.getX()+range);
		query.setParameter("ymin",p.getX()-range);
		query.setParameter("type",AlertType.BUILD.toString());
		final List<Alert> list = query.getResultList();
		return list;
	}
}
