package ar.edu.itba.paw.webapp.config;

import java.nio.charset.StandardCharsets;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import ar.edu.itba.model.Alert;
import ar.edu.itba.model.AlertType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

@Configuration
@EnableWebMvc
@EnableTransactionManagement
@EnableAsync
@EnableScheduling
@ComponentScan({"ar.edu.itba.paw.webapp.controller","ar.edu.itba.service","ar.edu.itba.persistence","ar.edu.itba.paw.webapp.config"})
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
//
//	@Bean
//	public DataSourceInitializer dataSourceInitializer (final DataSource ds){
//		DataSourceInitializer dsi= new DataSourceInitializer();
//		dsi.setDataSource(ds);
//		dsi.setDatabasePopulator(databasePopulator());
//		return dsi;
//
//	}
	
//	private DatabasePopulator databasePopulator(){
//		final ResourceDatabasePopulator dbp = new ResourceDatabasePopulator();
//		dbp.addScript(schemaSql);
//		//dbp.addScript(mapSql);
//		return dbp;
//	}

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
	public JavaMailSenderImpl mailSender() {
		JavaMailSenderImpl jmsi = new JavaMailSenderImpl();
		Properties p = new Properties();
		p.setProperty("mail.transport.protocol", "smtp");
		p.setProperty("mail.smtp.auth", "true");
		p.setProperty("mail.smtp.starttls.enable", "true");
		p.setProperty("mail.debug", "true");
		jmsi.setHost("smtp.gmail.com");
		jmsi.setPort(587);
		jmsi.setUsername("pawnsatwar@gmail.com");
		jmsi.setPassword("hoyquiero");
		jmsi.setJavaMailProperties(p);
		return jmsi;
	}
	 @Bean
	 public PlatformTransactionManager transactionManager(final EntityManagerFactory emf) {
	     return new JpaTransactionManager(emf);
	 }

	@Bean
	public org.springframework.web.filter.CharacterEncodingFilter characterEncodingFilter() {
		org.springframework.web.filter.CharacterEncodingFilter characterEncodingFilter = new org.springframework.web.filter.CharacterEncodingFilter();
		characterEncodingFilter.setEncoding("UTF-8");
		characterEncodingFilter.setForceEncoding(true);
		return characterEncodingFilter;
	}
	
	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(){
		final LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
		factoryBean.setPackagesToScan("ar.edu.itba.model");
		factoryBean.setDataSource(dataSource());
		final JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		factoryBean.setJpaVendorAdapter(vendorAdapter);
		final Properties properties = new Properties();
		properties.setProperty("hibernate.hbm2ddl.auto", "update");
		properties.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQL92Dialect");
		// Si ponen esto en prod, hay tabla!!
		//properties.setProperty("hibernate.show_sql", "true");
		//properties.setProperty("format_sql", "true");
		factoryBean.setJpaProperties(properties);
		return factoryBean;
	}

}
