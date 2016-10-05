package ar.edu.itba.persistence;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

@ComponentScan({"ar.edu.itba.persistence",})
@Configuration
public class TestConfig {
	
	@Bean
	public DataSource dataSource(){
		final SimpleDriverDataSource ds = new SimpleDriverDataSource();
		return ds;
	}

}
