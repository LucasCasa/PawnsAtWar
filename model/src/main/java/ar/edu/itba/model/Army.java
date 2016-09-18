package ar.edu.itba.model;

public class Army {
	private Point position;
	private int idArmy;
	private boolean available;
	
	public Army(Point position, int idArmy, boolean available) {
		super();
		this.position = position;
		this.idArmy = idArmy;
		this.available = available;
	}

	public boolean isAvailable() {
		return available;
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
