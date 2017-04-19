package persistence;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.itba.interfaces.ArmyDao;
import ar.edu.itba.interfaces.TroopDao;
import ar.edu.itba.interfaces.UserDao;
import ar.edu.itba.model.Army;
import ar.edu.itba.model.Troop;
import ar.edu.itba.model.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@Transactional
public class TroopHibernateDaoTest {
	@Autowired ArmyDao ad;
	@Autowired TroopDao td;
	@Autowired UserDao ud;
	
	private TestDataBasePopulator populator;
	
	@BeforeClass
	public static void beforeClass(){
		
	}
	
	@Before
	@Transactional
	public void setUp(){
		populator = new TestDataBasePopulator(ad, td, ud);
		populator.populateTroop();
	}
	
	@Test
	@Transactional
	public void testRemoveTroop(){
		User u = ud.findByUsername("maggie");
		List<Army> list = ad.getArmiesByUserId(u);
		Army a = list.get(0);
		td.addTroop(a.getIdArmy(), 1, 100);
		td.addTroop(a.getIdArmy(), 2, 100);
		Troop t = td.getTroop(a.getIdArmy(), 1);
		
		assertEquals(t.getQuantity(),100);
		assertEquals(td.getAllTroop(a.getIdArmy()).size(),3);
		
		td.deleteTroop(a.getIdArmy(), 2);
		assertEquals(td.getAllTroop(a.getIdArmy()).size(),2);
		
		td.deleteTroop(a.getIdArmy(), -1);
		assertEquals(td.getAllTroop(a.getIdArmy()).size(),2);
	}
	
	@Test
	@Transactional
	public void testAddTroop(){
		User u = ud.findByUsername("maggie");
		List<Army> list = ad.getArmiesByUserId(u);
		Army a = list.get(0);
		
		Troop t = td.getTroop(a.getIdArmy(), 0);
		assertNotNull(t);
		Troop t2 = td.addTroop(a.getIdArmy(), 0, 2);
		assertEquals(t,t2);
				
		t = td.addTroop(a.getIdArmy(), 1, -1);
		assertNotEquals(t.getQuantity(),-1);
		assertEquals(t.getQuantity(),0);
		
		t = td.addTroop(a.getIdArmy(),2,3);
		assertEquals(td.getTroop(a.getIdArmy(), 2),t);
		
	}
	
	@Test
	@Transactional
	public void testChangeTroop(){
		User u = ud.findByUsername("maggie");
		int idArmy = ad.getArmiesByUserId(u).get(0).getIdArmy();
		
		Troop t = td.getTroop(idArmy, 0);
		assertNotNull(t);
		assertEquals(t.getQuantity(),100);
		
		td.changeAmount(idArmy, 0, -1000);
		assertEquals(td.getAmount(idArmy, 0),100);
		
		td.changeAmount(idArmy, 0, 200);
		assertEquals(td.getAmount(idArmy, 0),200);
		
		
	}
	
	@Test
	@Transactional
	public void testGetTroop(){
		User u = ud.findByUsername("maggie");
		
	}
	

}
