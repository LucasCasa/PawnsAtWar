package ar.edu.itba.model;

public class User {
	private int id;
	private String name;
	private String password;
	private String email;

<<<<<<< HEAD
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
=======
	public User(int id, String name, String password, String email) {
		this.id = id;
		this.name = name;
		this.password = password;
		this.email = email;
	}
>>>>>>> d9433c89c73caca8960c804bdb6b8b63df0fe4cf
	
	@OneToMany(fetch = FetchType.LAZY, orphanRemoval = false, mappedBy = "to")
	private List<Message> receivedMessages;
	
	@OneToMany(fetch = FetchType.LAZY, orphanRemoval = false, mappedBy = "from")
	private List<Message> sentMessages;
	
	@OneToMany(fetch = FetchType.LAZY, orphanRemoval = false, mappedBy = "userAlert")
	private List<Alert> alerts;
	
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
	
<<<<<<< HEAD
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
	
	public List<Message> getReceivedMessages(){
		return receivedMessages;
	}
	
	public List<Message> getSentMessages(){
		return sentMessages;
	}

	public List<Alert> getAlerts() {
		return alerts;
	}
=======
	
	
	
>>>>>>> d9433c89c73caca8960c804bdb6b8b63df0fe4cf

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
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


}
