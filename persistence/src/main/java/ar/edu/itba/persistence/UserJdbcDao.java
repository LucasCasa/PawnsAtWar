package ar.edu.itba.persistence;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import ar.edu.itba.interfaces.UserDao;
import ar.edu.itba.model.User;

public class UserJdbcDao implements UserDao{
	
	private final JdbcTemplate jdbcTemplate;
	private final SimpleJdbcInsert jdbcInsert;
	
	@Autowired
	public UserJdbcDao(final DataSource dataSource){
		jdbcTemplate = new JdbcTemplate(dataSource);
		jdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("userPaw").usingGeneratedKeyColumns("idPlayer");
	}

	@Override
	public User findbyId(long id) {
		List<User> resourceList = jdbcTemplate
				.query("SELECT * FROM userPaw WHERE idPlayer = ?",(ResultSet resultSet, int rowNum) -> {
							return new User(resultSet.getInt("idPlayer"),resultSet.getString("username"),resultSet.getString("password"),resultSet.getString("email"));
						},id);
		return resourceList.get(0);
	}
	
	@Override
	public User create(String username, String password, String email) {
		final Map<String,Object> args = new HashMap<>();
		args.put("username",username);
		args.put("password", password);
		args.put("email",email);
		final Number key = jdbcInsert.executeAndReturnKey(args);
		return new User(key.intValue(),username,password,email);
	}

	@Override
	public String getUsername(long id) {
		List<String> resourceList = jdbcTemplate
				.query("SELECT * FROM userPaw WHERE idPlayer = ?",(ResultSet resultSet, int rowNum) -> {
							return resultSet.getString("username");
						},id);
		return resourceList.get(0);
	}

}
