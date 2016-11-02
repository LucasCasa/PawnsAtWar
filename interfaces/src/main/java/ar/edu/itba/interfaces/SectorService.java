package ar.edu.itba.interfaces;

import java.util.List;

import ar.edu.itba.model.Point;
import ar.edu.itba.model.Sector;
import ar.edu.itba.model.User;

public interface SectorService {
	
	public List<List<Sector>>getSector(Point p, int range);
	
	public Sector getSector(Point p);
<<<<<<< HEAD

	public void levelUp(Point p);

	public Integer getIdPlayer(Point p);

	public void setIdPlayer(Point p,User u);

	public List<Sector> getAllBuildings(User u);
	
	public void deleteBuilding(Point p);

	public boolean belongsTo(Point p,User u);
=======
	
	public void deleteBuilding(Point p);
>>>>>>> d9433c89c73caca8960c804bdb6b8b63df0fe4cf
	
	public boolean isCastleAlone(Point p, int range);

	public boolean createCastle(User u);

<<<<<<< HEAD
	public Point getCastle(User u);
	
	public Point addCastle(User u);

	public int getPrice(Point point,User u);

	public void addBuilding(Point p, User u, int type);

=======
	public void addBuilding(Point p, int idPlayer, int type);
>>>>>>> d9433c89c73caca8960c804bdb6b8b63df0fe4cf
}
