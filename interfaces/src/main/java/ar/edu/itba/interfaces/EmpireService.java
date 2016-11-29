package ar.edu.itba.interfaces;

import java.util.List;
import java.util.Map;
import java.util.Set;

import ar.edu.itba.model.Point;
import ar.edu.itba.model.Resource;
import ar.edu.itba.model.User;

public interface EmpireService {
	
	/**
	 * Obtains a list with all the resources associated with a certain empire
	 * @param userid The id of the user who owns the empire
	 * @return The list with all the resources
	 */
	public Set<Resource> getResources(User u);

	/**
	 * Attempts to build a building on the specified tile
	 * @param id The user's id
	 * @param xprime The x position of the tile
	 * @param yprime The y position of the tile
	 * @param typep The type of building
	 * @return If it was possible to build
	 */
	public boolean build(User u, int xprime, int yprime, int type);
	
	/**
	 * Returns the rate at which the empire produces certain resource type
	 * @param userid the users id
	 * @param type the type of resource
	 * @return the rate of resource produced per second
	 */
	public double getRate(User u, int type);
	
	/**
	 * Returns a map where the with Resource as key and the rate as value
	 * @param userid the users id
	 * @return A map <Resource,Rate>
	 */
	public Map<Resource,Double> getResourceMap(User u);
	
	/**
	 * Gets a list of the rates at which each respective resource is produced
	 * @param user the users id
	 * @return A list containing all rates
	 */
	public List<Double> getRates(User u);

	/**
	 * Returns the resource of type specified matching the user's id
	 * @param id The users id
	 * @param type The resource type
	 * @return The requested resource
	 */
	public Resource getResource(User u, int type);

	/**
	 * Increases the quantity of resource type of the user with id specified
	 * @param userid The user's id
	 * @param type The resource's type
	 * @param quantity The amount to be increased
	 */
	public void addResourceAmount(User u, int type, int quantity);

	/**
	 * Subtracts the quantity of resource type of the user with specified id if the quantity < available resources
     * @param userid The user's id
     * @param type The resource type
     * @param quantity The amount to be decreased
	 * @return true if the quantity > resources available
     */
	public boolean subtractResourceAmount(User u, int type, int quantity);

	/**
	 * Creates the empire, resources and castle of the user
	 * @param userid
	 * @return the point in which the castle is in
	 */
	public boolean createUser(User user,boolean newUser);

	public void deleteUser(User user);
	
	/**
	 * Returns whether a castle can be built there or not
	 * @param p The point
	 * @return True if can be built
	 */
	public boolean validCastlePosition(Point p);
	
	/**
	 * Calculates the empire's total score based upon existing buildings level
	 * @param user
	 * @return The score
	 */
	public long calculateScore(User user);

	public boolean createEmpire(User user);
	
	public boolean hasResourcesAvailable(User u, int amount, int resType);
	
	/**
	 * Returns the max amount of resources that can be stored
	 * @param u The user
	 * @return The max amount
	 */
	public int getMaxStorage(User u);
}
