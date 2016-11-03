package ar.edu.itba.interfaces;

import java.util.List;

import ar.edu.itba.model.Resource;
import ar.edu.itba.model.User;

public interface ResourceDao {

	public void addAmount(User u,int type, int value);
	public void subtractAmount (User u, int type, int value);
	public Resource addResource (User u,int type, int amount);
	public Resource addResource (User u,int type);
	public List<Resource> getResources(User u);
	public Resource getResource(User u,int type);
	public void deleteResource(User u, int type);

}
