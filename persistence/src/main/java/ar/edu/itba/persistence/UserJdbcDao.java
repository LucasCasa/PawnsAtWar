package ar.edu.itba.persistence;

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
		jdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("users").usingGeneratedKeyColumns("idPlayer");
	}

	@Override
	public User findbyId(long id) {
		return null;
	}

	@Override
	public User create(String username, String email) {
		return null;
	}

}
