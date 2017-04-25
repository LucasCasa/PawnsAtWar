package persistence;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import ar.edu.itba.model.TradeOffer;
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

import java.util.List;

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
		User u = ud.findByUsername("maggie3");
		List<TradeOffer> list = cd.getAllOffers(u);
		cd.removeOffer(list.get(0).getId());

		List<TradeOffer> list2 = cd.getAllOffers(u);
		assertNotEquals(list2.size(),list.size());

		cd.removeOffer(list.get(0).getId());
		assertEquals(cd.getAllOffers(u).size(),list2.size());

	}


	@Test
	@Transactional
	public void testGetAll(){
		User u = ud.findByUsername("maggie");
		User u2 = ud.findByUsername("maggie3");
		List<TradeOffer> list = cd.getAllOffers();
		List<TradeOffer> list2 = cd.getAllOffers(u);
		List<TradeOffer> list3 = cd.getAllOffers(u2);

		assertEquals(list.size(), list2.size() + list3.size());

		cd.createOffer(u, 1, 1000, 0, 1000);
		cd.createOffer(u2, 0, 122, 1, 122);

		assertNotEquals(cd.getAllOffers().size(),list.size());


	}
	
	
	

}
