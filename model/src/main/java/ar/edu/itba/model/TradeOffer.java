package ar.edu.itba.model;

<<<<<<< HEAD
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
=======
>>>>>>> d9433c89c73caca8960c804bdb6b8b63df0fe4cf

public class TradeOffer {
	
	private int id;
<<<<<<< HEAD
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name="idPlayer")
	private User owner;
	@Column(name = "offertype",nullable = false)
	private int offertype;
	@Column(name = "offeramount",nullable = false)
	private int offeramount;
	@Column(name = "receivetype",nullable = false)
	private int receivetype;
	@Column(name = "receiveamount", nullable = false)
	private int receiveamount;
=======
	private User owner;
	private Resource offers;
	private Resource receives;
>>>>>>> d9433c89c73caca8960c804bdb6b8b63df0fe4cf
	
	
	public TradeOffer(final User owner,final int offertype,final int offeramount,  final int receivetype, final int receiveamount){
		this.owner = owner;
		this.offeramount= offeramount;
		this.offertype = offertype;
		this.receiveamount = receiveamount;
		this.receivetype = receivetype;
	}

	/* package */ TradeOffer(){
		
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
	
	public int getOfferAmount() {
		return offeramount;
	}

	public int getOfferType() {
		return offertype;
	}

	public int getReceiveType() {
		return receivetype;
	}

	public int getReceiveAmount() {
		return receiveamount;
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
