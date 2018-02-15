package ar.edu.itba.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.itba.interfaces.ArmyService;
import ar.edu.itba.interfaces.TroopDao;
import ar.edu.itba.interfaces.TroopService;
import ar.edu.itba.model.Troop;

@Service
@Transactional
public class TroopServiceImpl implements TroopService {
	private static final double POW0 = 1;
	private static final double POW1 = 2;
	private static final double POW2 = 3;

	private static final int MAX_TROOP = 3;

	@Autowired
	private TroopDao td;

	@Autowired
	private ArmyService as;

	@Override
	public List<Troop> getTroopById(int idArmy) {
		return td.getAllTroop(idArmy);
	}

	@Override
	public int getAmount(int idArmy, int type) {
		if(type >= 0 && type < MAX_TROOP)
			return td.getAmount(idArmy, type);
		return -1;
	}

	@Override
	public void changeAmount(int idArmy, int type, int amount) {
			td.changeAmount(idArmy, type, amount);
	}

	@Override
	public void deleteTroop(int idArmy, int type) {
		if(td.getTroop(idArmy, type) == null)
			return;
		if(type >= 0 && type < MAX_TROOP)
			td.deleteTroop(idArmy, type);
		if(td.getAmountTroops(idArmy) == 0){
			as.deleteArmy(idArmy);
		}

	}

  @Override
  public void deleteTroops(int idArmy) {
    if(as.getArmyById(idArmy) == null)
      return;
    td.deleteTroops(idArmy);
  }

  @Override
	public void addTroop(int idArmy, int type, int amount) {
		if(type < 0 || type >= MAX_TROOP || amount <= 0){
			return;
		}
		if(td.exists(idArmy,type)){
			td.changeAmount(idArmy, type, td.getAmount(idArmy, type) + amount);
			return;
		}
		td.addTroop(idArmy,type,amount);
	}

	@Override
	public void subtractTroop(int idArmy, int type, int amount){
		if(type < 0 || type >= MAX_TROOP || amount <= 0){
			return;
		}
		if(!td.exists(idArmy,type)){
			return;
		}
		int originalAmount = td.getAmount(idArmy, type);
		if(originalAmount <= amount){
			deleteTroop(idArmy, type);
			return;
		}
		td.changeAmount(idArmy, type, originalAmount-amount);
	}

	@Override
	public double getValue(int idArmy) {
		int amount0 = td.getAmount(idArmy, 0);
		int amount1 = td.getAmount(idArmy, 1);
		int amount2 = td.getAmount(idArmy, 2);
		return POW0 * (amount0 == -1 ? 0:amount0) + POW1 *(amount1 == -1 ? 0:amount1) + POW2 * (amount2 == -1 ? 0:amount2);
	}

	public void setTroopDao(TroopDao troopDao){
		this.td = troopDao;
	}

	public void setArmyService(ArmyService armyService){
		this.as = armyService;
	}



}
