package persistence;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

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
		populator.populateEmpire();
	}
	
	@Test
	@Transactional
	public void testCreateUser(){
	}
	
	@Test
	@Transactional
	public void testFindById(){
	}
	
	@Test
	@Transactional
	public void testExists(){
	}
	
	@Test
	@Transactional
	public void testSetLocale(){
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

}
