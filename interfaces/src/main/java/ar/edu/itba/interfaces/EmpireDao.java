package ar.edu.itba.interfaces;

import java.sql.Timestamp;
import java.util.List;

import ar.edu.itba.model.Empire;
import ar.edu.itba.model.Point;
import ar.edu.itba.model.Resource;
import ar.edu.itba.model.Sector;
import ar.edu.itba.model.User;

public interface EmpireDao {
	
	/**
	 * Getter for the resource of type id
	 * @param id The id to be retrieved
	 * @return The resource with the id requested
	 */
	public Resource getResource(User u, int id);
	
	/**
	 * Setter for the resource of type id
	 * @param id The id of the resource to be set
	 * @param amount The amount to be set
	 */
	public void setResource(User u, int id, int amount);
	
	public Empire getByUser(User u);
	
	public List<Sector> getBuilding (Point p, int type);
	
	public Empire createEmpire(User u, Timestamp timestamp);
	
	public void createResource (User u,int type, int amount);

	public List<Sector> getBuilding(User u, int type);

	public void setLastUpdate(User u, Timestamp currentTime);

	public void deleteResource(User u, int type);

	public void deleteOffers(User u);

}
