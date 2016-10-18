package ar.edu.itba.model;

public class Building extends Sector {
	private int level;
	
	public Building(Point position, User user, int type, int level) {
		super(position,type,user);
		this.level = level;
	}
	
	public int getLevel() {
		return level;
	}
	public User getUser() {
		return super.getUser();
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public Point getPosition() {
		return super.getPosition();
	}

	public int getType() {
		return super.getType();
	}
	
	
	
}
