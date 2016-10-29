package ar.edu.itba.persistence;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

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

import ar.edu.itba.model.Point;
import ar.edu.itba.model.Sector;

@RunWith(SpringJUnit4ClassRunner.class) 
@ContextConfiguration(classes = TestConfig.class)
@Sql("/schema.sql")
public class TerrainJdbcDaoTest {
	
	private static final String TABLE_NAME = "terrain";
	
	private static final int idPlayer = 1;
	
	@Autowired
	private DataSource ds;
	
	@Autowired
	private BuildingJdbcDao buildingDao;
	
	private JdbcTemplate jdbcTemplate;
	
	@Before
	public void setUp(){
		jdbcTemplate = new JdbcTemplate(ds);
		JdbcTestUtils.deleteFromTables(jdbcTemplate, TABLE_NAME);
		jdbcTemplate.execute("INSERT into userPaw (idPlayer,username,password,email) VALUES (DEFAULT,'abc','xxx','a@ok');");
	}
	
	@Test
	public void testCreate(){
		final int posX = (int)Math.random()*100;
		final int posY = (int)Math.random()*100;
		final int level = 1;
		final int type = 3;
		
		Sector t = buildingDao.addBuilding(new Point(posX,posY), level, idPlayer, type);
		
		assertNotNull(t);
		assertEquals(posX, t.getPosition().getX());
		assertEquals(posY, t.getPosition().getY());
	}
	
	

}
