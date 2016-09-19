package ar.edu.itba.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.itba.interfaces.ArmyDao;
import ar.edu.itba.interfaces.ArmyService;
import ar.edu.itba.model.Army;
import ar.edu.itba.model.Point;

@Service
public class ArmyServiceImpl implements ArmyService {
	
	@Autowired
	private ArmyDao ad;

	@Override
	public List<Army> getArmies(int idPlayer) {
		return ad.getArmiesByUserId(idPlayer);
	}

	@Override
	public Army createArmy(Point p, int idPlayer) {
		return ad.addArmy(p, idPlayer);
	}

	@Override
	public Army getArmyById(int idArmy) {
		return ad.getArmyById(idArmy);
	}

}
