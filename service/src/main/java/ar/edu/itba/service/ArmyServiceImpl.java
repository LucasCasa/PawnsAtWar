package ar.edu.itba.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.itba.interfaces.ArmyDao;
import ar.edu.itba.interfaces.ArmyService;
import ar.edu.itba.interfaces.EmpireService;
import ar.edu.itba.interfaces.TroopService;
import ar.edu.itba.model.Army;
import ar.edu.itba.model.Point;
import ar.edu.itba.model.User;

@Service
@Transactional
public class ArmyServiceImpl implements ArmyService {
	
	@Autowired
	private ArmyDao ad;
	@Autowired
	private EmpireService es;
	
	@Autowired
	private TroopService ts;

	@Override
	public List<Army> getArmies(User u) {
		return ad.getArmiesByUserId(u);
	}

	@Override
	public Army getOrCreateArmy(Point p, User u) {
		if(ad.exists(p,u)){
			return ad.getArmy(p,u); 
		}
		return ad.addArmy(p, u);
	}

	@Override
	public Army getArmyById(int idArmy) {
		return ad.getArmyById(idArmy);
	}

	@Override
	public boolean belongs(User u, int idArmy) {
		return ad.belongs(u, idArmy);
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
	public Army getStrongest(User u) {
		Army strongest = null;
		double value = 0;
		List<Army> armies = ad.getArmiesByUserId(u);
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

	@Override
	public boolean trainTroops(User user, Point point, int amountTroops, int troopType) {
		Army ar = getOrCreateArmy(point,user );
		ts.addTroop(ar.getIdArmy(),troopType,amountTroops);
		return true;
	}

	@Override
	public Army getArmyAtPosition(User u, Point p) {
		return ad.getArmy(p,u);
	}


}
