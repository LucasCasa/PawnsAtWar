package ar.edu.itba.interfaces;

import java.util.List;
import java.util.Map;
import java.util.Set;

import ar.edu.itba.model.Resource;
import ar.edu.itba.model.User;

public interface EmpireService {
	
	/**
	 * Obtains a list with all the resources associated with a certain empire
	 * @param userid The id of the user who owns the empire
	 * @return The list with all the resources
	 */
	public Set<Resource> getResources(int userid);

	/**
	 * Attempts to build a building on the specified tile
	 * @param id The user's id
	 * @param xprime The x position of the tile
	 * @param yprime The y position of the tile
	 * @param typep The type of building
	 * @return If it was possible to build
	 */
	public boolean build(int id, int xprime, int yprime, int type);
	
	/**
	 * Returns the rate at which the empire produces certain resource type
	 * @param userid the users id
	 * @param type the type of resource
	 * @return the rate of resource produced per second
	 */
	public int getRate(int userid, int type);
	
	/**
	 * Returns a map where the with Resource as key and the rate as value
	 * @param userid the users id
	 * @return A map <Resource,Rate>
	 */
	public Map<Resource,Integer> getResourceMap(int userid);
	
	/**
	 * Gets a list of the rates at which each respective resource is produced
	 * @param userid the users id
	 * @return A list containing all rates
	 */
	public List<Integer> getRates(int userid);

	/**
	 * Returns the resource of type specified matching the user's id
	 * @param id The users id
	 * @param type The resource type
	 * @return The requested resource
	 */
	public Resource getResource(int id, int type);

	/**
	 * Increases the quantity of resource type of the user with id specified
	 * @param userid The user's id
	 * @param type The resource's type
	 * @param quantity The amount to be increased
	 */
	public void addResourceAmount(int userid, int type, int quantity);

	/**
	 * Subtracts the quantity of resource type of the user with specified id
	 * @param userid The user's id
	 * @param type The resource type
	 * @param quantity The amount to be decreased
	 */
	public void subtractResourceAmount(int userid, int type, int quantity);

	/**
	 * Creates the empire, resources and castle of the user
	 * @param user
	 * @return the point in which the castle is in
	 */
	public void createUser(User user);
	
	
}
