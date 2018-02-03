package ar.edu.itba.paw.webapp.auth;

import ar.edu.itba.interfaces.UserService;
import ar.edu.itba.paw.webapp.DTOs.UserAuthDTO;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;

@Component
public class TokenAuthService {

    private static final String AUTH_HEADER = "X-AUTH-TOKEN";

    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private UserService userService;

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
                authentication = new UsernamePasswordAuthenticationToken(userDetails.getUsername(), userDetails.getPassword(),userDetails.getAuthorities());
            }
        }

        return authentication;
    }

    void addAuth(final HttpServletResponse response, UserDetails userDetails) {
        response.setHeader(AUTH_HEADER, tokenHandler.createToken(userDetails.getUsername()));
    }

    Authentication getAuthForLogin(final HttpServletRequest request) {
        ObjectMapper mapper = new ObjectMapper();
        //mapper.configure(JsonParser.Feature.AUTO_CLOSE_SOURCE, true);

        try(BufferedReader reader = request.getReader()) {
            StringBuilder builder = new StringBuilder();

            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line).append('\n');
            }

            UserAuthDTO user = mapper.readValue(builder.toString(), UserAuthDTO.class);
            UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());

            if (userService.exists(user.getUsername(), user.getPassword())) {
                return new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(), userDetails.getAuthorities());
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return null;
    }
}
