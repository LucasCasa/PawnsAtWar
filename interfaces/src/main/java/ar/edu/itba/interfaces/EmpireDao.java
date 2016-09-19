package ar.edu.itba.interfaces;

import java.sql.Timestamp;
import java.util.List;

import ar.edu.itba.model.Resource;

public interface EmpireDao {
	
	/**
	 * Getter for the last time the resources were updated in the database
	 * @return The last time resources were updated
	 */
	public Timestamp getLastTimeUpdate(int userId);
	
	/**
	 * Getter for the last time the resources were updated in the database
	 * @param t The time to be set
	 */
	public void setLastTimeUpdate(int userId,Timestamp t);
	
	/**
	 * Getter for the resource of type id
	 * @param id The id to be retrieved
	 * @return The resource with the id requested
	 */
	public Resource getResource(int userid, int id);
	
	/**
	 * Setter for the resource of type id
	 * @param id The id of the resource to be set
	 * @param amount The amount to be set
	 */
	public void setResource(int userId, int id, int amount);
	
	/**
	 * Getter for all the resources of a certain user
	 * @param userId
	 * @return
	 */
	public List<Resource> getResources(int userId);
}
