package ar.edu.itba.persistence;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import ar.edu.itba.interfaces.UserDao;
import ar.edu.itba.model.User;

public class UserHibernateDao implements UserDao {
	
	@PersistenceContext
	private EntityManager em;

	@Override
	public User findById(long id) {
		return em.find(User.class, id);
	}

	@Override
	public User create(String username, String password, String email) {
		final User user = new User(username,password,email);
		em.persist(user);
		return user;
	}

	@Override
	public String getUsername(long id) {
		User u = findById(id);
		return u == null ? null : u.getName();
	}

	@Override
	public User findByUsername(String username) {
		final TypedQuery<User> query = em.createQuery("from User as u where u.username = :username",User.class);
		query.setParameter("username", username);
		final List<User> list = query.getResultList();
		return list.isEmpty() ? null : list.get(0);
	}

	@Override
	public String getEmail(long id) {
		User u = findById(id);
		return u == null ? null : u.getEmail();
	}

	@Override
	public String getEmail(String username) {
		User u = findByUsername(username);
		return u == null ? null : u.getEmail();
	}

	@Override
	public boolean exists(String username, String password) {
		User u = findByUsername(username);
		if(u != null && u.getPassword().equals(password)){
			return true;
		}
		return false;
	}

	@Override
	public boolean exists(String username) {
		User u = findByUsername(username);
		return u == null ? false : true;
	}

}
