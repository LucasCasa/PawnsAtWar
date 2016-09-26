package ar.edu.itba.model;

public class Terrain extends Sector {
	private int power;
	
	public Terrain(Point position, int type, int power,int idPlayer) {
		super(position,type,idPlayer);
		this.power = power;
	}

	public Point getPosition() {
		return super.getPosition();
	}

	public int getType() {
		return super.getType();
	}
	
	public int getidPlayer(){
		return super.getIdPlayer();
	}

	public int getPower() {
		return power;
	}
	
	public void setidPlayer(int idPlayer){
		super.setIdPlayer(idPlayer);
	}
	
	public String toString(){
		return "(" + super.getPosition().toString() + "," + super.getType() + "," + this.power + ")";
	}
}
