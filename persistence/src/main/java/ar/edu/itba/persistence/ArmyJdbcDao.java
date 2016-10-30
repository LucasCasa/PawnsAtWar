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
import ar.edu.itba.interfaces.TroopDao;
import ar.edu.itba.interfaces.UserDao;
import ar.edu.itba.model.Army;
import ar.edu.itba.model.Point;

@Repository
public class ArmyJdbcDao implements ArmyDao{
	
	private final JdbcTemplate jdbcTemplate;
	private final SimpleJdbcInsert jdbcInsert;
	
	@Autowired
	UserDao ud;
	
	@Autowired
	TroopDao td;
	
	@Autowired
	public ArmyJdbcDao(final DataSource dataSource){
		jdbcTemplate = new JdbcTemplate(dataSource);
		jdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("ARMY").usingGeneratedKeyColumns("idarmy");
	}

	@Override
	public Army addArmy(Point position, int idPlayer, boolean available) {
		final Map<String,Object> args = new HashMap<>();
		args.put("x", position.getX());
		args.put("y", position.getY());
		args.put("idPlayer",idPlayer);
		args.put("available", available);
		final Number key = jdbcInsert.executeAndReturnKey(args);
		return new Army(position,ud.findById(idPlayer),key.intValue(),available,td.getAllTroop(key.intValue()));
	}

	@Override
	public Army addArmy(Point position, int idPlayer) {
		return addArmy(position,idPlayer,true);
	}

	@Override
	public List<Army> getArmiesByUserId(int userId) {
		 List<Army> armyList = jdbcTemplate
	                .query("SELECT * FROM ARMY WHERE idPlayer = ?",(ResultSet resultSet, int rowNum) -> {
	                    return new Army(new Point(resultSet.getInt("x"),resultSet.getInt("y")),ud.findById(resultSet.getInt("idPlayer")),
	                    		resultSet.getInt("idArmy"),resultSet.getBoolean("available"),td.getAllTroop(resultSet.getInt("idArmy")));
	                },userId);
	     return armyList;
	}

	public Army getArmyById(int idArmy){
		List<Army> armies = jdbcTemplate
				.query("SELECT * FROM ARMY WHERE idArmy = ?",(ResultSet resultSet, int rowNum) -> {
					 return new Army(new Point(resultSet.getInt("x"),resultSet.getInt("y")),ud.findById(resultSet.getInt("idPlayer")),
	                    		resultSet.getInt("idArmy"),resultSet.getBoolean("available"),null);},idArmy);
		if(armies.isEmpty()){
			return null;
		}
		armies.get(0).setTroops(td.getAllTroop(idArmy));
		return armies.get(0);
	}

	@Override
	public boolean isAvailable(Point p) {
		List<Boolean> isAvailable = jdbcTemplate.
				query("SELECT available FROM ARMY WHERE x = ? AND y = ?", (ResultSet resultSet, int rowNum) -> {
					return resultSet.getBoolean("available");
				}, p.getX(), p.getY());

		return isAvailable.get(0);

	}
	
	@Override
	public void setAvailable(int idArmy,boolean available) {
		jdbcTemplate
		.update("UPDATE ARMY SET available = ? WHERE idArmy = ?",available,idArmy);
		
	}

	public boolean belongs(int userId, int idArmy){
		List<Integer> armyList = jdbcTemplate
				.query("SELECT COUNT(*) as aux FROM ARMY WHERE idPlayer = ? AND idArmy = ?",(ResultSet resultSet, int rowNum) -> {
					return resultSet.getInt("aux");
				},userId, idArmy);
		return armyList.get(0) == 0 ? false : true;
	}

	@Override
	public boolean exists(Point p, int idPlayer) {
		List<Integer> armyList = jdbcTemplate
				.query("SELECT COUNT(*) as aux FROM ARMY WHERE x = ? AND y = ? AND idPlayer = ?",(ResultSet resultSet, int rowNum) -> {
					return resultSet.getInt("aux");
				},p.getX(), p.getY(),idPlayer);
		return armyList.get(0) == 0 ? false : true;
	}

	@Override
	public Army getArmy(Point p, int idPlayer) {
		List<Army> armies = jdbcTemplate
				.query("SELECT * FROM ARMY WHERE idPlayer = ? AND x = ? AND y = ?",(ResultSet resultSet, int rowNum) -> {
					 return new Army(new Point(resultSet.getInt("x"),resultSet.getInt("y")),ud.findById(resultSet.getInt("idPlayer")),
	                    		resultSet.getInt("idArmy"),resultSet.getBoolean("available"),td.getAllTroop(resultSet.getInt("idArmy")));},
						idPlayer,p.getX(),p.getY());
		return armies.isEmpty() ? null : armies.get(0);
	}

	@Override
	public void deleteArmy(int idArmy) {
		jdbcTemplate
		.update("DELETE FROM army WHERE idarmy = ?",idArmy);
		
	}

}
