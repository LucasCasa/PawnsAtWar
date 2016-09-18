package ar.edu.itba.persistence;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import ar.edu.itba.interfaces.ArmyDao;
import ar.edu.itba.model.Army;
import ar.edu.itba.model.Point;

@Repository
public class ArmyJdbcDao implements ArmyDao{
	
	private final JdbcTemplate jdbcTemplate;
	private final SimpleJdbcInsert jdbcInsert;
	
	@Autowired
	public ArmyJdbcDao(final DataSource dataSource){
		jdbcTemplate = new JdbcTemplate(dataSource);
		jdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("army").usingGeneratedKeyColumns("idArmy");
	}

	@Override
	public Army addArmy(Point position, int idPlayer, boolean available) {
		final Map<String,Object> args = new HashMap<>();
		args.put("x", position.getX());
		args.put("y", position.getY());
		args.put("idPlayer",idPlayer);
		args.put("available", available);
		final Number key = jdbcInsert.executeAndReturnKey(args);
		return new Army(position,key.intValue(),available);
	}

	@Override
	public Army addArmy(Point position, int idPlayer) {
		return addArmy(position,idPlayer,false);
	}

}
