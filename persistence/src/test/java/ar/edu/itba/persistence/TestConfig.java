package ar.edu.itba.persistence;

import javax.sql.DataSource;

import org.hsqldb.jdbc.JDBCDriver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

@ComponentScan({"ar.edu.itba.persistence",})
@Configuration
public class TestConfig {
	
	@Bean
	public DataSource dataSource(){
		final SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
		dataSource.setDriverClass(JDBCDriver.class);
		dataSource.setUrl("jdbc:hsqldb:mem:paw");
		dataSource.setUsername("Magdalena");
		dataSource.setPassword("hoyquiero");

		return dataSource;
	}

}
