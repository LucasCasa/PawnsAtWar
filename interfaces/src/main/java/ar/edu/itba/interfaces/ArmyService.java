package ar.edu.itba.interfaces;

import java.util.List;

import ar.edu.itba.model.Army;
import ar.edu.itba.model.Point;

public interface ArmyService{
	public List<Army> getArmies(int idPlayer);
	public Army createArmy(Point p, int idPlayer);
	
}
