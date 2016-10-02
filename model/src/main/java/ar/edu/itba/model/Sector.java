package ar.edu.itba.model;

public class Sector {
	private Point p;
	private int type;
	private int idPlayer;
	
	public Sector(Point p, int type, int idPlayer) {
		this.p = p;
		this.type = type;
		this.idPlayer = idPlayer;
	}

	public int getType() {
		return type;
	}
	
	public int getIdPlayer(){
		return idPlayer;
	}

	public void setType(int type) {
		this.type = type;
	}
	
	public void setIdPlayer(int idPlayer){
		this.idPlayer = idPlayer;
	}

	public Point getPosition() {
		return p;
	}
	
	
	
	
}
