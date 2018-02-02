package ar.edu.itba.paw.webapp.auth;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.time.ZonedDateTime;
import java.util.Date;

public class TokenCreator implements TokenHandler {

    private static final String KEY = "Vm0wd2QyUXlWa1pOVldSWFYwZG9WbFl3WkZOVlJscHpXa1"
            + "pPVjFKdGVEQlpNM0JIVmpKS1NHVkdiRnBOTTBKSVdWZDRTMlJXUm5OaQpSbkJPVW14d1VWWnRlR0ZUTV"
            + "ZweVRsWnNWd3BpUm5CVVdXdFdXbVZzV2xWVWJYUnJZa1ZLVTFsdWIzZFRkMjg5Q2c9PQo=Vm0wd2QyUXlWa1pO"
            + "VldSWFYwZG9WbFl3WkZOVlJscHpXa1pPVjFKdGVEQlpNM0JIVmpKS1NHVkdiRnBOTTBKSVdWZDRTMlJXUm5OaQpSbk"
            + "JPVW14d1VWWnRlR0ZUTVZweVRsWnNWd3BpUm5CVVdXdFdXbVZzV2xWVWJYUnJZa1ZLVTFsdWIzZFRkMjg5Q2c9PQo=";

    private final long EXPIRATION_DAYS = 7;

    @Override
    public String createToken(String username) {
        final Date expirationDate = Date.from(ZonedDateTime.now().plusDays(EXPIRATION_DAYS).toInstant());

        return Jwts.builder()
                .setSubject(username)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS256, KEY)
                .compact();
    }

    @Override
    public String mapToken(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(KEY)
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
        } catch(Exception e) {
            return null;
        }
    }
}
