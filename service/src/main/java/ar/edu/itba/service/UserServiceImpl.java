package ar.edu.itba.service;

import ar.edu.itba.interfaces.EmpireService;
import ar.edu.itba.interfaces.UserDao;
import ar.edu.itba.interfaces.UserService;
import ar.edu.itba.model.User;
import org.springframework.beans.factory.annotation.Autowired;
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
	
	private static char [] alphabet = {'d','a','x','z','b','m','r','j','f','u','t','o','y','h','e','c','l','w','v','i','k','g','p','n','q','s'};

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
		User user = ud.create(username, encryptPass(password), email);
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
		return ud.exists(username,encryptPass(password));
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
	
	public String encryptPass(String pass){
		char [] aux = pass.toCharArray();
		for(int i=0;i<aux.length;i++){
			int value = aux[i] - 'a';
			aux[i]=alphabet[value];
			
		}
		return new String(aux);
	}
	
	public String decryptPass(String pass){
		char [] aux = pass.toCharArray();
		for(int i=0;i<aux.length;i++){
			int value = position(aux[i],alphabet);
			aux[i]= (char) (value + 'a');
		}
		return new String(aux);
	}

	private int position(char c, char[] alphabet) {
		for(int i=0;i<alphabet.length;i++){
			if(c == alphabet[i]){
				return i;
			}
		}
		return -1;
	}
	
}
