package ar.edu.itba.paw.webapp.auth;

import org.springframework.security.core.context.SecurityContextHolder;

public class AuthenticatedUser {

    public static int getId() {
        return Integer.parseInt(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
    }
}
