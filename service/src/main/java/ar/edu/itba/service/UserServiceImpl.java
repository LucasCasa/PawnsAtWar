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
	public User getByUsername(String username) {
		return ud.findByUsername(username);
	}
	
	
	
	
}
