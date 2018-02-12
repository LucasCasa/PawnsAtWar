package ar.edu.itba.interfaces;

import java.util.List;

import ar.edu.itba.model.Troop;

public interface TroopDao {
	public int getAmount(int idArmy,int type);
	public List<Troop> getAllTroop(int idArmy);
	public void changeAmount(int idArmy,int type, int amount);
	public void deleteTroop (int idArmy, int type);

  void deleteTroops(int idArmy);

  public Troop addTroop(int idArmy, int type, int amount);
	public boolean exists(int idArmy, int type);
	public Troop getTroop(int idArmy, int type);
	public int getAmountTroops(int idArmy);

}
