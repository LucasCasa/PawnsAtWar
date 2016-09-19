package ar.edu.itba.model;

public class Army {
	private Point position;
	private int idArmy;
	private int idPlayer;
	private boolean available;
	
	public Army(Point position, int idPlayer,int idArmy, boolean available) {
		this.position = position;
		this.idArmy = idArmy;
		this.idPlayer = idPlayer;
		this.available = available;
	}

	public boolean isAvailable() {
		return available;
	}
	
	public int getPlayer(){
		return idPlayer;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}

	public Point getPosition() {
		return position;
	}
	public int getIdArmy() {
		return idArmy;
	}
	
	
}
