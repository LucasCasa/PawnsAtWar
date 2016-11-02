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
@Table(name = "resource")
public class Resource {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "resource_id_seq")
	@SequenceGenerator(sequenceName = "resource_id_seq", name = "resource_id_seq", allocationSize = 1)
	@Column(name = "id")
	private long id;
	
	@Column(nullable = false, name = "type")
	private int type;
	
	@Column(nullable = false, name = "amount")
	private int quantity;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name="idPlayer")
	private User userResource;
	
	
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
