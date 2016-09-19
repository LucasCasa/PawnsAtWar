package ar.edu.itba.interfaces;

import java.util.List;

import ar.edu.itba.model.Buildings;
import ar.edu.itba.model.Point;
import ar.edu.itba.model.Sector;

public interface BuildingDao {
	
	public Integer getLevel(Point p);
	
	public void setLevel(Point p, int level);
	
	public List<Sector> getBuildings(Point p, int range);
	
	public Sector getBuilding(Point p);
	
	public Integer getIdPlayer(Point p);
	
	public void setIdPlayer(Point p,int idPlayer);
	
	public Buildings addBuilding(Point p, int level, int idPlayer, int type);
	
	public Buildings addBuilding(Point p, int idPlayer, int type);
	
	public boolean belongsTo(Point p, int idPlayer);
	
	public void deleteBuilding (Point p);
	
	
}
