package ar.edu.itba.persistence;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import ar.edu.itba.interfaces.UserDao;
import ar.edu.itba.model.User;

@Repository
public class UserHibernateDao implements UserDao {
	
	@PersistenceContext
	private EntityManager em;

	@Override
	public User findById(int id) {
		return em.find(User.class, id);
	}

	@Override
	public User create(String username, String password, String email) {
		final User user = new User(username,password,email);
		em.persist(user);
		return user;
	}

	@Override
	public String getUsername(int id) {
		User u = findById(id);
		return u == null ? null : u.getName();
	}

	@Override
	public User findByUsername(String username) {
		TypedQuery<User> query = em.createQuery("from User where name = :username",User.class);
		query.setParameter("username", username);
		final List<User> list = query.getResultList();
		System.err.println(list);
		return list.isEmpty() ? null : list.get(0);
	}

	@Override
	public String getEmail(int id) {
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
