package ar.edu.itba.model;

import java.sql.Time;
import java.sql.Timestamp;

public class Empire {
	private User user;
	private Timestamp lastUpdate;
	
	public Empire(User user, Timestamp timestamp) {
		this.user = user;
		this.lastUpdate = timestamp;
	}
	
	/* package */ Empire(){
		
	}

	public Timestamp getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(Timestamp lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public User getUser() {
		return user;
	}
}

