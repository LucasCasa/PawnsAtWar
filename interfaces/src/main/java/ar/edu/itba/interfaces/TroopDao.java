package ar.edu.itba.interfaces;

import java.util.List;

import ar.edu.itba.model.Troop;

public interface TroopDao {
	public int getAmount(int idArmy,int type);
	public List<Troop> getAllTroop(int idArmy);
	public void changeAmount(int idArmy,int type, int amount);
	public void deleteTroop (int idArmy, int type);
	
}
