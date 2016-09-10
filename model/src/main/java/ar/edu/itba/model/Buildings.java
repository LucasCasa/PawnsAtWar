package ar.edu.itba.model;

public class Buildings {
	private Point position;
	private int idPlayer;
	private Type type;
	private int level;
	
	public Buildings(Point position, int idPlayer, Type type, int level) {
		super();
		this.position = position;
		this.idPlayer = idPlayer;
		this.type = type;
		this.level = level;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public Point getPosition() {
		return position;
	}
	public int getIdPlayer() {
		return idPlayer;
	}
	public Type getType() {
		return type;
	}
	
	
	
}
