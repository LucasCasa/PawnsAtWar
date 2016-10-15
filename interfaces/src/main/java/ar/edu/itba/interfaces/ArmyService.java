package ar.edu.itba.interfaces;

import java.util.List;

import ar.edu.itba.model.Army;
import ar.edu.itba.model.Point;

public interface ArmyService{
	
	public List<Army> getArmies(int idPlayer);
	
	public Army getOrCreateArmy(Point p, int idPlayer);
	
	public Army getArmyById(int idArmy);

	public boolean belongs(int userId, int idArmy);

	public void setAvailable(int idArmy, boolean available);
	
	public void deleteArmy(int idArmy);
	
	
}
