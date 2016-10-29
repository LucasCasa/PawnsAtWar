package ar.edu.itba.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "commerce")
public class TradeOffer {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "commerce_tradeid_seq")
	@SequenceGenerator(sequenceName = "commerce_tradeid_seq", name = "commerce_tradeid_seq", allocationSize = 1)
	@Column(name = "tradeId")
	private int id;
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name="idPlayer")
	private User owner;
	@OneToOne(fetch = FetchType.LAZY)
	private Resource offers;
	@OneToOne(fetch = FetchType.LAZY)
	private Resource receives;
	
	public TradeOffer(final int id,final User owner,final Resource offers,final Resource receives){
		this.id = id;
		this.owner = owner;
		this.offers = offers;
		this.receives = receives;
	}
	
	public TradeOffer(final User owner,final Resource offers,final Resource receives){
		this.owner = owner;
		this.offers = offers;
		this.receives = receives;
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
