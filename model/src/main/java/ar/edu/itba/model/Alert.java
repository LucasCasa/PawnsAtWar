package ar.edu.itba.model;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "alert")
public class Alert {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "alert_idalert_seq")
	@SequenceGenerator(sequenceName = "alert_idalert_seq", name = "alert_idalert_seq", allocationSize = 1)
	@Column(name = "idalert")
	private int id;

	@ManyToOne(fetch = FetchType.LAZY ,optional = false)
	private User userAlert;

	@Column(nullable = false, name = "date")
	private Timestamp date;

	@Column(length = 200,nullable = false, name = "message")
	private String message;

	/* package */ Alert(){

	}


	public Alert(User user, String message,Date d) {
		this.userAlert = user;
		this.message = message;
		this.date = new Timestamp(d.getTime());
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}


	public Timestamp getDate() {
		return date;
	}

	public void setDate(Timestamp date) {
		this.date = date;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public User getUser() {
		return userAlert;
	}

	public void setUser(User user) {
		this.userAlert = user;
	}
	
	


	public User getUserAlert() {
		return userAlert;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
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
		Alert other = (Alert) obj;
		if (id != other.id)
			return false;
		return true;
	}



}
