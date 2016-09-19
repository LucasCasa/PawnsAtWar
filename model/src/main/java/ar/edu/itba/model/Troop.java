package ar.edu.itba.model;

public class Troop {
	private int idArmy;
	private int type;
	private int quantity;
	
	public Troop(int idArmy, int type, int quantity) {
		this.idArmy = idArmy;
		this.type = type;
		this.quantity = quantity;
	}
	
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public int getidArmy() {
		return idArmy;
	}
	public int getType() {
		return type;
	}
	
	
}
