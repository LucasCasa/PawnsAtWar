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

@Entity
@Table(name = "resource")
=======
>>>>>>> d9433c89c73caca8960c804bdb6b8b63df0fe4cf
public class Resource {
	private int type;
	private int quantity;
<<<<<<< HEAD
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name="idPlayer")
	private User userResource;
	
=======
	private User user;
>>>>>>> d9433c89c73caca8960c804bdb6b8b63df0fe4cf
	
	/* package */ Resource(){
		
	}

	
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
		return userResource;
	}
	
	public Resource(int type, int quantity){
		this.type = type;
		this.quantity = quantity;
	}

	public Resource(int type, User user, int quantity) {
		this.type = type;
		this.userResource = user;
		this.quantity = quantity;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Resource other = (Resource) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Type: " + type + " - Qty: " + quantity + "  | userid: " + userResource.getId();
	}
	
	
	
}
