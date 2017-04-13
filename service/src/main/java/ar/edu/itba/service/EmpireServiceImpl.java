package ar.edu.itba.service;

import java.util.ArrayList;
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

import ar.edu.itba.interfaces.AlertService;
import ar.edu.itba.interfaces.ArmyService;
import ar.edu.itba.interfaces.CommerceService;
import ar.edu.itba.interfaces.EmpireDao;
import ar.edu.itba.interfaces.EmpireService;
import ar.edu.itba.interfaces.MessageService;
import ar.edu.itba.interfaces.ResourceDao;
import ar.edu.itba.interfaces.ScheduleService;
import ar.edu.itba.interfaces.SectorService;
import ar.edu.itba.model.Alert;
import ar.edu.itba.model.Army;
import ar.edu.itba.model.Point;
import ar.edu.itba.model.Resource;
import ar.edu.itba.model.Sector;
import ar.edu.itba.model.TradeOffer;
import ar.edu.itba.model.User;

@Service
@Transactional
public class EmpireServiceImpl implements EmpireService{
	
	public static final int INITIAL_VALUE = 2000;
	public static final int MIN_STORAGE = 3000;
	public static final int STORAGE_PER_LEVEL = 1000;
	
	@Autowired
	private EmpireDao ed;
	@Autowired
	private SectorService ss;
	@Autowired
	private ArmyService as;
	@Autowired
	private MessageService ms;
	@Autowired
	private CommerceService cs;
	@Autowired
	private ResourceDao rd;
	@Autowired
	private ScheduleService sh;
	@Autowired
	private AlertService als;

	@Override
	public Set<Resource> getResources(User u) {
		updateResources(u);
		Set<Resource> set = new TreeSet<Resource>(new Comparator<Resource>(){
			public int compare(Resource r1, Resource r2){
				return r1.getType()-r2.getType();
			}
		});
		List<Resource> l = rd.getResources(u);
		set.addAll(l);
		return set;
	}
	
	/**
	 * Updates all resources by calculating the previous amount plus the rate times the time lasped in
	 * seconds since the last update
	 * @param u The id of the user
	 */
	public void updateResources(User u){
		List<Resource> list = rd.getResources(u);
		int seconds = (int)timeLapsed(u);
		int max = getMaxStorage(u);
		for(Resource r: list){
			int cant = (int) (r.getQuantity() + seconds*getRate(u, r.getType()));
			ed.setResource(u, r.getType(), (max<cant?max:cant));
		}
	}
	
	/**
	 * Returns the max amount of resources that can be stored
	 * @param u The user
	 * @return The max amount
	 */
	public int getMaxStorage(User u) {
		int max = MIN_STORAGE;
		List<Sector> castles = ed.getBuilding(u, SectorServiceImpl.CASTLE);
		for(Sector c: castles){
			for(int i=1; i<= c.getLevel(); i++)
				max += (1000-10*(i-1)+Math.pow(i, 4));
		}
		return max;
	}

	/**
	 * Retrieves the time lapsed since last update and sets the new time
	 * @param userid The id of the user whose resources are queried
	 * @return The time lapsed in seconds
	 */
	public long timeLapsed(User u){
		Date oldTime = u.getEmpire().getLastUpdate();
		Date currentTime = new Date();
		ed.setLastUpdate(u,currentTime);

		return (currentTime.getTime()-oldTime.getTime())/1000;
	}

	@Override
	public boolean build(User u, int xprime, int yprime, int type) {
		final int resType = 1; /*Temporary*/
		final Point p = new Point(xprime,yprime);
		int value;
		User owner = ss.getSector(p).getUser();
		if(type == SectorServiceImpl.CASTLE){
			if(!validCastlePosition(p))
				return false;
			value = ss.getCastlePrice(u);
		}else{
			if(!u.equals(owner))
				return false;
			value = ss.getPrice(u);
		}
		if(!hasResourcesAvailable(u, value, resType) || (owner!=null && !owner.equals(u))){
			return false;
		}
		if(ss.getSector(p).getType() == 5 && type != 4){
			return false;
		}
		subtractResourceAmount(u, resType, value);
		sh.buildTask(u,new Point(xprime,yprime),type);
		return true;
	}
	
