package ar.edu.itba.persistence;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import ar.edu.itba.interfaces.BuildingDao;
import ar.edu.itba.interfaces.EmpireDao;
import ar.edu.itba.interfaces.ResourceDao;
import ar.edu.itba.model.Building;
import ar.edu.itba.model.Resource;

@Repository
public class EmpireJdbcDao implements EmpireDao {
	private final JdbcTemplate jdbcTemplate;
	private final SimpleJdbcInsert jdbcInsert;
	
	@Autowired
	public EmpireJdbcDao(final DataSource dataSource){
		jdbcTemplate = new JdbcTemplate(dataSource);
		jdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("EMPIRE");
	}
	
	@Autowired
	private ResourceDao rd;
	
	@Autowired
	private BuildingDao bd;

	@Override
	public Timestamp getLastTimeUpdate(int userid) {
		List<Timestamp> time = jdbcTemplate.query
				("SELECT * FROM EMPIRE WHERE idPlayer = ?", (ResultSet resultSet, int rowNum) -> {
                    return resultSet.getTimestamp("lastUpdate");
                },userid);

	        return time == null ? null : time.get(0);
	}

	@Override
	public void setLastTimeUpdate(int userid, Timestamp t) {
		jdbcTemplate.update("UPDATE EMPIRE SET lastUpdate = ? WHERE idPlayer = ?",t,userid);
		
	}

	@Override
	public Resource getResource(int userid, int id) {
		return rd.getResource(userid, id);
	}

	@Override
	public void setResource(int userid, int id, int amount) {
		rd.setAmount(userid, id, amount);
	}
	
	@Override
	public List<Resource> getResources(int userid){
		return rd.getResources(userid);
	}

	@Override
	public void addAmount(int userid, int id, int amount) {
		rd.addAmount(userid, id, amount);
	}
	
	@Override
	public void substractAmount(int userid, int id, int amount) {
		rd.subtractAmount(userid, id, amount);
	}

	@Override
	public List<Building> getBuilding(int userid, int type) {
		return bd.getBuildings(userid,type);
	}

	@Override
	public void createEmpire(int userid, Timestamp timestamp) {
		final Map<String,Object> args = new HashMap<>();
		args.put("idPlayer", userid);
		args.put("lastUpdate", timestamp);
		jdbcInsert.execute(args);
	}
}
