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

import ar.edu.itba.interfaces.UserDao;
import ar.edu.itba.model.User;

public class UserJdbcDao implements UserDao{
	
	private final JdbcTemplate jdbcTemplate;
	private final SimpleJdbcInsert jdbcInsert;
	
	@Autowired
	public UserJdbcDao(final DataSource dataSource){
		jdbcTemplate = new JdbcTemplate(dataSource);
		jdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("userPaw").usingGeneratedKeyColumns("idplayer");
	}

	@Override
	public User findById(int id) {
		List<User> userList = jdbcTemplate
				.query("SELECT * FROM userPaw WHERE idPlayer = ?",(ResultSet resultSet, int rowNum) -> {
							return new User(resultSet.getString("username"),resultSet.getString("password"),resultSet.getString("email"));
						},id);
		return userList.isEmpty()?null:userList.get(0);

	}
	
	@Override
	public User create(String username, String password, String email) {
		final Map<String,Object> args = new HashMap<>();
		args.put("username",username);
		args.put("password", password);
		args.put("email",email);
		final Number key = jdbcInsert.executeAndReturnKey(args);
		return new User(username,password,email);
	}

	@Override
	public String getUsername(int id) {
		List<String> userList = jdbcTemplate
				.query("SELECT * FROM userPaw WHERE idPlayer = ?",(ResultSet resultSet, int rowNum) -> {
							return resultSet.getString("username");
						},id);
		return userList.isEmpty()?null:userList.get(0);
	}

	@Override
	public User findByUsername(String username) {
		List<User> userList = jdbcTemplate
				.query("SELECT * FROM userPaw WHERE username = ?",(ResultSet resultSet, int rowNum) -> {
							return new User(resultSet.getString("username"),resultSet.getString("password"),resultSet.getString("email"));
						},username);
		return userList.isEmpty()?null:userList.get(0);

	}

	@Override
	public String getEmail(int id) {
		List<String> userList = jdbcTemplate
				.query("SELECT * FROM userPaw WHERE idPlayer = ?",(ResultSet resultSet, int rowNum) -> {
							return resultSet.getString("email");
						},id);
		return userList.isEmpty()?null:userList.get(0);
	}

	@Override
	public String getEmail(String username) {
		List<String> userList = jdbcTemplate
				.query("SELECT * FROM userPaw WHERE username = ?",(ResultSet resultSet, int rowNum) -> {
							return resultSet.getString("email");
						},username);
		return userList.isEmpty()?null:userList.get(0);

	}

	@Override
	public boolean exists(String username, String password) {
		List<Integer> userList = jdbcTemplate
				.query("SELECT count(*) as aux FROM userPaw WHERE username = ? AND password = ? ",(ResultSet resultSet, int rowNum) -> {
							return new Integer(resultSet.getInt("aux"));
						},username,password);
		return userList.get(0) != 0 ? true : false;
	}

	@Override
	public boolean exists(String username) {
		List<Integer> userList = jdbcTemplate
				.query("SELECT count(*) as aux FROM userPaw WHERE username = ?",(ResultSet resultSet, int rowNum) -> {
							return new Integer(resultSet.getInt("aux"));
						},username);
		return userList.get(0) != 0 ? true : false;
	}

}
