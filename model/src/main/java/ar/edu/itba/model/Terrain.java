package ar.edu.itba.model;

public class Terrain extends Sector {
	private int power;
	
	public Terrain(Point position, int type, int power,User user) {
		super(position,type,user);
		this.power = power;
	}

	public Point getPosition() {
		return super.getPosition();
	}

	public int getType() {
		return super.getType();
	}
	
	public User getUser(){
		return super.getUser();
	}

	public int getPower() {
		return power;
	}
	
	public void setUser(User user){
		super.setUser(user);
	}
	
	public String toString(){
		return "(" + super.getPosition().toString() + "," + super.getType() + "," + this.power + ")";
	}
}
