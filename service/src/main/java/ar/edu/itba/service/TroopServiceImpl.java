package ar.edu.itba.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.itba.interfaces.TroopDao;
import ar.edu.itba.interfaces.TroopService;
import ar.edu.itba.model.Troop;

@Service
public class TroopServiceImpl implements TroopService {
	
	private static final int MAX_TROOP = 3;
	
	@Autowired
	TroopDao td;

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
		if(amount >= 0 || (type >=0 && type <MAX_TROOP))
			td.changeAmount(idArmy, type, amount);
	}

	@Override
	public void deleteTroop(int idArmy, int type) {
		if(type >= 0 && type < MAX_TROOP)
			td.deleteTroop(idArmy, type);	
	}

	@Override
	public Troop addTroop(int idArmy, int type, int amount) {
		if((type < 0 && type >= MAX_TROOP) || amount < 0){
			return null;
		}
		if(td.exists(idArmy,type)){
			td.changeAmount(idArmy, type, td.getAmount(idArmy, type) + amount);
			return td.getTroop(idArmy,type);
		}
		return td.addTroop(idArmy,type,amount);
	}
	

}
