package ar.edu.itba.paw.webapp.auth;

import ar.edu.itba.interfaces.UserService;
import ar.edu.itba.model.User;
import org.springframework.security.core.context.SecurityContextHolder;

public class AuthenticatedUser {

    public static String getUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
    }

    public static User getUser(UserService us) {
        return us.findByUsername(getUsername());
    }
}
