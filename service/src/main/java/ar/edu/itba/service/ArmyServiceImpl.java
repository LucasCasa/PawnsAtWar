package ar.edu.itba.service;

import java.util.List;
import java.util.Map;

import ar.edu.itba.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.itba.interfaces.ArmyDao;
import ar.edu.itba.interfaces.ArmyService;
import ar.edu.itba.interfaces.TroopService;

@Service
@Transactional
public class ArmyServiceImpl implements ArmyService {
	
	@Autowired
	private ArmyDao ad;
	
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

	@Override
	public void mergeArmies(int from, int to){
		List<Troop> troops = ts.getTroopById(from);
		for(Troop troop : troops){
			ts.addTroop(to,troop.getType(),troop.getQuantity());
		}
		deleteArmy(from);
	}
	@Override
	public void moveArmy(int armyId, Point p){
		ad.setArmyPosition(armyId,p);
	}
	@Override
	public Army splitArmy(int armyId, Map<TroopType,Integer> troops){
		Army a = getArmyById(armyId);
		Army newa = ad.addArmy(a.getPosition(),a.getUser(),false);
		for(Map.Entry<TroopType,Integer> e : troops.entrySet()){
			ts.addTroop(newa.getIdArmy(),e.getKey().getType(),e.getValue());
            ts.subtractTroop(armyId,e.getKey().getType(),e.getValue());
		}
		return newa;
	}


}
