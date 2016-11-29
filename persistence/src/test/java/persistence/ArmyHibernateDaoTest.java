package persistence;

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
import ar.edu.itba.interfaces.BuildingDao;
import ar.edu.itba.interfaces.CommerceDao;
import ar.edu.itba.interfaces.ResourceDao;
import ar.edu.itba.interfaces.TroopDao;
import ar.edu.itba.interfaces.UserDao;

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
		populator = new TestDataBasePopulator(this.ud,this.ad,this.td);
		populator.populate2();
	}
	
	@Test
	@Transactional
	public void addArmy(){
		
	}
	
	@Test
	@Transactional
	public void addTroop(){
		
	}
	
	@Test
	@Transactional
	public void removeArmy(){
		
	}
	
	@Test
	@Transactional
	public void subtractTroop(){
		
	}
	
	@Test
	@Transactional
	public void removeTroop(){
		
	}
}
