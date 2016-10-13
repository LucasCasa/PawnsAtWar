package ar.edu.itba.paw.webapp.form;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;


public class RegisterForm {

	@Size(min = 5, max = 100) @Pattern(regexp = "[a-zA-Z0-9]+") private String username;
	@Size(min = 5, max = 100) private String email;
	@Size(min = 5, max = 100) private String password;
	@Size(min = 5, max = 100) private String repeatPassword;
	public String getUsername() { 
		return username;
	}
	public void setUsername(String username) { 
		this.username = username;
	}
	public String getEmail(){
		return email;
	}
	public void setEmail(String email){
		this.email = email;
	}
	public String getPassword() { 
		return password;
	}
	public void setPassword(String password) { 
		this.password = password;
	}
	
	 public String getRepeatPassword() { 
		 return repeatPassword;
	 }
	 public void setRepeatPassword(String repeatPassword) {
		 this.repeatPassword = repeatPassword; 
	 }
	
	
}