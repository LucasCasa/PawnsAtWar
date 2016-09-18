package ar.edu.itba.model;

public class Terrain extends Sector {
	private int power;
	
	public Terrain(Point position, int type, int power) {
		super(position,type);
		this.power = power;
	}

	public Point getPosition() {
		return super.getPosition();
	}

	public int getType() {
		return super.getType();
	}

	public int getPower() {
		return power;
	}
	
	public String toString(){
		return "(" + super.getPosition().toString() + "," + super.getType() + "," + this.power + ")";
	}
}
