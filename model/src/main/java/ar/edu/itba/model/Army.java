package ar.edu.itba.model;

import java.util.List;

public class Army {
	private Point position;
<<<<<<< HEAD
	
	@Column(nullable = false, name = "available")
	private boolean available;
	
	@ManyToOne(fetch =FetchType.LAZY, optional = false)
	@JoinColumn(name="idPlayer")
	private User userArmy;
	
	@OneToMany(fetch = FetchType.EAGER,orphanRemoval = false, mappedBy="army")
=======
	private int idArmy;
	private User user;
>>>>>>> d9433c89c73caca8960c804bdb6b8b63df0fe4cf
	private List<Troop> troops;
	private boolean available;
	
	/* SOLO PARA JDBC */
	public Army(Point position, User user, int idArmy,boolean available,List<Troop> troops) {
		this.position = position;
		this.user = user;
		this.idArmy = idArmy;
		this.available = available;
		this.troops = troops;
	}
	public Army(Point position, User user,boolean available,List<Troop> troops) {
		this.position = position;
		this.user = user;
		this.available = available;
		this.troops = troops;
	}
	
	/* package */ Army(){
		
	}

	public boolean getAvailable() {
		return available;
	}
	
	public User getUser(){
		return user;
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
	
	public void setTroops(List<Troop> troops){
		this.troops=troops;
	}
	
	public List<Troop> getTroops(){
		return troops;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	
}
