package ar.edu.itba.paw.webapp.DTOs;

import ar.edu.itba.model.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserScoreDTO {

	private int id;
	private String username;
	private Long score;

	public UserScoreDTO(User user, Long score){
		this.id = user.getId();
		this.username = user.getName();
		this.score = score;
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

	public Long getScore() {
		return score;
	}

	public void setScore(Long score) {
		this.score = score;
	}
}
