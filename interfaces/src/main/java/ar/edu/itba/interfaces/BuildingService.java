package ar.edu.itba.interfaces;


import java.util.List;

import ar.edu.itba.model.Building;
import ar.edu.itba.model.Point;

public interface BuildingService {
	
	public Integer getLevel(Point p);
	
	public void setLevel(Point p, int level);
	
	public void levelUp(Point p);
	
	public Integer getIdPlayer(Point p);
	
	public void setIdPlayer(Point p,int idPlayer);
	
	boolean belongsTo(Point p, int idPlayer);

	public Point getCastle(int idPlayer);
	
	public Point addCastle(int idPlayer);
	
	public List<Building> getAllBuildings(int idPlayer);

	public void addBuilding(Point p, int idPlayer, int type);
}
