package ar.edu.itba.interfaces;

import java.util.List;

import ar.edu.itba.model.Army;
import ar.edu.itba.model.Point;
import ar.edu.itba.model.User;

public interface ArmyDao {
	
	public Army addArmy(Point position, User u, boolean available);
	
	public Army addArmy(Point position, User u);
	
	public List<Army> getArmiesByUserId (User u);
	
	public Army getArmyById(int idArmy);
	
	public boolean isAvailable(Point p);

	public boolean belongs(User u, int idArmy);

	public boolean exists(Point p, User u);

	public Army getArmy(Point p, User u);

	void setAvailable(int idArmy, boolean available);

	public void deleteArmy(int idArmy);

    void setArmyPosition(int armyId, Point p);
}
