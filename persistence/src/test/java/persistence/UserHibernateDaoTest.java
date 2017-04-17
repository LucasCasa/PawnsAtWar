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

import ar.edu.itba.interfaces.UserDao;
import ar.edu.itba.model.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@Transactional
public class UserHibernateDaoTest {
	
	@Autowired private UserDao ud;
	
	private TestDataBasePopulator populator;
	
	@BeforeClass
	public static void beforeClass(){
		
	}
	
	@Before
	@Transactional
	public void setUp(){
		populator = new TestDataBasePopulator(ud);
		populator.populateUser();
	}
	
	@Test
	@Transactional
	public void testCreateUser(){
		List<User> list= ud.getAllUsers();
		User u = ud.create("maggie2", "maggie", "mvega@itba.edu.ar");
		User u2 = ud.create("maggie4", "maggie", "mvega@itba.edu.ar");
		List<User> list2 = ud.getAllUsers();
		assertNotEquals(u.getId(),u2.getId());
		assertNotEquals(list.size(),list2.size());
		
	}
	
	@Test
	@Transactional
	public void testFindById(){
		User u = ud.findByUsername("maggie");
		User u2 = ud.findById(u.getId());
		User u3 = ud.findById(5);
		assertNotEquals(u,u3);
		assertEquals(u,u2);
		
	}
	
	@Test
	@Transactional
	public void testExists(){
		assertTrue(ud.exists("maggie"));
		assertTrue(ud.exists("MAGGIE"));
		assertFalse(ud.exists("maggie", "password"));
		assertFalse(ud.exists("maggie2"));
		assertFalse(ud.exists("maggie", "MAGGIE"));
		assertTrue(ud.exists("maggie", "maggie"));
		
		
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
		ud.create("maggie2", "maggie", "mvega@itba.edu.ar");
		assertNotEquals(ud.getAllUsers().size(),list.size());
	}
	
	@Test
	@Transactional
	public void testGetUsernames(){
		List<String> list = ud.getUsernames();
		assertNotNull(list);
		assertEquals(2,list.size());
		List<String> list2 = ud.getUsernames("mag");
		assertEquals(2,list2.size());
		list2 = ud.getUsernames("a");
		assertNotEquals(2,list2.size());
		assertEquals(0,list2.size());
	}

}
