package ar.edu.itba.interfaces;

import ar.edu.itba.model.User;

import java.util.List;

public interface UserDao {
	
	public User findById(int id);
	
	public User create(String username, String password, String email);
	
	public String getUsername(int id);

	public User findByUsername(String username);	

	public boolean exists(String username, String password);

	public boolean exists(String username);
	
	public List<User> getAllUsers();

	public List<String> getUsernames();

	public void setLocale(User u, String language);
}
