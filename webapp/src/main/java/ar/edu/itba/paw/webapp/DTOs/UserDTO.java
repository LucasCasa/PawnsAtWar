package ar.edu.itba.paw.webapp.DTOs;

import ar.edu.itba.model.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDTO {

	private int id;
	private String username;
	private String locale;

	public UserDTO(final User user) {
		this.id = user.getId();
		this.username = user.getName();
		this.locale = user.getLocale();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getLocale() {
		return locale;
	}

	public void setLocale(String locale) {
		this.locale = locale;
	}
}
