package ar.edu.itba.interfaces;

import java.util.List;

import ar.edu.itba.model.Army;
import ar.edu.itba.model.Point;
import ar.edu.itba.model.User;

public interface ArmyService{
	
	public List<Army> getArmies(User u);
	
	public Army getOrCreateArmy(Point p, User u);
	
	public Army getArmyById(int idArmy);

	public boolean belongs(User u, int idArmy);

	public void setAvailable(int idArmy, boolean available);
	
	public void deleteArmy(int idArmy);
	
	public Army getStrongest(User u);
	
	
}
