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

import ar.edu.itba.interfaces.MessageDao;
import ar.edu.itba.interfaces.UserDao;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@Transactional
public class MessageHIbernateDaoTest {
	@Autowired public MessageDao md;
	@Autowired private UserDao ud;
	private TestDataBasePopulator populator;

	@BeforeClass
	public static void beforeClass(){

	}

	@Before
	@Transactional
	public void setUp(){
		populator = new TestDataBasePopulator(ud,md);
		populator.populateMessage();
	}
	
	@Test
	@Transactional
	public void testCreateMessage(){
	}
	
	@Test
	@Transactional
	public void testGetMessage(){
	}
	
	@Test
	@Transactional
	public void testGetReadMessage(){
	}
	
	@Test
	@Transactional
	public void testGetUnreadMessage(){
	}
	
	@Test
	@Transactional
	public void testGetMessageById(){
	}
	
	@Test
	@Transactional
	public void testRemoveMessage(){
	}
	
	@Test
	@Transactional
	public void testGetSent(){
	}
	
	

}
