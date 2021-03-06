package ar.edu.itba.paw.webapp.config;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import ar.edu.itba.paw.webapp.auth.PawUserDetailsService;


@Configuration
@EnableWebSecurity
@ComponentScan("ar.edu.itba.paw.webapp.auth")
public class WebAuthConfig extends WebSecurityConfigurerAdapter{
	private static final String KEY = "Vm0wd2QyUXlWa1pOVldSWFYwZG9WbFl3WkZOVlJscHpXa1"
			+ "pPVjFKdGVEQlpNM0JIVmpKS1NHVkdiRnBOTTBKSVdWZDRTMlJXUm5OaQpSbkJPVW14d1VWWnRlR0ZUTV"
			+ "ZweVRsWnNWd3BpUm5CVVdXdFdXbVZzV2xWVWJYUnJZa1ZLVTFsdWIzZFRkMjg5Q2c9PQo=Vm0wd2QyUXlWa1pO"
			+ "VldSWFYwZG9WbFl3WkZOVlJscHpXa1pPVjFKdGVEQlpNM0JIVmpKS1NHVkdiRnBOTTBKSVdWZDRTMlJXUm5OaQpSbk"
			+ "JPVW14d1VWWnRlR0ZUTVZweVRsWnNWd3BpUm5CVVdXdFdXbVZzV2xWVWJYUnJZa1ZLVTFsdWIzZFRkMjg5Q2c9PQo=";
	
	@Autowired
	private PawUserDetailsService userDetailsService;
	
	protected void configure(final HttpSecurity http) throws Exception {
//		.authenticationProvider(authProvider)
		http.userDetailsService(userDetailsService)
		.sessionManagement()
		.invalidSessionUrl("/login")

		.and().authorizeRequests()
		.antMatchers("/login/**").anonymous()
		.antMatchers("/admin/**").hasRole("ADMIN")
		.antMatchers("/**").authenticated()

		.and().formLogin()
		.usernameParameter("j_username")
		.passwordParameter("j_password")
		.defaultSuccessUrl("/", false)
//		.successHandler(authSuccessHandler)
		.failureUrl("/login?error")
		.loginPage("/login")
		.loginProcessingUrl("/login")

		.and().rememberMe()
		.rememberMeParameter("j_rememberme")
		.userDetailsService(userDetailsService)
		.key(KEY)
		.tokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(30))

		.and().logout()
		.logoutUrl("/logout")
		.logoutSuccessUrl("/login?logout")

		.and().exceptionHandling()
		.accessDeniedPage("/403")

		.and().csrf()
		.disable();
	}
	
	@Override
	public void configure(final WebSecurity web) throws Exception {
		web.ignoring()
				.antMatchers("/css/*", "/js/**", "/img/**", "/favicon.ico", "/403");
	}

}
