package ar.edu.itba.model;

public class Sector {
	private Point p;
	private int type;
	private User user;
	
<<<<<<< HEAD
	@ManyToOne(fetch = FetchType.EAGER,optional = false)
	@JoinColumn(name="idPlayer")
	private User userBuilding;
	
	public Sector(User u, Point p, int type) {
		this.userBuilding = u;
		this.p = p;
		this.type = type;
	}

	public Sector(User user, Point p, int type, int level) {
		this.userBuilding = user;
=======
	public Sector(Point p, int type, User user) {
>>>>>>> d9433c89c73caca8960c804bdb6b8b63df0fe4cf
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
