package ar.edu.itba.persistence;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import ar.edu.itba.interfaces.MessageDao;
import ar.edu.itba.model.Message;
import ar.edu.itba.model.User;

import java.util.List;

@Repository
public class MessageHibernateDao implements MessageDao {
	
	@PersistenceContext
    private EntityManager em;

	@Override
	public Message createMessage(User from, User to, String subject, String message) {
		if(from == null || to == null || subject == null || message == null){
			return null;
		}
		Message m = new Message(from, to, subject , message);
		em.persist(m);
		return m;
	}

	@Override
	public Message getById(Long id) {
		if(id < 0){
			return null;
		}
		return em.find(Message.class, id);
	}

	@Override
	public List<Message> getAllMessages(User u) {
		final TypedQuery<Message> query = em.createQuery("from Message as c where c.to = :u", Message.class);
		query.setParameter("u", u);
		final List<Message> list = query.getResultList();
		return list;
	}

	@Override
	public void removeMessage(Long id) {
		if(id < 0)
			return;
		final Query query = em.createQuery("delete Message where id = :id");
		query.setParameter("id", id);
		query.executeUpdate();
	}

	@Override
	public void removeMessages(List<Message> message) {
		for(Message m: message){
			removeMessage(m.getId());
		}
		
	}

	@Override
	public int countUnreadMessages(User u){
		return getUnReadMessages(u).size();
	}

	@Override
	public void markAsRead(Long id) {
		Message m = getById(id);
		m.setRead(true);
		em.persist(m);
		return;
	}

	@Override
	public List<Message> getReadMesssages(User u) {
		final TypedQuery<Message> query = em.createQuery("from Message as c where c.to = :u and read = true", Message.class);
		query.setParameter("u", u);
		final List<Message> list = query.getResultList();
		return list;
	}

	@Override
	public List<Message> getUnReadMessages(User u) {
		final TypedQuery<Message> query = em.createQuery("from Message as c where c.to = :u and read = false", Message.class);
		query.setParameter("u", u);
		final List<Message> list = query.getResultList();
		return list;
	}

	@Override
	public List<Message> getSentMessages(User u) {
		final TypedQuery<Message> query = em.createQuery("from Message as c where c.from = :u", Message.class);
		query.setParameter("u", u);
		final List<Message> list = query.getResultList();
		return list;
	}

}
