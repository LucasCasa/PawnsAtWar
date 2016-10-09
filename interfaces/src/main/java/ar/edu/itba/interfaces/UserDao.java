package ar.edu.itba.interfaces;

import ar.edu.itba.model.User;

public interface UserDao {
	
	public User findById(long id);
	
	public User create(String username, String password, String email);
	
	public String getUsername(long id);

	public User findByUsername(String username);
	
}
