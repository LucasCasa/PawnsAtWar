package ar.edu.itba.model;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name ="building",uniqueConstraints=@UniqueConstraint(columnNames={"x", "y"}))
public class Sector {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "building_id_seq")
	@SequenceGenerator(sequenceName = "building_id_seq", name = "building_id_seq", allocationSize = 1)
	@Column(name = "id")
	private long id;

	@Column(name = "level", nullable = false)
	private int level;

	@Embedded
	private Point p;
	@Column(name = "type" , nullable = false)
	private int type;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="idPlayer")
	private User userBuilding;


	public Sector(User user, Point p, int type, int level) {
		this.userBuilding = user;

		this.p = p;
		this.type = type;
		this.level  =level;
	}

	/* package */ Sector(){

	}

	public int getType() {
		return type;
	}

	public User getUser(){
		return userBuilding;
	}

	public void setType(int type) {
		this.type = type;
	}

	public void setUser(User user){
		this.userBuilding = user;
	}

	public Point getPosition() {
		return p;
	}

	
	public void setId(long id) {
		this.id = id;
	}

	public void setP(Point p) {
		this.p = p;
	}

	public void setUserBuilding(User userBuilding) {
		this.userBuilding = userBuilding;
	}

	public int getLevel() {
		return level;

	}
	public void setLevel(int level) {
		this.level = level;
	}

	public String toString(){
		return "(" + getPosition().toString() + "," + getType() + "," + this.level + ")";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((p == null) ? 0 : p.hashCode());
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
		Sector other = (Sector) obj;
		if (id != other.id)
			return false;
		if (p == null) {
			if (other.p != null)
				return false;
		} else if (!p.equals(other.p))
			return false;
		return true;
	}




}
