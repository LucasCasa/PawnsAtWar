package ar.edu.itba.persistence;

import ar.edu.itba.interfaces.UserDao;
import ar.edu.itba.model.User;

public class UserHibernateDao implements UserDao {

	@Override
	public User findById(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User create(String username, String password, String email) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getUsername(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User findByUsername(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getEmail(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getEmail(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean exists(String username, String password) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean exists(String username) {
		// TODO Auto-generated method stub
		return false;
	}

}
