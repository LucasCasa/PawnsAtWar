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
import ar.edu.itba.interfaces.UserDao;
import ar.edu.itba.model.Resource;

@Repository
public class ResourceJdbcDao implements ResourceDao {
	private final JdbcTemplate jdbcTemplate;
	private final SimpleJdbcInsert jdbcInsert;
	
	@Autowired
	UserDao ud;
	
	@Autowired
	public ResourceJdbcDao(final DataSource dataSource){
		jdbcTemplate = new JdbcTemplate(dataSource);
		jdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("resource");
	}

	@Override
	public void addAmount(int idPlayer, int type, int value) {
		Resource amount = getResource(idPlayer,type);
		jdbcTemplate.update("UPDATE resource SET amount = ? WHERE idPlayer = ? AND type = ?",amount.getQuantity() + value,idPlayer,type);
	}
	
	@Override
	public void setAmount(int idPlayer, int type, int value) {
		jdbcTemplate
				.update("UPDATE resource SET amount = ? WHERE idPlayer = ? AND type = ?",value,idPlayer,type);
	}

	@Override
	public void subtractAmount(int idPlayer, int type, int value) {
		Resource amount = getResource(idPlayer,type);
		int aux = amount.getQuantity() - value < 0 ? 0 : amount.getQuantity() - value ;
		jdbcTemplate
				.update("UPDATE resource SET amount = ? WHERE idPlayer = ? AND type = ?",aux,idPlayer,type);
	
	}


	@Override
	public Resource addResource(int idPlayer, int type, int amount) {
		final Map<String,Object> args = new HashMap<>();
		args.put("type", type);
		args.put("amount", amount);
		args.put("idPlayer",idPlayer);
		jdbcInsert.execute(args);
		return new Resource(type,ud.findById(idPlayer),amount);
	}

	@Override
	public Resource addResource(int idPlayer, int type) {
		return addResource(idPlayer, type, 0);
	}

	@Override
	public List<Resource> getResources(int idPlayer) {
		List<Resource> resourceList = jdbcTemplate
				.query("SELECT * FROM resource WHERE idPlayer = ?",(ResultSet resultSet, int rowNum) -> {
							return new Resource(resultSet.getInt("type"),ud.findById(resultSet.getInt("idPlayer")),resultSet.getInt("amount"));
						},idPlayer);
		return resourceList;
	}

	@Override
	public Resource getResource(int idPlayer, int type) {
		List<Resource> resourceList = jdbcTemplate
				.query("SELECT * FROM resource WHERE idPlayer = ? AND type = ?",(ResultSet resultSet, int rowNum) -> {
							return new Resource(resultSet.getInt("type"),ud.findById(resultSet.getInt("idPlayer")),resultSet.getInt("amount"));
						},idPlayer,type);
		return resourceList.size()>0?resourceList.get(0):null;
	}
	
	
}
