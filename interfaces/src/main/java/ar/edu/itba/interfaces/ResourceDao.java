package ar.edu.itba.interfaces;

import java.util.List;

import ar.edu.itba.model.Resource;

public interface ResourceDao {

	public int addAmount(int idPlayer,int type, int value);
	public int subtractAmount (int idPlayer, int type, int value);
	public int getAmount(int idPlayer,int type);
	public Resource addResource (int idPlayer,int type, int amount);
	public Resource addResource (int idPlayer,int type);
	public List<Resource> getResources(int idPlayer);
}
