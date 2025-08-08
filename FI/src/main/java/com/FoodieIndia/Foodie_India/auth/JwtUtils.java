package com.FoodieIndia.Foodie_India.auth;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtUtils {

    @Value("${authSecretKey}")
    private String authSecretKey;

    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24)) //
                .signWith(SignatureAlgorithm.HS256, authSecretKey)
                .compact();
    }

    public String extractUsername(String token) {
        return Jwts.parser().setSigningKey(authSecretKey).parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateToken(String token) {
        return extractUsername(token) != null && !isTokenExpired(token);
    }

    public boolean isTokenExpired(String token) {
        return Jwts.parser().setSigningKey(authSecretKey).parseClaimsJws(token).getBody().getExpiration()
                .before(new Date());
    }
}
