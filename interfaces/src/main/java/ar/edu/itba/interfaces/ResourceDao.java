package ar.edu.itba.interfaces;

import java.util.List;

import ar.edu.itba.model.Resource;

public interface ResourceDao {

<<<<<<< HEAD
	public void addAmount(User u,int type, int value);
	public void subtractAmount (User u, int type, int value);
	public Resource addResource (User u,int type, int amount);
	public Resource addResource (User u,int type);
	public List<Resource> getResources(User u);
	public Resource getResource(User u,int type);
	public void deleteResource(User u, int type);
=======
	public void addAmount(int idPlayer,int type, int value);
	public void subtractAmount (int idPlayer, int type, int value);
	public Resource addResource (int idPlayer,int type, int amount);
	public Resource addResource (int idPlayer,int type);
	public List<Resource> getResources(int idPlayer);
	public Resource getResource(int idPlayer,int type);
	public void setAmount(int idPlayer, int type, int value);

>>>>>>> d9433c89c73caca8960c804bdb6b8b63df0fe4cf
}
