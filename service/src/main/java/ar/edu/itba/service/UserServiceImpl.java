package ar.edu.itba.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.itba.interfaces.EmpireService;
import ar.edu.itba.interfaces.UserDao;
import ar.edu.itba.interfaces.UserService;
import ar.edu.itba.model.User;


@Service
@Transactional
public class UserServiceImpl implements UserService{

	@Autowired
	UserDao ud;
	
	@Autowired
	EmpireService es;

	@Override
	public User findByUsername(String username) {
		return ud.findByUsername(username);
	}

	@Override
	public User findById(int id) {
		return ud.findById(id);
	}

	@Override
	public User create(String username, String password, String email) {
		User user = ud.create(username, password, email);
		es.createUser(user);
		return user;
		
	}

	@Override
	public String getUsername(int id) {
		return ud.getUsername(id);
	}
	
	public String getEmail (int id){
		return ud.getEmail(id);
	}
	
	public String getEmail (String username){
		return ud.getEmail(username);
	}

	@Override
	public boolean exists(String username, String password) {
		return ud.exists(username,password);
	}

	@Override
	public boolean exists(String username) {
		return ud.exists(username);
	}
	
	
	
}
