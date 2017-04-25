package persistence;

import ar.edu.itba.model.Point;
import ar.edu.itba.model.Sector;
import ar.edu.itba.model.User;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.itba.interfaces.BuildingDao;
import ar.edu.itba.interfaces.UserDao;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@Transactional
public class BuildingHibernateDaoTest {
	
	@Autowired private BuildingDao bd;
	@Autowired private UserDao ud;
	private TestDataBasePopulator populator;
	
	@BeforeClass
	public static void beforeClass(){
		
	}
	
	@Before
	@Transactional
	public void setUp(){
		populator = new TestDataBasePopulator(ud, bd);
		populator.populateBuilding();
	}
	
	@Test
	@Transactional
	public void testAddBuilding(){
		User u = ud.findByUsername("maggie");
		List<Sector> list = bd.getBuildings(new Point(3,3),2);
		assertEquals(list.size(),5*5);

		bd.addBuilding(new Point(3,6),1,u,0);
		List<Sector> list2 = bd.getBuildings(new Point(3,3),3);
		assertEquals(list2.size(),list.size() + 1);

		Sector s = bd.addBuilding(new Point(3,6),1, u, 0);
		assertEquals(bd.getBuildings(new Point(3,3),3).size(),list2.size());
		assertNull(s);

	 	s = bd.addBuilding(null,1, u, 0);
		assertEquals(bd.getBuildings(new Point(3,3),3).size(),list2.size());
		assertNull(s);

		s = bd.addBuilding(new Point(-3,6),1, u, 0);
		assertEquals(bd.getBuildings(new Point(3,3),3).size(),list2.size());
		assertNull(s);

		s = bd.addBuilding(new Point(3,-6),1, u, 0);
		assertEquals(bd.getBuildings(new Point(3,3),3).size(),list2.size());
		assertNull(s);

	}
	
	@Test
	@Transactional
	public void testGetBuilding(){
		List<Sector> list = bd.getBuildings(new Point(0,0),0);
		assertEquals(list.size(),0);

		list = bd.getBuildings(new Point(0,0),1);
		assertEquals(list.size(),1);

		list = bd.getBuildings(new Point(3,3),1);
		assertEquals(list.size(),9);

		list = bd.getBuildings(new Point(3,0),1);
		assertEquals(list.size(),3);

		list = bd.getBuildings(new Point(-1,1),0);
		assertEquals(list.size(),0);

		list = bd.getBuildings(new Point(1,-1),0);
		assertEquals(list.size(),0);

	}
	
	@Test
	@Transactional
	public void testDeleteBuilding(){
		User u = ud.findByUsername("maggie");
		List<Sector> list = bd.getBuildings(new Point(3,3),2);
		assertEquals(list.size(),5*5);

		bd.deleteBuilding(new Point(3,3));
		list = bd.getBuildings(new Point(3,3),2);
		assertEquals(list.size(),5*5-1);


	}
	
	@Test
	@Transactional
	public void testGetAllCastle(){
		User u = ud.findByUsername("maggie");
		List<Point> p = bd.getAllCastles(u);
		assertEquals(p.size(),1);
		assertEquals(p.get(0), new Point(3,3));

		bd.addBuilding(new Point(10,10),1,u,1);
		p = bd.getAllCastles(u);
		assertEquals(p.size(),2);

		p = bd.getAllCastles(null);
		assertNull(p);

	}
	

}
