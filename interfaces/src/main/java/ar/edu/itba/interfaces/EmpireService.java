package ar.edu.itba.interfaces;

import java.util.List;

import ar.edu.itba.model.Resource;

public interface EmpireService {
	
	/**
	 * Obtains a list with all the resources associated with a certain empire
	 * @param userid The id of the user who owns the empire
	 * @return The list with all the resources
	 */
	public List<Resource> getResources(int userid);

	/**
	 * Attempts to build a building on the specified tile
	 * @param id The user's id
	 * @param xprime The x position of the tile
	 * @param yprime The y position of the tile
	 * @param typep The type of building
	 * @return If it was possible to build
	 */
	public boolean build(int id, int xprime, int yprime, int type);
	
}
