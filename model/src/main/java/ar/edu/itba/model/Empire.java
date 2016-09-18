package ar.edu.itba.model;

import java.sql.Time;
import java.util.List;

public class Empire {
	private User user;
	private List<Buildings> buildings;
	private List<Army> army;
	private List<Resources> resources;
	private Time lastUpdate;
	
	public Empire(User user, List<Buildings> buildings, List<Army> army, List<Resources> resources, Time lastUpdate) {
		this.user = user;
		this.buildings = buildings;
		this.army = army;
		this.resources = resources;
		this.lastUpdate = lastUpdate;
	}

	public Time getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(Time lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public User getUser() {
		return user;
	}

	public List<Buildings> getBuildings() {
		return buildings;
	}

	public List<Army> getArmy() {
		return army;
	}

	public List<Resources> getResources() {
		return resources;
	}
	
	public void addResources(Resources r){
		resources.add(r);
	}
	

	public void addBuilding(Buildings b){
		buildings.add(b);
	}
	

	public void addArmy(Army a){
		army.add(a);
	}
}

