package ar.edu.itba.model;

public class Buildings extends Sector {
	private int level;
	
	public Buildings(Point position, int type, int level) {
		super(position,type);
		this.level = level;
	}
	public int getLevel() {
		return level;
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
