package ar.edu.itba.interfaces;

import java.util.List;

import ar.edu.itba.model.Point;
import ar.edu.itba.model.Sector;
import ar.edu.itba.model.User;

public interface BuildingDao {
	
	public Integer getLevel(Point p);
	
	public void setLevel(Point p, int level);
	
	public List<Sector> getBuildings(Point p, int range);
	
	public Sector getBuilding(Point p);
	
	public Integer getIdPlayer(Point p);
	
	public void setIdPlayer(Point p,User u);
	
	public Sector addBuilding(Point p, int level, User u, int type);
	
	public Sector addBuilding(Point p, User u, int type);
	
	public boolean belongsTo(Point p, User u);
	
	public void deleteBuilding (Point p);
	
	public Point getCastle(User u);
	
	/**
	 * This method returns if there are any other buildings in the range provided surrounding the castle
	 * @param p; the point in which the castle is found
	 * @param range; the range to look for
	 * @return if there are any other buildings
	 */
	public boolean isCastleAlone (Point p, int range);

	public List<Sector> getBuildings(User u, int type);

	public List<Point> getAllCastles();

	public List<Sector> getBuildings(User u);
	
	public int getType(Point p);

	public void setType(Point p, int type);
	
}
