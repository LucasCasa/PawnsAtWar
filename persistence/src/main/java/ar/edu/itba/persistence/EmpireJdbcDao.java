package ar.edu.itba.persistence;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import ar.edu.itba.interfaces.EmpireDao;
import ar.edu.itba.interfaces.ResourceDao;
import ar.edu.itba.model.Resource;

@Repository
public class EmpireJdbcDao implements EmpireDao {
	private final JdbcTemplate jdbcTemplate;
	private final SimpleJdbcInsert jdbcInsert;
	
	@Autowired
	public EmpireJdbcDao(final DataSource dataSource){
		jdbcTemplate = new JdbcTemplate(dataSource);
		jdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("empire");
	}
	
	@Autowired
	private ResourceDao rd;

	@Override
	public Timestamp getLastTimeUpdate(int userId) {
		List<Timestamp> time = jdbcTemplate.query
				("SELECT * FROM empire WHERE idPlayer = ?", (ResultSet resultSet, int rowNum) -> {
                    return resultSet.getTimestamp("lastUpdate");
                },userId);

	        return time.get(0);
	}

	@Override
	public void setLastTimeUpdate(int userId, Timestamp t) {
		jdbcTemplate.update("UPDATE empire SET lastUpdate = ? WHERE idPlayer = ?",t,userId);
		
	}

	@Override
	public Resource getResource(int userid, int id) {
		return rd.getResource(userid, id);
	}

	@Override
	public void setResource(int userId, int id, int amount) {
		rd.setAmount(userId, id, amount);
	}
	
	@Override
	public List<Resource> getResources(int userId){
		return rd.getResources(userId);
	}

	@Override
	public void addAmount(int userID, int id, int amount) {
		rd.addAmount(userID, id, amount);
	}
	
}
