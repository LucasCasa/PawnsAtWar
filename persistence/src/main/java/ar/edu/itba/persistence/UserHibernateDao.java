package ar.edu.itba.persistence;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
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
		TypedQuery<User> query = em.createQuery("from User where UPPER(name) = UPPER(:username)",User.class);
		query.setParameter("username", username);
		final List<User> list = query.getResultList();
		return list.isEmpty() ? null : list.get(0);
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
	
	@Override
	public List<User> getAllUsers(){
		TypedQuery<User> query = em.createQuery("from User",User.class);
		final List<User> list = query.getResultList();
		return list;
	}

	@Override
	public List<String> getUsernames(){
		List<String> rta = new ArrayList<>();
		List<User> list = getAllUsers();
		for(User u: list){
			rta.add(u.getName());
		}
		return rta;
	}

	@Override
	public void setLocale(User u, String language) {
		final Query query = em.createQuery("update User set locale = :locale where idPlayer = :id");
		query.setParameter("locale", language);
		query.setParameter("id", u.getId());
		query.executeUpdate();
	}

}
