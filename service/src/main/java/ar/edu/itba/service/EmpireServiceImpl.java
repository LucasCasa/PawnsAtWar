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

import ar.edu.itba.interfaces.ArmyService;
import ar.edu.itba.interfaces.BuildingService;
import ar.edu.itba.interfaces.EmpireDao;
import ar.edu.itba.interfaces.EmpireService;
import ar.edu.itba.interfaces.SectorService;
<<<<<<< HEAD
import ar.edu.itba.interfaces.UserDao;
=======
import ar.edu.itba.model.Building;
>>>>>>> d9433c89c73caca8960c804bdb6b8b63df0fe4cf
import ar.edu.itba.model.Point;
import ar.edu.itba.model.Resource;

@Service
public class EmpireServiceImpl implements EmpireService{
	
	private static final int INITIAL_VALUE = 2000;
	
	@Autowired
	EmpireDao ed;
	@Autowired
	SectorService ss;
	@Autowired
<<<<<<< HEAD
=======
	BuildingService bs;
	@Autowired
>>>>>>> d9433c89c73caca8960c804bdb6b8b63df0fe4cf
	ArmyService as;
	@Autowired
	UserDao ud;
	

	@Override
	public Set<Resource> getResources(User u) {
		updateResources(u);
		Set<Resource> set = new TreeSet<Resource>(new Comparator<Resource>(){
			public int compare(Resource r1, Resource r2){
				return r1.getType()-r2.getType();
			}
		});
		set.addAll(u.getResources());
		return set;
	}
	
	/**
	 * Updates all resources by calculating the previous amount plus the rate times the time lasped in
	 * seconds since the last update
	 * @param u The id of the user
	 */
	public void updateResources(User u){
		List<Resource> list = u.getResources();
		int seconds = (int)timeLapsed(u);
		for(Resource r: list){
			int cant = r.getQuantity() + seconds*getRate(u, r.getType());
			ed.setResource(u, r.getType(), cant);
		}
	}
	
	/**
	 * Retrieves the time lapsed since last update and sets the new time
	 * @param userid The id of the user whose resources are queried
	 * @return The time lapsed in seconds
	 */
<<<<<<< HEAD
	public long timeLapsed(User u){
		Timestamp oldTime = u.getEmpire().getLastUpdate();
		Timestamp currentTime = new Timestamp(new Date().getTime());
		ed.setLastUpdate(u,currentTime);
		
=======
	private long timeLapsed(int userid){
		Timestamp oldTime = ed.getLastTimeUpdate(userid);
		Date date= new Date();
		Timestamp currentTime = new Timestamp(date.getTime());
		ed.setLastTimeUpdate(userid, currentTime);
>>>>>>> d9433c89c73caca8960c804bdb6b8b63df0fe4cf
		return (currentTime.getTime()-oldTime.getTime())/1000;
	}

	@Override
	public boolean build(User u, int xprime, int yprime, int type) {
		final int resType = 1; /*Temporary*/
		final Point p = new Point(xprime,yprime);
		final int value = ss.getPrice(p, u);
		if(!hasResourcesAvailable(u, value, resType)){
			return false;
		}
		if(ss.getSector(p).getType() == 5 && type != 4){
			return false;
		}
		updateResources(u);
		subtractResourceAmount(u, resType, value);
		ss.addBuilding(p, u, type);
		return true;
	}
	
	public boolean hasResourcesAvailable(User u, int amount, int resType){
		int rate = 1; /*Temporary*/
		Resource l = ed.getResource(u,resType);
		int time = (int)timeLapsed(u);
		return l.getQuantity()+time*rate>=amount;
	}


	@Override
	public Map<Resource, Integer> getResourceMap(User u) {
		Map<Resource,Integer> map = new HashMap<>();
		List<Resource> l = u.getResources();
		for(Resource r: l){
			map.put(r, getRate(u,r.getType()));
		}
		return map;
	}
	
	@Override
	public List<Integer> getRates(User u){
		List<Integer> l = new ArrayList<>();
		for(Resource r: u.getResources()){
			l.add(getRate(u,r.getType()));
		}
		return l;
	}
	
	@Override
<<<<<<< HEAD
	public int getRate(User u, int type){
		List<Sector> list;
=======
	public int getRate(int userid, int type){
		List<Building> list;
>>>>>>> d9433c89c73caca8960c804bdb6b8b63df0fe4cf
		int rate = 1;
		switch(type){
			case 0:
				list = ed.getBuilding(u, SectorServiceImpl.MILL);//food
				break;
			case 1:
				list = ed.getBuilding(u, SectorServiceImpl.GOLD);//gold
				break;
			default: return 1;
		}
		for(Building b : list){
			rate += b.getLevel();
		}
		return rate;
	}

	@Override
	public Resource getResource(User u, int type) {
		updateResources(u);
		return ed.getResource(u, type);
	}

	@Override
	public void addResourceAmount(User u, int type, int quantity) {
		updateResources(u);
		Resource r = ed.getResource(u, type);
		ed.setResource(u, type, r.getQuantity() + quantity);
	}

	@Override
	public void subtractResourceAmount(User u, int type, int quantity) {
		updateResources(u);
		Resource r = ed.getResource(u, type);
		int cant = r.getQuantity() <= quantity ? -1 : r.getQuantity() - quantity;
		if(cant != -1){
			ed.setResource(u,type,cant);
		}
	}
	
	@Override
<<<<<<< HEAD
	public void createUser(User user) {
		boolean resp = ss.createCastle(user);
=======
	public void createUser(int userid) {
		boolean resp = ss.createCastle(userid);
>>>>>>> d9433c89c73caca8960c804bdb6b8b63df0fe4cf
		if(resp){
			ed.createEmpire(userid, new Timestamp(Calendar.getInstance().getTime().getTime()));
		ed.createResource(userid, 0, INITIAL_VALUE);
		ed.createResource(userid, 1, INITIAL_VALUE);
		}
		
	}

	@Override
	public void deleteUser(User user) {
		ed.deleteResource(user,0);
		ed.deleteResource(user,1);
		ed.deleteOffers(user);
		
	}


}
