package ar.edu.itba.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.itba.interfaces.ArmyService;
import ar.edu.itba.interfaces.EmpireDao;
import ar.edu.itba.interfaces.EmpireService;
import ar.edu.itba.interfaces.SectorService;
import ar.edu.itba.model.Point;
import ar.edu.itba.model.Resource;
import ar.edu.itba.model.Sector;
import ar.edu.itba.model.User;

@Service
@Transactional
public class EmpireServiceImpl implements EmpireService{
	
	private static final int INITIAL_VALUE = 2000;
	
	@Autowired
	EmpireDao ed;
	@Autowired
	SectorService ss;
	@Autowired
	SectorService bs;
	@Autowired
	ArmyService as;

	@Override
	public Set<Resource> getResources(int userid) {
		updateResources(userid);
		Set<Resource> set = new TreeSet<Resource>(new Comparator<Resource>(){
			public int compare(Resource r1, Resource r2){
				return r1.getType()-r2.getType();
			}
		});
		set.addAll(ed.getResources(userid));
		return set;
	}
	
	/**
	 * Updates all resources by calculating the previous amount plus the rate times the time lasped in
	 * seconds since the last update
	 * @param userid The id of the user
	 */
	private void updateResources(int userid){
		List<Resource> list = ed.getResources(userid);
		int seconds = (int)timeLapsed(userid);
		for(Resource r: list){
			ed.addAmount(userid, r.getType(), seconds*getRate(userid, r.getType()));
		}
	}
	
	/**
	 * Retrieves the time lapsed since last update and sets the new time
	 * @param userid The id of the user whose resources are queried
	 * @return The time lapsed in seconds
	 */
	private long timeLapsed(int userid){
		Timestamp oldTime = ed.getLastTimeUpdate(userid);
		Timestamp currentTime = new Timestamp(new Date().getTime());
		ed.setLastTimeUpdate(userid, currentTime);
		return (currentTime.getTime()-oldTime.getTime())/1000;
	}

	@Override
	public boolean build(int id, int xprime, int yprime, int type) {
		final int resType = 1; /*Temporary*/
		final Point p = new Point(xprime,yprime);
		final int value = bs.getPrice(p, id);
		if(!hasResourcesAvailable(id, value, resType)){
			return false;
		}
		if(ss.getSector(p).getType() == 5 && type != 4){
			return false;
		}
		updateResources(id);
		ed.substractAmount(id, resType, value);
		ss.addBuilding(p, id, type);
		return true;
	}
	
	private boolean hasResourcesAvailable(int userid, int amount, int resType){
		int rate = 1; /*Temporary*/
		Resource l = ed.getResource(userid, resType);
		int time = (int)timeLapsed(userid);
		return l.getQuantity()+time*rate>=amount;
	}
	
	@Override
	public int getRate(int userid, int type){
		List<Sector> list;
		int rate = 1;
		switch(type){
			case 0:
				list = ed.getBuilding(userid, SectorServiceImpl.MILL);//food
				break;
			case 1:
				list = ed.getBuilding(userid, SectorServiceImpl.GOLD);//gold
				break;
			default: return 1;
		}
		for(Sector b : list){
			rate += b.getLevel();
		}
		return rate;
	}

	@Override
	public Map<Resource, Integer> getResourceMap(int userid) {
		Map<Resource,Integer> map = new HashMap<>();
		List<Resource> l = ed.getResources(userid);
		for(Resource r: l){
			map.put(r, getRate(userid,r.getType()));
		}
		return map;
	}
	
	@Override
	public List<Integer> getRates(int userid){
		List<Integer> l = new ArrayList<>();
		for(Resource r: ed.getResources(userid)){
			l.add(getRate(userid,r.getType()));
		}
		return l;
	}

	@Override
	public Resource getResource(int id, int type) {
		updateResources(id);
		return ed.getResource(id, type);
	}

	@Override
	public void addResourceAmount(int userid, int type, int quantity) {
		updateResources(userid);
		ed.addAmount(userid, type, quantity);
	}

	@Override
	public void subtractResourceAmount(int userid, int type, int quantity) {
		updateResources(userid);
		ed.substractAmount(userid, type, quantity);
	}
	
	@Override
	public void createUser(User user) {
		boolean resp = ss.createCastle(user.getId());
		if(resp){
			ed.createEmpire(user, new Timestamp(Calendar.getInstance().getTime().getTime()));
		ed.createResource(user, 0, INITIAL_VALUE);
		ed.createResource(user, 1, INITIAL_VALUE);
		}
		
	}


}
