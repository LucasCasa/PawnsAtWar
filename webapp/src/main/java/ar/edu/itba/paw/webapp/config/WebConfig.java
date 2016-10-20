package ar.edu.itba.paw.webapp.config;

import java.nio.charset.StandardCharsets;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

@EnableWebMvc
@ComponentScan({"ar.edu.itba.paw.webapp.controller","ar.edu.itba.service","ar.edu.itba.persistence"})
@Configuration
@EnableTransactionManagement
public class WebConfig extends WebMvcConfigurerAdapter {

	@Value("classpath:schema.sql")
	private Resource schemaSql;

	@Value("classpath:map.sql")
	private Resource mapSql;

	@Bean
	public ViewResolver viewResolver(){
		final InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setViewClass(JstlView.class);
		viewResolver.setPrefix("/WEB-INF/jsp/");
		viewResolver.setSuffix(".jsp");

		return viewResolver;
	}

	@Bean
	public DataSource dataSource(){
		final SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
		dataSource.setDriverClass(org.postgresql.Driver.class);
		dataSource.setUrl("jdbc:postgresql://localhost:5432/paw-2016b-04");
		dataSource.setUsername("paw-2016b-04");
		dataSource.setPassword("idif6ieZ");
		return dataSource;
	}

	@Bean
	public DataSourceInitializer dataSourceInitializer (final DataSource ds){
		DataSourceInitializer dsi= new DataSourceInitializer();
		dsi.setDataSource(ds);
		dsi.setDatabasePopulator(databasePopulator());
		return dsi;

	}
	

	private DatabasePopulator databasePopulator(){
		final ResourceDatabasePopulator dbp = new ResourceDatabasePopulator();
		dbp.addScript(schemaSql);
		dbp.addScript(mapSql);
		return dbp;
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
	}
	
	@Bean
	 public MessageSource messageSource() {
		final ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasename("classpath:i18n/messages");
		messageSource.setDefaultEncoding(StandardCharsets.UTF_8.displayName());
		messageSource.setCacheSeconds(5);
		return messageSource;
	}
	
	 @Bean
	 public PlatformTransactionManager transactionManager(final DataSource ds) {
		 return new DataSourceTransactionManager(ds);
	 }

	@Bean
	public org.springframework.web.filter.CharacterEncodingFilter characterEncodingFilter() {
		org.springframework.web.filter.CharacterEncodingFilter characterEncodingFilter = new org.springframework.web.filter.CharacterEncodingFilter();
		characterEncodingFilter.setEncoding("UTF-8");
		characterEncodingFilter.setForceEncoding(true);
		return characterEncodingFilter;
	}
	
}
