package ar.edu.itba.interfaces;

import java.util.List;

import ar.edu.itba.model.Buildings;
import ar.edu.itba.model.Point;
import ar.edu.itba.model.User;

public interface BuildingDao {
	public Integer getLevel(Point p);
	
	public Integer setLevel(Point p);
	
	public List<Buildings> getBuildings(Point p, int range);
	
	public Buildings getBuilding(Point p);
	
	public Integer getIdPlayer(Point p);
	
	public Buildings setIdPlayer(Point p);
	
	public Buildings addBuilding(Point p, int level, int idPlayer, int type);
	
	public Buildings addBuilding(Point p, int idPlayer, int type);
	
	public boolean belongsTo(Point p, User u);
	
	
}
