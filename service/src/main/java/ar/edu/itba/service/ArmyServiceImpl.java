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
	public Army getOrCreateArmy(Point p, int idPlayer) {
		if(ad.exists(p,idPlayer)){
			return ad.getArmy(p,idPlayer); 
		}
		return ad.addArmy(p, idPlayer);
	}

	@Override
	public Army getArmyById(int idArmy) {
		return ad.getArmyById(idArmy);
	}

	@Override
	public boolean belongs(int userId, int idArmy) {
		return ad.belongs(userId, idArmy);
	}
	
	@Override
	public void setAvailable(int idArmy, boolean available){
		ad.setAvailable(idArmy, available);
	}


}
