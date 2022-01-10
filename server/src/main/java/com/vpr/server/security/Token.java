package com.vpr.server.security;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.security.Key;

public class Token {

    private static Key KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    public static String Generate(String subject){
        return Jwts.builder().setSubject(subject).signWith(KEY).compact();
    }

    public static boolean Verify(String jws, String subject){
        try {
            assert Jwts.parserBuilder().setSigningKey(KEY).build().parseClaimsJws(jws)
                    .getBody().getSubject().equals(subject);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }
}
