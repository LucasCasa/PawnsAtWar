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
	
	/* package */ Troop(){
		
	}
	
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
<<<<<<< HEAD
	
	public Army getArmy() {
		return army;
=======
	public int getidArmy() {
		return idArmy;
>>>>>>> d9433c89c73caca8960c804bdb6b8b63df0fe4cf
	}
	
	public int getType() {
		return type;
	}
	
	
}
