package ar.edu.itba.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.*;

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

	public Empire(User u, Date date) {
		this.userEmpire = u;
		this.lastUpdate = new Timestamp(date.getTime());
	}

	/* package */ Empire(){

	}

	public Date getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(Date date ) {
		this.lastUpdate = new Timestamp(date.getTime()) ;
	}

	public User getUser() {
		return userEmpire;
	}
	

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public User getUserEmpire() {
		return userEmpire;
	}

	public void setUserEmpire(User userEmpire) {
		this.userEmpire = userEmpire;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((userEmpire == null) ? 0 : userEmpire.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Empire other = (Empire) obj;
		if (userEmpire == null) {
			if (other.userEmpire != null)
				return false;
		} else if (!userEmpire.equals(other.userEmpire))
			return false;
		return true;
	}
	
	

}

