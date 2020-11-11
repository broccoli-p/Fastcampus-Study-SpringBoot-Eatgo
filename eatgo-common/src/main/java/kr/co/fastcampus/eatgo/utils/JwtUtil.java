package kr.co.fastcampus.eatgo.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;

public class JwtUtil {

    private Key key;

    public JwtUtil(String secret) {

        this.key = Keys.hmacShaKeyFor(secret.getBytes());
    }

    public String createToken(Long userid, String name) {
        String token = Jwts.builder()
            .claim("userid", userid)
            .claim("name", name)
            .signWith(key, SignatureAlgorithm.HS256)
            .compact();
        return token;
    }
}
