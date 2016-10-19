package ar.edu.itba.model;

import java.util.List;

public class Army {
	private Point position;
	private int idArmy;
	private User user;
	private List<Troop> troops;
	private boolean available;
	
	public Army(Point position, User user,int idArmy, boolean available,List<Troop> troops) {
		this.position = position;
		this.idArmy = idArmy;
		this.user = user;
		this.available = available;
		this.troops = troops;
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
