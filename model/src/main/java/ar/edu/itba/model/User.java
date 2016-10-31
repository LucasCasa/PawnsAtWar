package ar.edu.itba.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "userpaw")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "userpaw_idplayer_seq")
	@SequenceGenerator(sequenceName = "userpaw_idplayer_seq", name = "userpaw_idplayer_seq", allocationSize = 1)
	@Column(name = "idPlayer")
	private int id;
	
	@Column(length = 100,nullable = false, unique = true, name = "username")
	private String name;
	
	@Column(length = 100,nullable = false, name = "password")
	private String password;
	
	@Column(length = 100,nullable = false, name = "email")
	private String email;

	@OneToMany(fetch = FetchType.EAGER, orphanRemoval = false, mappedBy = "userResource")
	private List<Resource> resources;
	
	@OneToMany(fetch = FetchType.LAZY, orphanRemoval = false, mappedBy = "owner")
	private List<TradeOffer> commerce;
	
	@OneToMany(fetch = FetchType.LAZY, orphanRemoval = false, mappedBy = "userArmy")
	private List<Army> army;
	
	@OneToOne(optional=false, mappedBy="userEmpire")
	private Empire empire;
	
	@OneToMany(fetch = FetchType.LAZY, orphanRemoval = false, mappedBy = "userBuilding")
	private List<Sector> sector;
	
	public User(String name,String password,String email){
		this.name=name;
		this.password=password;
		this.email=email;
	}
	
	/*package*/ User(){
	}
	
	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getPassword() {
		return password;
	}

	public String getEmail() {
		return email;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public void setPassword(String pass){
		this.password = pass;
	}
	
	public void setEmail(String email){
		this.email = email;
	}
	
	public List<Resource> getResources() {
		return resources;
	}

	public List<TradeOffer> getCommerce() {
		return commerce;
	}

	public List<Army> getArmy() {
		return army;
	}

	public Empire getEmpire() {
		return empire;
	}

	public List<Sector> getSector() {
		return sector;
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
		User other = (User) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "( username = " + this.name + " , id = " + this.id + " , email = " + this.email + " )";
	}

}
