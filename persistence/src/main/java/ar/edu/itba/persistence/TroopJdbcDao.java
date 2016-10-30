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
import ar.edu.itba.model.Troop;

@Repository
public class TroopJdbcDao implements TroopDao {

	
	private final JdbcTemplate jdbcTemplate;
	private final SimpleJdbcInsert jdbcInsert;
	
	@Autowired
	ArmyDao ad;
	
	@Autowired
	public TroopJdbcDao(final DataSource dataSource){
		jdbcTemplate = new JdbcTemplate(dataSource);
		jdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("TROOP");
	}

	@Override
	public int getAmount(int idArmy, int type) {
		List<Integer> troopList = jdbcTemplate
				.query("SELECT * FROM TROOP WHERE idArmy = ? AND type = ?",(ResultSet resultSet, int rowNum) -> {
							return resultSet.getInt("amount");
						},idArmy,type);
		return troopList.isEmpty() ? -1 : troopList.get(0);
	}

	@Override
	public List<Troop> getAllTroop(int idArmy) {
		List<Troop> troopList = jdbcTemplate
				.query("SELECT * FROM TROOP WHERE idArmy = ?",(ResultSet resultSet, int rowNum) -> {
							return new Troop(resultSet.getInt("idArmy"),resultSet.getInt("type"),resultSet.getInt("amount"));
						},idArmy);
		return troopList;
	}

	@Override
	public void changeAmount(int idArmy, int type, int amount) {
		jdbcTemplate
		.update("UPDATE TROOP SET amount = ? WHERE idArmy = ? AND type = ?",amount,idArmy,type);
		
	}

	@Override
	public void deleteTroop(int idArmy, int type) {
		jdbcTemplate.update("DELETE FROM TROOP WHERE idArmy = ? AND type = ?", idArmy,type);
	}

	@Override
	public Troop addTroop(int idArmy, int type, int amount) {
		final Map<String,Object> args = new HashMap<>();
		args.put("idArmy", idArmy);
		args.put("type", type);
		args.put("amount", amount);
		jdbcInsert.execute(args);
		return new Troop(idArmy,type,amount);
	}

	@Override
	public boolean exists(int idArmy, int type) {
		List<Integer> troopList = jdbcTemplate
				.query("SELECT count(*) AS aux FROM TROOP WHERE idArmy = ? AND  type = ?",(ResultSet resultSet, int rowNum) -> {
							return new Integer(resultSet.getInt("aux"));
						},idArmy,type);
		return troopList.get(0) == 0 ? false : true;
	}

	@Override
	public Troop getTroop(int idArmy, int type) {
		List<Troop> troopList = jdbcTemplate
				.query("SELECT * FROM TROOP WHERE idArmy = ? AND type = ?",(ResultSet resultSet, int rowNum) -> {
							return new Troop(idArmy,resultSet.getInt("type"),resultSet.getInt("amount"));
						},idArmy,type);
		return troopList.get(0);
	}

	@Override
	public int getAmountTroops(int idArmy) {
		List<Integer> troopList = jdbcTemplate
				.query("SELECT count(*) AS aux FROM TROOP WHERE idArmy = ?",(ResultSet resultSet, int rowNum) -> {
							return new Integer(resultSet.getInt("aux"));
						},idArmy);
		return troopList.get(0);
	}

}
