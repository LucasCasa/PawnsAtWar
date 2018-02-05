package ar.edu.itba.paw.webapp.config;

import ar.edu.itba.paw.webapp.auth.AuthFilter;
import ar.edu.itba.paw.webapp.auth.LoginFilter;
import ar.edu.itba.paw.webapp.auth.TokenAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@ComponentScan("ar.edu.itba.paw.webapp.auth")
public class WebAuthConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private TokenAuthService tokenAuthService;

    @Autowired
    private UserDetailsService userDetailsService;

    protected void configure(final HttpSecurity http) throws Exception {
        http.csrf().disable();

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.authorizeRequests().antMatchers(HttpMethod.POST, "/api/users").permitAll().antMatchers("/api/**").authenticated();

        http.addFilterBefore(new LoginFilter("/api/login", tokenAuthService, userDetailsService, authenticationManager()), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new AuthFilter(tokenAuthService), UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    public void configure(final WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers("/css/*", "/js/**", "/img/**", "/favicon.ico", "/403");
    }

}
