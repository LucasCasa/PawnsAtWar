package ar.edu.itba.model;

public class Troop {
	private Army army;
	private int type;
	private int quantity;
	
	public Troop(Army army, int type, int quantity) {
		this.army = army;
		this.type = type;
		this.quantity = quantity;
	}
	
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public Army getArmy() {
		return army;
	}
	public int getType() {
		return type;
	}
	
	
}
