package ar.edu.itba.interfaces;

import java.util.List;

import ar.edu.itba.model.Army;
import ar.edu.itba.model.Point;
import ar.edu.itba.model.User;

public interface ArmyService{
	public List<Army> getArmy(User u);
	public Army createArmy(Point p, User u);
	
}
