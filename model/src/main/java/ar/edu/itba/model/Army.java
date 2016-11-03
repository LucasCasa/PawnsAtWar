package ar.edu.itba.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "Army")
public class Army {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "army_idarmy_seq")
	@SequenceGenerator(sequenceName = "army_idarmy_seq", name = "army_idarmy_seq", allocationSize = 1)
	@Column(name = "idArmy")
	private int idArmy;
	
	@Embedded
	private Point position;
	
	@Column(nullable = false, name = "available")
	private boolean available;
	
	@ManyToOne(fetch =FetchType.LAZY, optional = false)
	@JoinColumn(name="idPlayer")
	private User userArmy;
	
	@OneToMany(fetch = FetchType.EAGER,orphanRemoval = false, mappedBy="army")
	private List<Troop> troops;

	public Army(Point position, User user,boolean available) {
		this.position = position;
		this.userArmy = user;
		this.available = available;
	}
	
	/* package */ Army(){
		
	}

	public boolean getAvailable() {
		return available;
	}
	
	public User getUser(){
		return userArmy;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}

	public Point getPosition() {
		return position;
	}
	public int getIdArmy() {
		return idArmy;
	}
	
	public void addTroop(Troop t){
		troops.add(t);
	}

	
	public List<Troop> getTroops(){
		return troops;
	}

	public void setUser(User user) {
		this.userArmy = user;
	}
	
	
}
