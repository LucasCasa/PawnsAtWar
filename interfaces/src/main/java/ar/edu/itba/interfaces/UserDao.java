package ar.edu.itba.interfaces;

import ar.edu.itba.model.User;

public interface UserDao {
	
	public User findById(int id);
	
	public User create(String username, String password, String email);
	
	public String getUsername(int id);

	public User findByUsername(String username);

	public String getEmail(int id);
	
	public String getEmail(String username);

	public boolean exists(String username, String password);

	public boolean exists(String username);
	
}
