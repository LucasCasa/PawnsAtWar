package persistence;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.itba.interfaces.CommerceDao;
import ar.edu.itba.interfaces.UserDao;
import ar.edu.itba.model.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@Transactional
public class CommerceHibernateDaoTest {
	
	private TestDataBasePopulator populator;
	@Autowired private CommerceDao cd;
	@Autowired private UserDao ud;
	
	@BeforeClass
	public static void beforeClass(){
		
	}
	
	@Before
	@Transactional
	public void setUp(){
		populator = new TestDataBasePopulator(ud, cd);
		populator.populateCommerce();
	}
	
	@Test
	@Transactional
	public void testAddCommerce(){
		User u = ud.findByUsername("maggie3");
		assertEquals(cd.getAllOffers(u).size(),2);
		cd.createOffer(u, 1, 1000, 0, 1000);
		cd.createOffer(u, 0, 122, 1, 122);
		assertEquals(cd.getAllOffers(u).size(),4);
	}
	
	@Test
	@Transactional
	public void testDeleteCommerce(){
	}
	
	@Test
	@Transactional
	public void testGetAll(){
	}
	
	
	

}
