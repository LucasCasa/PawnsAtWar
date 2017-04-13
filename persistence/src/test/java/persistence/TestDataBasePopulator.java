package persistence;


import ar.edu.itba.interfaces.AlertDao;
import ar.edu.itba.interfaces.ArmyDao;
import ar.edu.itba.interfaces.BuildingDao;
import ar.edu.itba.interfaces.CommerceDao;
import ar.edu.itba.interfaces.EmpireDao;
import ar.edu.itba.interfaces.MessageDao;
import ar.edu.itba.interfaces.ResourceDao;
import ar.edu.itba.interfaces.TroopDao;
import ar.edu.itba.interfaces.UserDao;
import ar.edu.itba.model.Army;
import ar.edu.itba.model.Point;
import ar.edu.itba.model.User;

public class TestDataBasePopulator{
	
	private ResourceDao rd;
	private CommerceDao cd;
	private UserDao ud;
	private ArmyDao ard;
	private TroopDao td;
	private MessageDao md;
	private EmpireDao ed;
	private AlertDao ald;
	private BuildingDao bd;

	public TestDataBasePopulator(UserDao ud,ResourceDao rd, CommerceDao cd) {
		this.ud = ud;
		this.rd = rd;
		this.cd = cd;
	}
	
	public TestDataBasePopulator(UserDao ud,ResourceDao rd) {
		this.ud = ud;
		this.rd = rd;
	}
	
	public TestDataBasePopulator(UserDao ud,AlertDao ald) {
		this.ud = ud;
		this.ald = ald;
	}
	
	

	public TestDataBasePopulator(ArmyDao ad, TroopDao td, UserDao ud) {
		this.td = td;
		this.ard = ad;
		this.ud = ud;
	}
	
	public TestDataBasePopulator(UserDao ud, MessageDao md) {
		this.ud = ud;
		this.md = md;
	}
	
	

	public TestDataBasePopulator(UserDao ud) {
		this.ud = ud;
	}

	public TestDataBasePopulator(UserDao ud, BuildingDao bd) {
		this.ud = ud;
		this.bd = bd;
	}

	public void populateEmpire(){
		
	}
	
	public void populateTroop(){
		User u;
		u = ud.create("maggie", "maggie", "mvega@itba.edu.ar");
		Army a = ard.addArmy(new Point(2,2), u, true);
		td.addTroop(a.getIdArmy(), 2, 100);
		td.addTroop(a.getIdArmy(), 1, 100);
		td.addTroop(a.getIdArmy(), 0, 100);
		a = ard.addArmy(new Point(2,0), u, false);
		td.addTroop(a.getIdArmy(), 2, 200);
		td.addTroop(a.getIdArmy(), 1, 200);
		td.addTroop(a.getIdArmy(), 0, 200);
		
	}
	
	public void populateMessage(){
		
	}
	
	public void populateBuilding(){
		
	}
	
	public void populateArmy(){
		User u;
		u = ud.create("maggie", "maggie", "mvega@itba.edu.ar");
		ard.addArmy(new Point(2,2), u, true);
		ard.addArmy(new Point(2,0), u, false);
	}
	
	public void populateAlert(){
		
	}

	public void populateResource() {
		User u;
		u = ud.create("maggie", "maggie", "mvega@itba.edu.ar");
		rd.addResource(u, 1, 1000);
		rd.addResource(u, 0, 1000);
		u = ud.create("maggie3", "maggie3", "mvega@itba.edu.ar");
		rd.addResource(u, 1, 1000);
		rd.addResource(u, 0, 1000);
	}
	
	public void populateUser(){
		ud.create("maggie", "maggie", "mvega@itba.edu.ar");
		ud.create("maggie3", "maggie3", "mvega@itba.edu.ar");
	}
	
	public void populateCommerce(){
		User u;
		u = ud.create("maggie", "maggie", "mvega@itba.edu.ar");
		rd.addResource(u, 1, 1000);
		rd.addResource(u, 0, 1000);
		u = ud.create("maggie3", "maggie3", "mvega@itba.edu.ar");
		rd.addResource(u, 1, 1000);
		rd.addResource(u, 0, 1000);
		
		cd.createOffer(u, 1, 100, 0, 100);
		cd.createOffer(u, 0, 1000, 1, 10);
	}

}
