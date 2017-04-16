package ar.edu.itba.service;

import ar.edu.itba.interfaces.EmpireService;
import ar.edu.itba.interfaces.UserDao;
import ar.edu.itba.interfaces.UserService;
import ar.edu.itba.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional
public class UserServiceImpl implements UserService{

	@Autowired
	private UserDao ud;
	
	@Autowired
	private EmpireService es;
	
	private BCryptPasswordEncoder bc =  new BCryptPasswordEncoder() ;
	

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
		User user = ud.create(username, bc.encode(password), email);
		boolean resp = es.createEmpire(user);
		return resp ? user : null;
		
	}
	
	@Override
	public boolean restoreUser(User user){
		if(user != null){
			return es.createUser(user,false);
		}
		return false;
		
	}

	@Override
	public List<String> getUsernames() {
		return ud.getUsernames();
	}

	@Override
	public String getUsername(int id) {
		return ud.getUsername(id);
	}

	@Override
	public boolean exists(String username, String password) {
		String pass = ud.getPassword(username);
		return ud.exists(username) && bc.matches(password, pass);
	}

	@Override
	public boolean exists(String username) {
		return ud.exists(username);
	}
	
	@Override
	public List<User> getAllUsers(){
		return ud.getAllUsers();
	}
	
	

	@Override
	public void setLocale(User u, String language) {
		ud.setLocale(u,language);
	}

	@Override
	public List<String> getUsernames(String exp) {
		return ud.getUsernames(exp);
	}

	
}