	public boolean validCastlePosition(Point p) {
		List<Alert> alerts = als.getBuildingConstructed(p);
		if(!alerts.isEmpty())
			return false;
		List<List<Sector>> l = ss.getSector(p,2);
		for(List<Sector> row: l){
			for(Sector s: row){
				if(s==null || s.getUser()!=null)
					return false;
			}
		}
		return true;
	}

	@Override
	public boolean hasResourcesAvailable(User u, int amount, int resType){
		int rate = 1; /*Temporary*/
		Resource l = ed.getResource(u,resType);
		int time = (int)timeLapsed(u);
		return l.getQuantity()+time*rate>=amount;
	}


	@Override
	public Map<Resource, Double> getResourceMap(User u) {
		Map<Resource,Double> map = new HashMap<>();
		List<Resource> l = u.getResources();
		for(Resource r: l){
			map.put(r, getRate(u,r.getType()));
		}
		return map;
	}
	
	@Override
	public List<Double> getRates(User u){
		List<Double> l = new ArrayList<>();
		for(Resource r: u.getResources()){
			l.add(getRate(u,r.getType()));
		}
		return l;
	}
	
	@Override
	public double getRate(User u, int type){
		List<Sector> list;

		double rate = 1;
		switch(type){
			case 0:
				list = ed.getBuilding(u, SectorServiceImpl.MILL);//food
				break;
			case 1:
				list = ed.getBuilding(u, SectorServiceImpl.GOLD);//gold
				break;
			default: return 1;
		}
		for(Sector b : list){
			rate += 0.1*b.getLevel();
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
		int amount = r.getQuantity() + quantity;
		int max =  getMaxStorage(u);
		if(amount > max)
			amount = max;
		ed.setResource(u, type, amount);
	}

	@Override
	public boolean subtractResourceAmount(User u, int type, int quantity) {
		updateResources(u);
		Resource r = ed.getResource(u, type);
		int quant = r.getQuantity();
		if(quant >= quantity){
			ed.setResource(u, type, quant - quantity);
			return true;
		}
		return false;
	}
	
	@Override
	public boolean createUser(User user, boolean newUser) {
		boolean resp = ss.createCastle(user);
		if(resp){
			if(!newUser)
				ed.setLastUpdate(user, new Date());
			if(user.getResources() == null || user.getResources().isEmpty()){
				ed.createResource(user, 0, INITIAL_VALUE);
				ed.createResource(user, 1, INITIAL_VALUE);
			}else{
				ed.setResource(user, 0, INITIAL_VALUE);
				ed.setResource(user, 1, INITIAL_VALUE);
			}
			
		}else{
			return false;
		}
		return true;
	}

	
	@Override
	public boolean createEmpire(User user){
		ed.createEmpire(user,new Date());
		return createUser(user,true);
	}

	@Override
	public void deleteUser(User user) {
		if(user == null){
			return;
		}
		ed.deleteResource(user,0);
		ed.deleteResource(user,1);
		ed.deleteOffers(user);
		for(Army a: user.getArmy()){
			as.deleteArmy(a.getIdArmy());
		}
		for(TradeOffer td: user.getCommerce()){
			cs.deleteOffer(td.getId());
		}
		ms.deleteMessages(user);
		
	}

	@Override
	public long calculateScore(User user) {
		long score = 10;
		for(Sector s: ed.getAllBuildings(user)){
			score += 10*(s.getLevel());
		}
		return score;
	}
	
	@Override
	public List<Sector> getBuilding(User u, int type){
		return ed.getBuilding(u, type);
	}

}
