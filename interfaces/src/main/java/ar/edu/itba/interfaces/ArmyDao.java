package ar.edu.itba.interfaces;

import java.util.List;

import ar.edu.itba.model.Army;
import ar.edu.itba.model.Point;

public interface ArmyDao {
	public Army addArmy(Point position, int idPlayer, boolean available);
	public Army addArmy(Point position, int idPlayer);
	public List<Army> getArmy (int userId);
	public boolean isAvailable(Point p);
	
	
}
