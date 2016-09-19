package ar.edu.itba.interfaces;

import java.sql.Time;

import ar.edu.itba.model.Resources;

public interface EmpireDao {
	
	/**
	 * Getter for the last time the resources were updated in the database
	 * @return The last time resources were updated
	 */
	public Time getLastTimeUpdate();
	
	/**
	 * Getter for the last time the resources were updated in the database
	 * @param t The time to be set
	 */
	public void setLastTimeUpdate(Time t);
	
	/**
	 * Getter for the resource of type id
	 * @param id The id to be retrieved
	 * @return The resource with the id requested
	 */
	public Resources getResource(int id);
	
	/**
	 * Setter for the resource of type id
	 * @param id The id of the resource to be set
	 * @param amount The amount to be set
	 */
	public void setResource(int id, int amount);
}
