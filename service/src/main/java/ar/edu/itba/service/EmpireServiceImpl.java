package ar.edu.itba.service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.itba.interfaces.BuildingService;
import ar.edu.itba.interfaces.EmpireDao;
import ar.edu.itba.interfaces.EmpireService;
import ar.edu.itba.interfaces.SectorService;
import ar.edu.itba.model.Point;
import ar.edu.itba.model.Resource;

@Service
public class EmpireServiceImpl implements EmpireService{
	
	@Autowired
	EmpireDao ed;
	@Autowired
	SectorService ss;
	@Autowired
	BuildingService bs;
	
	//Production rate, temporary implementation
	public static final int rate = 1;

	@Override
	public List<Resource> getResources(int userid) {
		updateResources(userid);
		return ed.getResources(userid);
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
			ed.addAmount(userid, r.getType(), seconds*rate);
		}
	}
	
	/**
	 * Retrieves the time lapsed since last update and sets the new time
	 * @param userid The id of the user whose resources are queried
	 * @return The time lapsed in seconds
	 */
	private long timeLapsed(int userid){
		Timestamp oldTime = ed.getLastTimeUpdate(userid);
		Date date= new Date();
		Timestamp currentTime = new Timestamp(date.getTime());
		ed.setLastTimeUpdate(userid, currentTime);
		return (currentTime.getTime()-oldTime.getTime())/1000;
	}

	@Override
	public boolean build(int id, int xprime, int yprime, int type) {
		final int resType = 1; /*Temporary*/
		final Point p = new Point(xprime,yprime);
		if(!hasResourcesAvailable(id, 1000, resType)){
			return false;
		}
		if(ss.getSector(p).getType() == 5 && type != 4){
			return false;
		}
		updateResources(id);
		ed.substractAmount(id, resType, 1000);
		bs.addBuilding(p, id, type);
		return true;
	}
	
	private boolean hasResourcesAvailable(int userid, int amount, int resType){
		int rate = 1; /*Temporary*/
		Resource l = ed.getResource(userid, resType);
		int time = (int)timeLapsed(userid);
		return l.getQuantity()*time*rate>=amount;
	}

}
