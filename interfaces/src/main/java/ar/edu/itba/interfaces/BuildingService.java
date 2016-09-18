package ar.edu.itba.interfaces;


import ar.edu.itba.model.Buildings;
import ar.edu.itba.model.Point;
import ar.edu.itba.model.User;

public interface BuildingService {
public Integer getLevel(Point p);
	
	public Integer setLevel(Point p, int level);
	
	public Integer getIdPlayer(Point p);
	
	public Buildings setIdPlayer(Point p);
	
	public Buildings addBuilding(Point p, int level, int idPlayer, int type);
	
	public Buildings addBuilding(Point p, int idPlayer, int type);
	
	public boolean belongsTo(Point p, User u);
}
