package ar.edu.itba.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.itba.interfaces.ArmyDao;
import ar.edu.itba.interfaces.ArmyService;
import ar.edu.itba.interfaces.TroopService;
import ar.edu.itba.model.Army;
import ar.edu.itba.model.Point;

@Service
public class ArmyServiceImpl implements ArmyService {
	
	@Autowired
	private ArmyDao ad;
	
	@Autowired
	private TroopService ts;

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

	@Override
	public void deleteArmy(int idArmy) {
		if(ad.getArmyById(idArmy) != null)
			ad.deleteArmy(idArmy);
	}

	@Override
	public Army getStrongest(int userId) {
		Army strongest = null;
		double value = 0;
		List<Army> armies = ad.getArmiesByUserId(userId);
		for(Army a: armies){
			if(a.getAvailable()){
				double aux = ts.getValue(a.getIdArmy());
				if(new Double(aux).compareTo(value) > 0){
					value = aux;
					strongest = a;
				}
			}	
		}
		return strongest;
	}
	
	


}
