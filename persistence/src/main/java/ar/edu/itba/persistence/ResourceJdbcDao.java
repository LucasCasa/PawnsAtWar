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

import ar.edu.itba.interfaces.ResourceDao;
import ar.edu.itba.model.Resource;

@Repository
public class ResourceJdbcDao implements ResourceDao {
	private final JdbcTemplate jdbcTemplate;
	private final SimpleJdbcInsert jdbcInsert;
	
	@Autowired
	public ResourceJdbcDao(final DataSource dataSource){
		jdbcTemplate = new JdbcTemplate(dataSource);
		jdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("resources");
	}

	@Override
	public int addAmount(int idPlayer, int type, int value) {
		int amount = getAmount(idPlayer,type);
		List<Integer> amountList = jdbcTemplate
				.query("UPDATE resources SET amount = ? WHERE idPlayer = ? AND type = ?",(ResultSet resultSet, int rowNum) -> {
							return resultSet.getInt("amount");
						},amount + value,idPlayer,type);
		return amountList.isEmpty() ? null:amountList.get(0);
	}

	@Override
	public int subtractAmount(int idPlayer, int type, int value) {
		int amount = getAmount(idPlayer,type);
		int aux = amount - value < 0 ? 0 : amount - value ;
		List<Integer> amountList = jdbcTemplate
				.query("UPDATE resources SET amount = ? WHERE idPlayer = ? AND type = ?",(ResultSet resultSet, int rowNum) -> {
							return resultSet.getInt("amount");
						},aux,idPlayer,type);
		return amountList.isEmpty() ? null:amountList.get(0);
	}

	@Override
	public int getAmount(int idPlayer, int type) {
		List<Integer> amountList = jdbcTemplate
				.query("SELECT * FROM resources WHERE idPlayer = ?  AND type = ?",(ResultSet resultSet, int rowNum) -> {
							return resultSet.getInt("amount");
						},idPlayer,type);
		return amountList.isEmpty() ? null:amountList.get(0);
	}

	@Override
	public Resource addResource(int idPlayer, int type, int amount) {
		final Map<String,Object> args = new HashMap<>();
		args.put("type", type);
		args.put("amount", amount);
		args.put("idPlayer",idPlayer);
		jdbcInsert.execute(args);
		return new Resource(type,amount);
	}

	@Override
	public Resource addResource(int idPlayer, int type) {
		return addResource(idPlayer, type, 0);
	}

	@Override
	public List<Resource> getResources(int idPlayer) {
		List<Resource> resourceList = jdbcTemplate
				.query("SELECT * FROM resources WHERE idPlayer = ?",(ResultSet resultSet, int rowNum) -> {
							return new Resource(resultSet.getInt("type"),resultSet.getInt("amount"));
						},idPlayer);
		return resourceList;
	}
	
	
}
