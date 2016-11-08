package ar.edu.itba.model;

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


@Entity
@Table(name = "commerce")
public class TradeOffer {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "commerce_tradeid_seq")
	@SequenceGenerator(sequenceName = "commerce_tradeid_seq", name = "commerce_tradeid_seq", allocationSize = 1)
	@Column(name = "tradeId")
	private int id;
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name="ownerId")
	private User owner;
	@Column(name = "offertype",nullable = false)
	private int offertype;
	@Column(name = "offeramount",nullable = false)
	private int offeramount;
	@Column(name = "receivetype",nullable = false)
	private int receivetype;
	@Column(name = "receiveamount", nullable = false)
	private int receiveamount;



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

	
	public int getOffertype() {
		return offertype;
	}

	public int getOfferamount() {
		return offeramount;
	}

	public int getReceivetype() {
		return receivetype;
	}

	public int getReceiveamount() {
		return receiveamount;
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
