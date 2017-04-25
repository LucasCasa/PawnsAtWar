package persistence;

import ar.edu.itba.model.Alert;
import ar.edu.itba.model.AlertType;
import ar.edu.itba.model.Point;
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

import ar.edu.itba.interfaces.AlertDao;
import ar.edu.itba.interfaces.UserDao;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@Transactional
public class AlertHibernateDaoTest {
	@Autowired private AlertDao ad;
	@Autowired private UserDao ud;
	private TestDataBasePopulator populator;
	
	@BeforeClass
	public static void beforeClass(){
		
	}
	
	@Before
	@Transactional
	public void setUp(){
		populator = new TestDataBasePopulator(ud, ad);
		populator.populateAlert();
	}

	@Test
	@Transactional
	public void testCreate(){
		User u;
		u = ud.findByUsername("maggie");
		assertEquals(ad.getByUser(u).size(),2);

		ad.createAlert(u,"Alert3", new Date(), AlertType.MERGE.toString(), new Point(3,3),0,0);
		ad.createAlert(u,"Alert4", new Date(), AlertType.MOVE.toString(), new Point(2,3),0,0);

		assertEquals(ad.getByUser(u).size(),4);

		ad.createAlert(null,"Alert3", new Date(), AlertType.MERGE.toString(), new Point(3,3),0,0);
		assertNotEquals(ad.getByUser(u).size(),5);

		ad.createAlert(u,null, new Date(), AlertType.MERGE.toString(), new Point(3,3),0,0);
		assertNotEquals(ad.getByUser(u).size(),5);

		ad.createAlert(u,"Alert3", null, AlertType.MERGE.toString(), new Point(3,3),0,0);
		assertNotEquals(ad.getByUser(u).size(),5);

		ad.createAlert(u,"Alert3", new Date(), null, new Point(3,3),0,0);
		assertNotEquals(ad.getByUser(u).size(),5);

		ad.createAlert(u,"Alert3", new Date(), AlertType.MERGE.toString(), null,0,0);
		assertNotEquals(ad.getByUser(u).size(),5);

		ad.createAlert(u,"Alert3", new Date(), AlertType.MERGE.toString(), new Point(3,3),null,0);
		ad.createAlert(u,"Alert3", new Date(), AlertType.MERGE.toString(), new Point(3,3),0,null);
		assertEquals(ad.getByUser(u).size(),6);


	}



	
	@Test
	@Transactional
	public void testRemove(){
		User u = ud.findByUsername("maggie");
		assertEquals(ad.getByUser(u).size(),2);

		List<Alert> list = ad.getByUser(u);
		Alert a = list.get(0);
		assertEquals(list.size(),2);

		ad.removeAlert(a);
		assertEquals(ad.getByUser(u).size(),1);

		ad.removeAlert(a);
		assertEquals(ad.getByUser(u).size(),1);

		list = ad.getByUser(u);
		ad.removeAlert(list.get(0));
		assertEquals(ad.getByUser(u).size(),0);
		
	}
	
	@Test
	@Transactional
	public void testGetAll(){
		User u2 = ud.create("maggie2","maggie","mvega@itba.edu.ar");
		ad.createAlert(u2,"Alert5",new Date(),AlertType.RECRUIT.toString(),new Point(4,4),2,2);
		ad.createAlert(u2,"Alert4",new Date(),AlertType.UPGRADE.toString(),new Point(3,4),1,2);

		assertEquals(ad.getAllAlerts(),4);
		assertEquals(ad.getByUser(u2),2);
		
	}
	
	@Test
	@Transactional
	public void testGetByPoint(){
		User u = ud.findByUsername("maggie");
		List<Alert> list = ad.getByUser(u);

		Alert a = ad.getAlertByPoint(new Point(0,1));
		assertEquals(list.get(0),a);

		a = ad.getAlertByPoint(new Point(3,3));
		assertNull(a);

	}
	
	@Test
	@Transactional
	public void testFind(){
		User u = ud.findByUsername("maggie");
		List<Alert> list = ad.getByUser(u);

		Alert a = list.get(0);
		Alert a2 = ad.findById(a.getId());
		assertEquals(a,a2);

		a = ad.findById(9);
		assertNull(a);
		
	}

	

}
