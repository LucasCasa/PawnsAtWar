package ar.edu.itba.interfaces;

import java.util.List;
import java.util.Map;

import ar.edu.itba.model.Army;
import ar.edu.itba.model.Point;
import ar.edu.itba.model.TroopType;
import ar.edu.itba.model.User;

public interface ArmyService{

	public List<Army> getArmies(User u);

	public Army getOrCreateArmy(Point p, User u);

	public Army getArmyById(int idArmy);

	public boolean belongs(User u, int idArmy);

	public void setAvailable(int idArmy, boolean available);

	public void deleteArmy(int idArmy);

	public boolean trainTroops(User user, Point point, int amountTroops, int troopType);

    Army getArmyAtPosition(User u, Point p);

    void mergeArmies(int from, int to);

	void moveArmy(int armyId, Point p);

    Army splitArmy(int armyId, Map<TroopType, Integer> troops);
}
