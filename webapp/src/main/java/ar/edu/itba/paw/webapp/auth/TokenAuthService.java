package ar.edu.itba.paw.webapp.auth;

import ar.edu.itba.paw.webapp.DTOs.UserAuthDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class TokenAuthService {

    private static final String AUTH_HEADER = "X-AUTH-TOKEN";

    @Autowired
    private UserDetailsService userDetailsService;

    private final TokenHandler tokenHandler;

    public TokenAuthService() {
        this.tokenHandler = new TokenCreator();
    }

    Authentication getAuth(final HttpServletRequest request) {
        final String token = request.getHeader(AUTH_HEADER);
        Authentication authentication = null;

        if(token != null) {
            final String username = tokenHandler.mapToken(token);
            if(username != null) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                authentication = new UsernamePasswordAuthenticationToken(userDetails.getUsername(), userDetails.getPassword());
            }
        }

        return authentication;
    }

    void addAuth(final HttpServletResponse response, UserDetails userDetails) {
        response.setHeader(AUTH_HEADER, tokenHandler.createToken(userDetails.getUsername()));
    }

    Authentication getAuthForLogin(final HttpServletRequest request) {
        ObjectMapper mapper = new ObjectMapper();

        try {
            UserAuthDTO user = mapper.readValue(request.getInputStream(), UserAuthDTO.class);
            UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());

            if (user.getPassword().equals(userDetails.getPassword())) {
                return new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return null;
    }
}
