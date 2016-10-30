package ar.edu.itba.interfaces;

import java.util.List;

import ar.edu.itba.model.Point;
import ar.edu.itba.model.Sector;

public interface SectorService {
	
	public List<List<Sector>>getSector(Point p, int range);
	
	public Sector getSector(Point p);
	
	public void deleteBuilding(Point p);
	
	public boolean isCastleAlone(Point p, int range);

	public boolean createCastle(int userid);

	public void addBuilding(Point p, int idPlayer, int type);
}
