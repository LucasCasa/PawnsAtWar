package ar.edu.itba.interfaces;

import java.util.List;

import ar.edu.itba.model.Troop;

public interface TroopService {
	public List<Troop> getTroopById(int idArmy);
	
	public int getAmount (int idArmy, int type);
	
	public void changeAmount (int idArmy, int type, int amount);
	
	public void deleteTroop (int idArmy, int type);
	
	public Troop addTroop (int idArmy, int type, int amount);
		
}
