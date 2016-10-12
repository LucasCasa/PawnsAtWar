package ar.edu.itba.model;


public class TradeOffer {
	
	private int id;
	private User owner;
	private Resource offers;
	private Resource receives;
	
	public TradeOffer(final int id,final User owner,final Resource offers,final Resource receives){
		this.id = id;
		this.owner = owner;
		this.offers = offers;
		this.receives = receives;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public User getOwner() {
		return owner;
	}
	public void setOwner(User owner) {
		this.owner = owner;
	}
	public Resource getOffer() {
		return offers;
	}
	public void setOffers(Resource offers) {
		this.offers = offers;
	}
	public Resource getReceives() {
		return receives;
	}
	public void setReceives(Resource receives) {
		this.receives = receives;
	}
	
	public int hashCode(){
		return this.id;
	}
	
	public boolean equals(Object other){
		if(other==null)
			return false;
		if(other.getClass()!=this.getClass())
			return false;
		TradeOffer o = (TradeOffer)other;
		if(o.id != this.id)
			return false;
		return true;
	}
}
