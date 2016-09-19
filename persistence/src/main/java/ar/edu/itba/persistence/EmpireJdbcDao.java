package ar.edu.itba.persistence;

import java.sql.ResultSet;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import ar.edu.itba.interfaces.EmpireDao;
import ar.edu.itba.interfaces.ResourceDao;
import ar.edu.itba.model.Empire;
import ar.edu.itba.model.Point;
import ar.edu.itba.model.Resource;
import ar.edu.itba.model.Sector;

@Repository
public class EmpireJdbcDao implements EmpireDao {
	private final JdbcTemplate jdbcTemplate;
	private final SimpleJdbcInsert jdbcInsert;
	
	@Autowired
	public EmpireJdbcDao(final DataSource dataSource){
		jdbcTemplate = new JdbcTemplate(dataSource);
		jdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("empire");
	}

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
		jdbcTemplate.query("UPDATE empire SET lastUpdate = ? WHERE idPlayer = ?",(ResultSet resultSet, int rowNum) -> {
				return resultSet.getInt("idPlayer");
			},t,userId);
		
	}

	@Override
	public Resource getResource(int userid, int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setResource(int userId, int id, int amount) {
		// TODO Auto-generated method stub
		
	}
	
}
