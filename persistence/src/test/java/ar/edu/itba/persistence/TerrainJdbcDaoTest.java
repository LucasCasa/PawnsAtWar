package ar.edu.itba.persistence;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.jdbc.JdbcTestUtils;

@RunWith(SpringJUnit4ClassRunner.class) 
@ContextConfiguration(classes = TestConfig.class)
public class TerrainJdbcDaoTest {
	
	private static final String X = "x";
	private static final String Y = "y";
	private static final String POWER = "power";
	private static final String TYPE = "type";
	private static final String IDPLAYER = "idPlayer";
	
	@Autowired
	private DataSource ds;
	
	@Autowired
	private TerrainJdbcDao sectorDao;
	
	private JdbcTemplate jdbcTemplate;
	
	@Before
	public void setUp(){
		jdbcTemplate = new JdbcTemplate(ds);
		JdbcTestUtils.deleteFromTables(jdbcTemplate, "terrain");
		
	}
	
	@Test
	public void testCreate(){
	}
	
	

}
