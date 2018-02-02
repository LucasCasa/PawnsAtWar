package ar.edu.itba.paw.webapp.auth;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginFilter extends AbstractAuthenticationProcessingFilter {

    private final TokenAuthService tokenAuthService;
    private final UserDetailsService userDetailsService;

    public LoginFilter(String urlMapping, TokenAuthService tokenAuthService,
                       UserDetailsService userDetailsService, AuthenticationManager authenticationManager) {
        super(new AntPathRequestMatcher(urlMapping));
        this.tokenAuthService = tokenAuthService;
        this.userDetailsService = userDetailsService;
        this.setAuthenticationManager(authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
            throws AuthenticationException, IOException, ServletException {
        final Authentication authentication = tokenAuthService.getAuthForLogin(httpServletRequest);
        if (authentication == null) {
            throw new CustomAuthenticationException("Invalid Credentials");
        }
        return authentication;
    }

    @Override
    public void successfulAuthentication(HttpServletRequest httpServletRequest, HttpServletResponse response, FilterChain filterChain,
                                         Authentication authentication) throws IOException, ServletException {
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authentication.getName());

        tokenAuthService.addAuth(response, userDetails);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private class CustomAuthenticationException extends AuthenticationException {
        public CustomAuthenticationException(String msg) {
            super(msg);
        }
    }
}
