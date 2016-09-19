package ar.edu.itba.model;

public class Resource {
	private int type;
	private int quantity;
	private int idPlayer;

	
	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public int getType() {
		return type;
	}
	
	public int getPlayer() {
		return idPlayer;
	}

	public Resource(int type, int idPlayer, int quantity) {
		this.type = type;
		this.idPlayer = idPlayer;
		this.quantity = quantity;
	}
	
	
	
}
