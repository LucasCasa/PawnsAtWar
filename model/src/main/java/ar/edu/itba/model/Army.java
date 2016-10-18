package ar.edu.itba.model;

import java.util.List;

public class Army {
	private Point position;
	private int idArmy;
	private User user;
	private List<Troop> troop;
	private boolean available;
	
	public Army(Point position, User user,int idArmy, boolean available,List<Troop> troop) {
		this.position = position;
		this.idArmy = idArmy;
		this.user = user;
		this.available = available;
		this.troop = troop;
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
		troop.add(t);
	}
	
	public void setTroop(List<Troop> troop){
		this.troop=troop;
	}
	
	public List<Troop> getTroops(){
		return troop;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	
}
