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
		Message m = new Message(from, to, subject , message);
		em.persist(m);
		return m;
	}

	@Override
	public Message getById(Long id) {
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
		final Query query = em.createQuery("delete Message where id = :id");
		query.setParameter("id", id);
		query.executeUpdate();
	}

}
