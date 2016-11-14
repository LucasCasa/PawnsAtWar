package ar.edu.itba.interfaces;

import java.util.List;

import ar.edu.itba.model.User;

public interface UserService {

	public User findByUsername(String username);
	
	public User findById(int id);
	
	public User create(String username, String password, String email);
	
	public String getUsername(int id);

	public boolean exists(String username);

	public boolean exists(String username, String password);	
	
	public List<User> getAllUsers();

	public void restoreUser(User user);
}
