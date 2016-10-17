package ar.edu.itba.persistence;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.jdbc.JdbcTestUtils;

import ar.edu.itba.interfaces.UserDao;
import ar.edu.itba.model.User;

@RunWith(SpringJUnit4ClassRunner.class) 
@ContextConfiguration(classes = TestConfig.class)
@Sql("/schema.sql")
public class UserJdbcDaoTest {
	
	private static final String TABLE_NAME = "userPaw";
	private static final String username = "username";
	private static final String password = "password";
	private static final String email = "hola@emai.com";
	
	@Autowired
	private DataSource ds;
	
	@Autowired
	private UserDao ud;
	
	private JdbcTemplate jdbcTemplate;
	
	@Before
	public void setUp(){
		jdbcTemplate = new JdbcTemplate(ds);
		JdbcTestUtils.deleteFromTables(jdbcTemplate, TABLE_NAME);
	}
	
	@Test
	public void testCreate(){
		int before = JdbcTestUtils.countRowsInTable(jdbcTemplate, TABLE_NAME);
		
		User u = ud.create(username, password, email);

		assertNotNull(u);
		assertEquals(username, u.getName());
		assertEquals(before+1, JdbcTestUtils.countRowsInTable(jdbcTemplate, TABLE_NAME));
	}
	
	@Test
	public void testExists(){
		jdbcTemplate.execute("INSERT INTO userPaw VALUES (DEFAULT,'" +username+"','"+password+"','"+email+"');");
		
		boolean shouldNotExist = ud.exists("any", "whatever");
		boolean shouldExist = ud.exists(username, password);
		
		assertFalse(shouldNotExist);
		assertTrue(shouldExist);
	}
	
	

}
