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
import ar.edu.itba.interfaces.UserDao;
import ar.edu.itba.model.Army;
import ar.edu.itba.model.Point;
import ar.edu.itba.model.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@Transactional
public class ArmyHibernateDaoTest {
	@Autowired ArmyDao ad;
	@Autowired UserDao ud;
	
	private TestDataBasePopulator populator;
	
	@BeforeClass
	public static void beforeClass(){
		
	}
	
	@Before
	@Transactional
	public void setUp(){
		populator = new TestDataBasePopulator(ud,ad);
		populator.populateArmy();
	}
	
	@Test
	@Transactional
	public void addAndremoveArmy(){
		User u = ud.findByUsername("maggie");
		assertNotNull(u);
		List<Army> list =  ad.getArmiesByUserId(u);
		assertEquals(list.size(),2);
		Army a = ad.addArmy(new Point(1,2), u);
		assertEquals(ad.getArmiesByUserId(u).size(),3);
		ad.deleteArmy(a.getIdArmy());
		assertEquals(ad.getArmiesByUserId(u).size(),2);
	}
	
}
