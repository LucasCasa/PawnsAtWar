package ar.edu.itba.interfaces;

import java.util.List;

import ar.edu.itba.model.Point;
import ar.edu.itba.model.Sector;

public interface SectorService {
	
	public List<List<Sector>>getSector(Point p, int range);
	
	public Sector getSector(Point p);

	public void levelUp(Point p);

	public Integer getIdPlayer(Point p);

	public void setIdPlayer(Point p,int idPlayer);

	public int getType(Point p);

	public List<Sector> getAllBuildings(int idPlayer);
	
	public void deleteBuilding(Point p);

	public boolean belongsTo(Point p, int idPlayer);
	
	public boolean isCastleAlone(Point p, int range);

	public boolean createCastle(int userid);

	public Point getCastle(int idPlayer);
	
	public Point addCastle(int idPlayer);

	public int getPrice(Point point,int userId);

	public void addBuilding(Point p, int idPlayer, int type);

}
