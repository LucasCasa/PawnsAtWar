package ar.edu.itba.model;

public class Resources {
	private int type;
	private int quantity;
	
	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public int getType() {
		return type;
	}


	public Resources(int type, int quantity) {
		this.type = type;
		this.quantity = quantity;
	}
	
	
	
}
