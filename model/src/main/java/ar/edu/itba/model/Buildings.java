package ar.edu.itba.model;

public class Buildings extends Sector {
	private int level;
	private int idPlayer;
	
	public Buildings(Point position, int idPlayer, int type, int level) {
		super(position,type);
		this.level = level;
		this.idPlayer = idPlayer;
	}
	
	public int getLevel() {
		return level;
	}
	public int getPlayer() {
		return idPlayer;
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
