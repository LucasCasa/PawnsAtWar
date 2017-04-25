package persistence;

import ar.edu.itba.model.Message;
import ar.edu.itba.model.User;
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

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@Transactional
public class MessageHIbernateDaoTest {
	@Autowired private MessageDao md;
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
		User u = ud.findByUsername("maggie");
		User u2 = ud.findByUsername("maggie2");

		List<Message> list = md.getAllMessages(u);
		assertEquals(list.size(),1);

		md.createMessage(u2,u,"HOLA","CHAU");
		assertEquals(md.getAllMessages(u).size(),2);

		md.createMessage(null,u,"HOLA","CHAU");
		assertNotEquals(md.getAllMessages(u).size(),3);

		md.createMessage(u2,null,"HOLA","CHAU");
		assertNotEquals(md.getAllMessages(u).size(),3);

		md.createMessage(u2,u,null,"CHAU");
		assertNotEquals(md.getAllMessages(u).size(),3);

		md.createMessage(u2,u,"HOLA",null);
		assertNotEquals(md.getAllMessages(u).size(),3);

	}
	
	@Test
	@Transactional
	public void testGetMessage(){
		User u = ud.findByUsername("maggie");
		List<Message> list = md.getAllMessages(u);
		Message m = list.get(0);

		assertEquals(md.getById(m.getId()),m);

		assertNull(md.getById(new Long(15)));

		assertNull(md.getById(new Long(-2)));

	}
	
	@Test
	@Transactional
	public void testGetReadMessage(){
		User u = ud.findByUsername("maggie");
		List<Message> list = md.getReadMesssages(u);

		assertEquals(list.size(),0);

		Message m = md.getAllMessages(u).get(0);
		md.markAsRead(m.getId());

		assertNotEquals(md.getReadMesssages(u).size(),0);
		assertEquals(md.getReadMesssages(u).size(),1);

		md.markAsRead(m.getId());
		assertNotEquals(md.getReadMesssages(u).size(),2);
	}
	
	@Test
	@Transactional
	public void testGetUnreadMessage(){
		User u = ud.findByUsername("maggie");
		List<Message> list = md.getUnReadMessages(u);

		assertEquals(list.size(),md.getAllMessages(u).size());

		Message m = md.getAllMessages(u).get(0);
		md.markAsRead(m.getId());

		assertNotEquals(md.getUnReadMessages(u).size(),list.size());
		assertEquals(md.getUnReadMessages(u).size(),list.size()-1);

	}
	
	@Test
	@Transactional
	public void testRemoveMessage(){
		User u = ud.findByUsername("maggie");

		List<Message> list = md.getAllMessages(u);

		md.removeMessage(list.get(0).getId());
		List<Message> list2 = md.getAllMessages(u);
		assertNotEquals(list2.size(),list.size());

		md.removeMessage(list.get(0).getId());
		assertEquals(md.getAllMessages(u).size(),list2.size());

		md.removeMessages(list);
		assertEquals(md.getAllMessages(u).size(),0);

	}
	
	@Test
	@Transactional
	public void testGetSent(){
		User u = ud.findByUsername("maggie");
		User u2 = ud.findByUsername("maggie2");
		List<Message> list = md.getSentMessages(u);

		md.createMessage(u,u2,"HOLA","TESTING");

		List<Message> list2 = md.getSentMessages(u);
		assertNotEquals(md.getSentMessages(u).size(),list.size());

		md.createMessage(u2,u,"HOLA","OTRO TEST");

		assertEquals(md.getSentMessages(u).size(),list2.size());
	}
	
	

}
