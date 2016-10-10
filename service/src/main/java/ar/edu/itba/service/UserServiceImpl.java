package ar.edu.itba.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.itba.interfaces.UserDao;
import ar.edu.itba.interfaces.UserService;
import ar.edu.itba.model.User;


@Service
public class UserServiceImpl implements UserService{

	@Autowired
	UserDao ud;

	@Override
	public User findByUsername(String username) {
		return ud.findByUsername(username);
	}

	@Override
	public User findById(long id) {
		return ud.findById(id);
	}

	@Override
	public User create(String username, String password, String email) {
		return ud.create(username, password, email);
	}

	@Override
	public String getUsername(long id) {
		return ud.getUsername(id);
	}
	
	public String getEmail (long id){
		return ud.getEmail(id);
	}
	
	public String getEmail (String username){
		return ud.getEmail(username);
	}
	
	
	
}
