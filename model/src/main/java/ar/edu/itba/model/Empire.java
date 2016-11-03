package ar.edu.itba.model;

import java.io.Serializable;
import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "empire")
public class Empire implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@OneToOne(optional=false)
    @JoinColumn(name="idPlayer", nullable=false, updatable=false)
	private User userEmpire;
	
	@Column(nullable = false)
	private Timestamp lastUpdate;
 	
		public Empire(User u, Timestamp timestamp) {
			this.userEmpire = u;
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
		return userEmpire;
	}
}

