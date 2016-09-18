package ar.edu.itba.model;

public class Sector {
	private Point p;
	private int type;
	
	public Sector(Point p, int type) {
		this.p = p;
		this.type = type;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public Point getPosition() {
		return p;
	}
	
	
	
	
}
