package persistence;


import ar.edu.itba.interfaces.ArmyDao;
import ar.edu.itba.interfaces.CommerceDao;
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
	private ArmyDao ad;
	private TroopDao td;

	public TestDataBasePopulator(UserDao ud,ResourceDao rd, CommerceDao cd) {
		this.ud = ud;
		this.rd = rd;
		this.cd = cd;
	}

	public TestDataBasePopulator(ArmyDao ad, TroopDao td, UserDao ud) {
		this.td = td;
		this.ad = ad;
		this.ud = ud;
	}

	public void populate() {
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
	
	public void populate2(){
		User u;
		u = ud.create("maggie", "maggie", "mvega@itba.edu.ar");
		Army a = ad.addArmy(new Point(2,2), u, true);
		td.addTroop(a.getIdArmy(), 2, 100);
		td.addTroop(a.getIdArmy(), 1, 100);
		td.addTroop(a.getIdArmy(), 0, 100);
		a = ad.addArmy(new Point(2,0), u, false);
		td.addTroop(a.getIdArmy(), 2, 200);
		td.addTroop(a.getIdArmy(), 1, 200);
		td.addTroop(a.getIdArmy(), 0, 200);
		
		
		
	}

}
