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
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "troop",uniqueConstraints=@UniqueConstraint(columnNames={"idArmy", "type"}))
public class Troop {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "troop_id_seq")
	@SequenceGenerator(sequenceName = "troop_id_seq", name = "troop_id_seq", allocationSize = 1)
	@Column(name = "id")
	private long id;

	@ManyToOne(fetch =FetchType.EAGER, optional = false)
	@JoinColumn(name="idArmy")
	private Army army;

	@Column(nullable = false, name = "type")
	private int type;
	@Column(nullable = false, name = "amount")
	private int quantity;

	public Troop(Army a, int type, int quantity) {
		this.army = a;
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

	public Army getArmy() {
		return army;
	}

	public int getType() {
		return type;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setArmy(Army army) {
		this.army = army;
	}

	public void setType(int type) {
		this.type = type;
	}
	
	

}
