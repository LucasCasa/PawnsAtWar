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
import ar.edu.itba.model.Point;
import ar.edu.itba.model.Troop;
import ar.edu.itba.model.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@Transactional
public class ArmyHibernateDaoTest {
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
		populator.populate2();
	}
	
	@Test
	@Transactional
	public void addAndremoveArmy(){
		User u = ud.findByUsername("maggie");
		assertNotNull(u);
		List<Army> list =  ad.getArmiesByUserId(u);
		assertEquals(list.size(),2);
		Army a = ad.addArmy(new Point(1,2), u);
		td.addTroop(a.getIdArmy(), 1, 1);
		assertEquals(ad.getArmiesByUserId(u).size(),3);
		td.deleteTroop(a.getIdArmy(), 1);
		ad.deleteArmy(a.getIdArmy());
		assertEquals(ad.getArmiesByUserId(u).size(),2);
	}
	
	
	@Test
	@Transactional
	public void removeTroop(){
		User u = ud.findByUsername("maggie");
		List<Army> list = ad.getArmiesByUserId(u);
		Army a = list.get(0);
		Troop t = td.getTroop(a.getIdArmy(), 1);
		assertEquals(t.getQuantity(),100);
		assertEquals(td.getAllTroop(a.getIdArmy()).size(),3);
		td.deleteTroop(a.getIdArmy(), 2);
		assertEquals(td.getAllTroop(a.getIdArmy()).size(),2);	
	}
}
