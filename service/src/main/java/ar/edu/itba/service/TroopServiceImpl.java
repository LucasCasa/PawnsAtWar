package ar.edu.itba.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.itba.interfaces.TroopDao;
import ar.edu.itba.interfaces.TroopService;
import ar.edu.itba.model.Troop;

@Service
public class TroopServiceImpl implements TroopService {
	
	@Autowired
	TroopDao td;

	@Override
	public List<Troop> getTroopById(int idArmy) {
		return td.getAllTroop(idArmy);
	}

	@Override
	public int getAmount(int idArmy, int type) {
		return td.getAmount(idArmy, type);
	}

	@Override
	public void changeAmount(int idArmy, int type, int amount) {
		td.changeAmount(idArmy, type, amount);
	}

	@Override
	public void deleteTroop(int idArmy, int type) {
		td.deleteTroop(idArmy, type);	
	}

}
