package ar.edu.itba.interfaces;

import java.util.List;

import ar.edu.itba.model.Resource;

public interface ResourceDao {

	public void addAmount(int idPlayer,int type, int value);
	public void subtractAmount (int idPlayer, int type, int value);
	public Resource addResource (int idPlayer,int type, int amount);
	public Resource addResource (int idPlayer,int type);
	public List<Resource> getResources(int idPlayer);
	public Resource getResource(int idPlayer,int type);
	public void setAmount(int idPlayer, int type, int value);

}
