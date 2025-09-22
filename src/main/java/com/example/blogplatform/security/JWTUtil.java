package com.example.blogplatform.security;

import java.util.Date;
import java.security.Key;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.SignatureAlgorithm;


import org.springframework.stereotype.Component;

@Component
public class JWTUtil {

    private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private final Long expiration = 3600000L;
    
    public String generateToken(String username) {
        return Jwts.builder( )
                .setExpiration(new Date(System.currentTimeMillis( ) + expiration))
                .setIssuedAt(new Date( ))
                .setSubject(username)
                .signWith(key)
                .compact( );
    }

}
