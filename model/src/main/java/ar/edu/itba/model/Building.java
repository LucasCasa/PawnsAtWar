package ar.edu.itba.model;

public class Building extends Sector {
	private int level;
	private int idPlayer;
	
	public Building(Point position, int idPlayer, int type, int level) {
		super(position,type,idPlayer);
		this.level = level;
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
