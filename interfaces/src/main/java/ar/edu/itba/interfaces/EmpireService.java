package ar.edu.itba.interfaces;

import java.util.List;

import ar.edu.itba.model.Resource;

public interface EmpireService {
	
	/**
	 * Obtains a list with all the resources associated with a certain empire
	 * 
	 * @return The list with all the resources
	 */
	public List<Resource> getResources();
}
