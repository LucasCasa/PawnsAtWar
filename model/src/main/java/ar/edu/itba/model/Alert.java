package ar.edu.itba.model;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.*;

@Entity
@Table(name = "alert")
public class Alert {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "alert_idalert_seq")
	@SequenceGenerator(sequenceName = "alert_idalert_seq", name = "alert_idalert_seq", allocationSize = 1)
	@Column(name = "idalert")
	private int id;

	@ManyToOne(fetch = FetchType.EAGER ,optional = false)
	private User userAlert;

	@Column(nullable = false, name = "date")
	private Timestamp date;

	@Column(length = 200,nullable = false, name = "message")
	private String message;

	@Column(length = 20,nullable = false, name = "type")
	private String type;

	@Embedded
	private Point p;

	@Column(name = "param1")
	private Integer param1;

	@Column(name = "param2")
	private Integer param2;



	/* package */ Alert(){

	}


	public Alert(User user, String message,Date d,String type,Point p, Integer param1, Integer param2) {
		this.userAlert = user;
		this.message = message;
		this.date = new Timestamp(d.getTime());
		this.p = p;
		this.type = type;
		this.param1 = param1;
		this.param2 = param2;
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

	public void setUserAlert(User userAlert) {
		this.userAlert = userAlert;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Point getP() {
		return p;
	}

	public void setP(Point p) {
		this.p = p;
	}

	public Integer getParam1() {
		return param1;
	}

	public void setParam1(Integer param1) {
		this.param1 = param1;
	}

	public Integer getParam2() {
		return param2;
	}

	public void setParam2(Integer param2) {
		this.param2 = param2;
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
