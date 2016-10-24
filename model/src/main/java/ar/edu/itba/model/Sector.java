package ar.edu.itba.model;

public class Sector {
	private Point p;
	private int type;
	private User user;
	
	public Sector(Point p, int type, User user) {
		this.p = p;
		this.type = type;
		this.user = user;
	}
	
	/* package */ Sector(){
		
	}

	public int getType() {
		return type;
	}
	
	public User getUser(){
		return user;
	}

	public void setType(int type) {
		this.type = type;
	}
	
	public void setUser(User user){
		this.user = user;
	}

	public Point getPosition() {
		return p;
	}
	
	
	
	
}
