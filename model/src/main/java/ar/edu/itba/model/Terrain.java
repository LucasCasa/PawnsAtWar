package ar.edu.itba.model;

public class Terrain {
	private Point position;
	private TypeMod typemod;
	private int power;
	
	public Terrain(Point position, TypeMod typemod, int power) {
		super();
		this.position = position;
		this.typemod = typemod;
		this.power = power;
	}

	public Point getPosition() {
		return position;
	}

	public TypeMod getTypemod() {
		return typemod;
	}

	public int getPower() {
		return power;
	}
	
}
