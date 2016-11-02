package ar.edu.itba.persistence;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import ar.edu.itba.interfaces.MessageDao;
import ar.edu.itba.model.Message;
import ar.edu.itba.model.User;

@Repository
public class MessageHibernateDao implements MessageDao {
	
	@PersistenceContext
    private EntityManager em;

	@Override
	public Message createMessage(User from, User to, String message) {
		Message m = new Message(from, to, message);
		em.persist(m);
		return m;
	}

	@Override
	public Message getById(int id) {
		return em.find(Message.class, id);
	}

}
