package ar.edu.itba.paw.webapp.beans;

import ar.edu.itba.model.User;

public class UserScoreBean {
	private User user;
	private Long score;
	
	public UserScoreBean(User user, Long score) {
		this.user = user;
		this.score = score;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Long getScore() {
		return score;
	}

	public void setScore(Long score) {
		this.score = score;
	}

	@Override
	public int hashCode() {
		return user.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		return user.equals(obj);
	}
}
