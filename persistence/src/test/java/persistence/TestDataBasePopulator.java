package persistence;


import ar.edu.itba.interfaces.BuildingDao;
import ar.edu.itba.interfaces.CommerceDao;
import ar.edu.itba.interfaces.ResourceDao;
import ar.edu.itba.interfaces.UserDao;
import ar.edu.itba.model.Resource;
import ar.edu.itba.model.Sector;
import ar.edu.itba.model.User;

public class TestDataBasePopulator{
	
	private ResourceDao rd;
	private BuildingDao bd;
	private CommerceDao cd;
	private UserDao ud;

	public TestDataBasePopulator(UserDao ud,ResourceDao rd, BuildingDao bd, CommerceDao cd) {
		this.rd = rd;
		this.bd = bd;
		this.cd = cd;
	}

	public void populate() {
		User u;
		Resource r;
		Sector b;
		u = ud.create("maggie", "maggie", "mvega@itba.edu.ar");
		r = rd.addResource(u, 1, 1000);
		r = rd.addResource(u, 0, 1000);
		u = ud.create("maggie3", "maggie3", "mvega@itba.edu.ar");
		r = rd.addResource(u, 1, 1000);
		r = rd.addResource(u, 0, 1000);
		
		cd.createOffer(u, 1, 100, 0, 100);
		cd.createOffer(u, 0, 1000, 1, 10);
		
	}

}
