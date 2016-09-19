package ar.edu.itba.persistence;

import java.sql.ResultSet;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import ar.edu.itba.interfaces.TroopDao;
import ar.edu.itba.model.Troop;

@Repository
public class TroopJdbcDao implements TroopDao {

	
	private final JdbcTemplate jdbcTemplate;
	private final SimpleJdbcInsert jdbcInsert;
	
	@Autowired
	public TroopJdbcDao(final DataSource dataSource){
		jdbcTemplate = new JdbcTemplate(dataSource);
		jdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("troop");
	}

	@Override
	public int getAmount(int idArmy, int type) {
		List<Integer> troopList = jdbcTemplate
				.query("SELECT * FROM troop WHERE idArmy = ? AND type = ?",(ResultSet resultSet, int rowNum) -> {
							return resultSet.getInt("amount");
						},idArmy,type);
		return troopList.isEmpty() ? -1 : troopList.get(0);
	}

	@Override
	public List<Troop> getAllTroop(int idArmy) {
		List<Troop> troopList = jdbcTemplate
				.query("SELECT * FROM troop WHERE idArmy = ?",(ResultSet resultSet, int rowNum) -> {
							return new Troop(resultSet.getInt("idArmy"),resultSet.getInt("type"),resultSet.getInt("amount"));
						},idArmy);
		return troopList;
	}

	@Override
	public void changeAmount(int idArmy, int type, int amount) {
		jdbcTemplate
		.update("UPDATE troop SET amount = ? WHERE idArmy = ? AND type = ?",amount,idArmy,type);
		
	}

	@Override
	public void deleteTroop(int idArmy, int type) {
		jdbcTemplate.update("DELETE FROM troop WHERE idArmy = ? AND type = ?", idArmy,type);
	}
	
}
