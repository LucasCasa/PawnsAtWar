package ar.edu.itba.persistence;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.List;
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
		jdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("ARMY").usingGeneratedKeyColumns("idArmy");
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

	@Override
	public List<Army> getArmiesByUserId(int userId) {
		 List<Army> armyList = jdbcTemplate
	                .query("SELECT * FROM ARMY WHERE idPlayer = ?",(ResultSet resultSet, int rowNum) -> {
	                    return new Army(new Point(resultSet.getInt("x"),resultSet.getInt("y")),resultSet.getInt("idPlayer"),resultSet.getInt("idArmy"),resultSet.getBoolean("available"));
	                },userId);

	        return armyList;
	}

	public Army getArmyById(int idArmy){
		List<Army> armies = jdbcTemplate
				.query("SELECT * FROM ARMY WHERE idArmy = ?",(ResultSet resultSet, int rowNum) -> {
					return new Army(new Point(resultSet.getInt("x"),resultSet.getInt("y")),resultSet.getInt("idPlayer"),resultSet.getInt("idArmy"),resultSet.getBoolean("available"));},idArmy);
		return armies.isEmpty()? null: armies.get(0);
	}

	@Override
	public boolean isAvailable(Point p) {
		List<Boolean> isAvailable = jdbcTemplate.
				query("SELECT available FROM ARMY WHERE x = ? AND y = ?", (ResultSet resultSet, int rowNum) -> {
					return resultSet.getBoolean("available");
				}, p.getX(), p.getY());

		return isAvailable.get(0);

	}

}
