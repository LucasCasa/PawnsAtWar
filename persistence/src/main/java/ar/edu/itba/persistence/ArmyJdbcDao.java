package ar.edu.itba.persistence;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import ar.edu.itba.interfaces.ArmyDao;
import ar.edu.itba.model.Army;
import ar.edu.itba.model.Point;

public class ArmyJdbcDao implements ArmyDao{
	
	private final JdbcTemplate jdbcTemplate;
	private final SimpleJdbcInsert jdbcInsert;
	
	@Autowired
	public ArmyJdbcDao(final DataSource dataSource){
		jdbcTemplate = new JdbcTemplate(dataSource);
		jdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("army").usingGeneratedKeyColumns("idArmy");
		jdbcTemplate.execute("create table if not exists army ("
				+ "idArmy integer SERIAL PRIMARY KEY,"
		 		+ "x integer not null,"
				+ "y integer not null, "
		 		+ "idPlayer integer," 
		 		+ "boolean available"
		 		+ "CHECK (x >= 0),"
		 		+ "CHECK (y >= 0)"
		 		+ ");"
		);
	}

	@Override
	public Army addArmy(Point position, int idPlayer, boolean available) {
		final Map<String,Object> args = new HashMap<>();
		args.put("x", position.getX());
		args.put("y", position.getY());
		args.put("idPlayer",idPlayer);
		args.put("available", available);
		final Number key = jdbcInsert.executeAndReturnKey(args);
		return new Army(position,idPlayer,key.intValue(),available);
	}

	@Override
	public Army addArmy(Point position, int idPlayer) {
		return addArmy(position,idPlayer,false);
	}

}
