package ar.edu.itba.model;

public class Resource {
	private int type;
	private int quantity;
	private User user;

	
	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public int getType() {
		return type;
	}
	
	public User getUser() {
		return user;
	}
	
	public Resource(int type, int quantity){
		this.type = type;
		this.quantity = quantity;
	}

	public Resource(int type, User user, int quantity) {
		this.type = type;
		this.user = user;
		this.quantity = quantity;
	}
	
	@Override
	public String toString() {
		return "Type: " + type + " - Qty: " + quantity + "  | userid: " + user.getId();
	}
	
	
	
}
