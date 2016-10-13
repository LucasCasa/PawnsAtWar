package ar.edu.itba.interfaces;


import ar.edu.itba.model.Building;
import ar.edu.itba.model.Point;

public interface BuildingService {
	
	public Integer getLevel(Point p);
	
	public void setLevel(Point p, int level);
	
	public void levelUp(Point p);
	
	public Integer getIdPlayer(Point p);
	
	public void setIdPlayer(Point p,int idPlayer);
	
	public Building addBuilding(Point p, int level, int idPlayer, int type);
	
	public Building addBuilding(Point p, int idPlayer, int type);
	
	boolean belongsTo(Point p, int idPlayer);

	public Point getCastle(int idPlayer);

	public void addCastle(int userid);
}
