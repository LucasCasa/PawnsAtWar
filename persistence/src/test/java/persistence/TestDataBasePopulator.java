package persistence;


import java.util.Date;

import ar.edu.itba.interfaces.AlertDao;
import ar.edu.itba.interfaces.ArmyDao;
import ar.edu.itba.interfaces.BuildingDao;
import ar.edu.itba.interfaces.CommerceDao;
import ar.edu.itba.interfaces.MessageDao;
import ar.edu.itba.interfaces.ResourceDao;
import ar.edu.itba.interfaces.TroopDao;
import ar.edu.itba.interfaces.UserDao;
import ar.edu.itba.model.AlertType;
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
	private AlertDao ald;
	private BuildingDao bd;

	//Empire
	public TestDataBasePopulator(UserDao ud,ResourceDao rd, CommerceDao cd) {
		this.ud = ud;
		this.rd = rd;
		this.cd = cd;
	}
	
	
	//Resource
	public TestDataBasePopulator(UserDao ud,ResourceDao rd) {
		this.ud = ud;
		this.rd = rd;
	}
	
	//Alert
	public TestDataBasePopulator(UserDao ud,AlertDao ald) {
		this.ud = ud;
		this.ald = ald;
	}
	
	//Troop
	public TestDataBasePopulator(ArmyDao ad, TroopDao td, UserDao ud) {
		this.td = td;
		this.ard = ad;
		this.ud = ud;
	}
	
	//Message
	public TestDataBasePopulator(UserDao ud, MessageDao md) {
		this.ud = ud;
		this.md = md;
	}
	
	//User
	public TestDataBasePopulator(UserDao ud) {
		this.ud = ud;
	}

	//Building
	public TestDataBasePopulator(UserDao ud, BuildingDao bd) {
		this.ud = ud;
		this.bd = bd;
	}
	
	//Army
	public TestDataBasePopulator(UserDao ud, ArmyDao ad) {
		this.ud = ud;
		this.ard = ad;
	}
	
	//Commerce
	public TestDataBasePopulator(UserDao ud, CommerceDao cd) {
		this.ud = ud;
		this.cd = cd;
	}
	
	public void populateTroop(){
		User u;
		u = ud.create("maggie", "maggie", "mvega@itba.edu.ar");
		Army a = ard.addArmy(new Point(2,2), u, true);
		td.addTroop(a.getIdArmy(), 0, 100);
		a = ard.addArmy(new Point(2,0), u, false);
		td.addTroop(a.getIdArmy(), 2, 200);
		td.addTroop(a.getIdArmy(), 1, 200);
		td.addTroop(a.getIdArmy(), 0, 200);
		
	}
	
	public void populateMessage(){
		User u,u2,u3;
		u = ud.create("maggie", "maggie", "mvega@itba.edu.ar");
		u2 = ud.create("maggie2", "maggie2", "mvega@itba.edu.ar");
		u3 = ud.create("maggie3", "maggie3", "mvega@itba.edu.ar");
		md.createMessage(u, u2, "Mensaje1", "Hola");
		md.createMessage(u2, u, "Mensaje2", "Hola");
		md.createMessage(u, u3, "Mensaje3", "Hola");
		
	}
	
	public void populateBuilding(){
		User u = ud.create("maggie", "maggie", "mvega@itba.edu.ar");

		bd.addBuilding(new Point (1,1), u, 5);
		bd.addBuilding(new Point (1,2), u, 0);
		bd.addBuilding(new Point (1,3), u, 0);
		bd.addBuilding(new Point (1,4), u, 0);
		bd.addBuilding(new Point (1,5), u, 0);
		bd.addBuilding(new Point (2,1), u, 0);
		bd.addBuilding(new Point (2,2), u, 0);
		bd.addBuilding(new Point (2,3), u, 0);
		bd.addBuilding(new Point (2,4), u, 0);
		bd.addBuilding(new Point (2,5), u, 0);
		bd.addBuilding(new Point (3,1), u, 5);
		bd.addBuilding(new Point (3,2), u, 0);
		bd.addBuilding(new Point (3,3), u, 1);
		bd.addBuilding(new Point (3,4), u, 0);
		bd.addBuilding(new Point (3,5), u, 4);
		bd.addBuilding(new Point (4,1), u, 0);
		bd.addBuilding(new Point (4,2), u, 0);
		bd.addBuilding(new Point (4,3), u, 5);
		bd.addBuilding(new Point (4,4), u, 0);
		bd.addBuilding(new Point (4,5), u, 0);
		bd.addBuilding(new Point (5,1), u, 0);
		bd.addBuilding(new Point (5,2), u, 0);
		bd.addBuilding(new Point (5,3), u, 5);
		bd.addBuilding(new Point (5,4), u, 0);
		bd.addBuilding(new Point (5,5), u, 0);

		
	}
	
	public void populateArmy(){
		User u;
		u = ud.create("maggie", "maggie", "mvega@itba.edu.ar");
		ard.addArmy(new Point(2,2), u, true);
		ard.addArmy(new Point(2,0), u, false);
	}
	
	public void populateAlert(){
		User u;
		u = ud.create("maggie", "maggie", "mvega@itba.edu.ar");
		ald.createAlert(u, "Alert2", new Date(), AlertType.BUILD.toString(), new Point(0,1), 0, 0);
		ald.createAlert(u, "Alert1", new Date(), AlertType.ATTACK.toString(), new Point(0,0), 0, 0);
	}

	public void populateResource() {
		User u;
		u = ud.create("maggie", "maggie", "mvega@itba.edu.ar");
		rd.addResource(u, 1, 1000);
		rd.addResource(u, 0, 1000);
		u = ud.create("maggie3", "maggie3", "mvega@itba.edu.ar");
	}
	
	public void populateUser(){
		ud.create("maggie", "maggie", "mvega@itba.edu.ar");
		ud.create("maggie3", "maggie3", "mvega@itba.edu.ar");
	}
	
	public void populateCommerce(){
		User u;
		u = ud.create("maggie", "maggie", "mvega@itba.edu.ar");
		cd.createOffer(u, 1, 100, 0, 100);
		cd.createOffer(u, 0, 1000, 1, 10);
		u = ud.create("maggie3", "maggie3", "mvega@itba.edu.ar");
		cd.createOffer(u, 1, 100, 0, 100);
		cd.createOffer(u, 0, 1000, 1, 10);
	}

}
