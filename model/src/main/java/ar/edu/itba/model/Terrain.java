package ar.edu.itba.model;

public class Terrain {
	private Point position;
	private int typemod;
	private int power;
	
	public Terrain(Point position, int typemod, int power) {
		super();
		this.position = position;
		this.typemod = typemod;
		this.power = power;
	}

	public Point getPosition() {
		return position;
	}

	public int getTypemod() {
		return typemod;
	}

	public int getPower() {
		return power;
	}
	
}
