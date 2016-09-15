package ar.edu.itba.model;

public class Terrain {
	private Point position;
	private int type;
	private int power;
	
	public Terrain(Point position, int type, int power) {
		super();
		this.position = position;
		this.type = type;
		this.power = power;
	}

	public Point getPosition() {
		return position;
	}

	public int getType() {
		return type;
	}

	public int getPower() {
		return power;
	}
	
	public String toString(){
		return "(" + this.position.toString() + "," + this.type + "," + this.power + ")";
	}
}
