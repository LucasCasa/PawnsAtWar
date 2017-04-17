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

import ar.edu.itba.interfaces.AlertDao;
import ar.edu.itba.interfaces.UserDao;

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
		
	}
	
	@Test
	@Transactional
	public void testRemove(){
		
	}
	
	@Test
	@Transactional
	public void testGetAll(){
		
	}
	
	@Test
	@Transactional
	public void testGetByPoint(){
		
	}
	
	@Test
	@Transactional
	public void testFind(){
		
	}

	

}
