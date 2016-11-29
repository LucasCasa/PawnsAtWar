package ar.edu.itba.interfaces;

import java.util.List;

import ar.edu.itba.model.Point;
import ar.edu.itba.model.Sector;
import ar.edu.itba.model.User;

public interface SectorService {
	
	public List<List<Sector>>getSector(Point p, int range);
	
	public Sector getSector(Point p);

	public void levelUp(Point p);

	public Integer getIdPlayer(Point p);

	public void setIdPlayer(Point p,User u);

	public List<Sector> getAllBuildings(User u);
	
	public void deleteBuilding(Point p);

	public boolean belongsTo(Point p,User u);
	
	public boolean isCastleAlone(Point p, int range);
	
	public void updateTerrain(Point p, User u,int range);

	public boolean createCastle(User u);

	public Point getCastle(User u);
	
	public Point addCastle(User u);

	public int getPrice(User u);
	
	public int getCastlePrice(User u);

	public void addBuilding(Point p, User u, int type);

	public List<Point> getAvailableSpots();
}
