package ar.edu.itba.interfaces;

import ar.edu.itba.model.User;

public interface UserDao {
	public User findbyId(long id);
	public User create(String username, String password, String email);
	
}
