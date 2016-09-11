package ar.edu.itba.persistence;

import ar.edu.itba.interfaces.UserDao;
import ar.edu.itba.model.User;

public class UserJdbcDao implements UserDao{

	@Override
	public User findbyId(long id) {
		return null;
	}

	@Override
	public User create(String username, String email) {
		return null;
	}

}
