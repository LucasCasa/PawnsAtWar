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

import ar.edu.itba.interfaces.CommerceDao;
import ar.edu.itba.interfaces.ResourceDao;
import ar.edu.itba.interfaces.UserDao;
import ar.edu.itba.model.Resource;
import ar.edu.itba.model.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@Transactional
public class EmpireHibernateDaoTest {
	@Autowired private ResourceDao rd;
	@Autowired private CommerceDao cd;
	@Autowired private UserDao ud;
	
	private TestDataBasePopulator populator;
	
	@BeforeClass
	public static void beforeClass(){
		
	}
	
	@Before
	@Transactional
	public void setUp(){
		populator = new TestDataBasePopulator(ud, rd, cd);
		populator.populate();
	}
	
	@Test
	@Transactional
	public void testGetUser(){
		User u = ud.findByUsername("maggie");
		User u2 = ud.findByUsername("maggie2");
		User u3 = ud.findByUsername("maggie3");
		assertNull(u2);
		assertNotNull(u);
		assertEquals(u.getPassword(),"maggie");
		assertNotEquals(u.getId(),u3.getId());
		
	}
	
	@Test
	@Transactional
	public void testGetAllUser(){
		List<User> list = ud.getAllUsers();
		assertNotNull(list);
		assertEquals(2,list.size());
		assertEquals("maggie",list.get(0).getName());
		assertEquals("maggie3",list.get(1).getName());
	}
	
	@Test
	@Transactional
	public void testAddResource(){
		User u = ud.findByUsername("maggie");
		List<Resource> list = rd.getResources(u);
		assertEquals(list.size(),2);
		rd.addResource(u, 1, 1000);
		list = rd.getResources(u);
		assertEquals(list.get(0).getQuantity(),1000);
		rd.addResource(u, 0);
		assertNotEquals(list.size(),1);
		
		u = ud.findByUsername("maggie2");
		assertEquals(rd.getResources(u).size(),0);
	}
	
	@Test
	@Transactional
	public void testChangeResource(){
		User u = ud.findByUsername("maggie");
		Resource r = rd.getResource(u, 0);
		assertEquals(r.getQuantity(),1000);
		rd.subtractAmount(u,0,10);
		assertEquals(rd.getResource(u, 0).getQuantity(),990);
		rd.subtractAmount(u, 0, 1000);
		assertNotEquals(rd.getResource(u, 0).getQuantity(),-10);
		assertNotEquals(rd.getResource(u, 0).getQuantity(),990);
		assertEquals(rd.getResource(u, 0).getQuantity(),0);
		
		r = rd.getResource(u, 1);
		assertNotNull(r);
		assertEquals(r.getQuantity(),1000);
		rd.addAmount(u, 1, 100);
		assertEquals(rd.getResource(u, 1).getQuantity(),1100);
	}
	
	@Test
	@Transactional
	public void testRemoveResource(){
		User u = ud.findByUsername("maggie");
		List<Resource> list = rd.getResources(u);
		assertEquals(list.size(),2);
		rd.deleteResource(u, 1);
		assertEquals(rd.getResources(u).size(),1);
		assertNull(rd.getResource(u, 1));
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
	
}
