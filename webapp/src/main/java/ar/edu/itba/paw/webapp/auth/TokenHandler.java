package ar.edu.itba.paw.webapp.auth;

public interface TokenHandler {

    /**
     * Given a username create a token for that users authentication
     *
     * @param username The user's username
     * @return The token
     */
    String createToken(String username);

    /**
     * Retrieves the username to whom the token was issued
     *
     * @param token The token received
     * @return The username
     */
    String mapToken(String token);
}
