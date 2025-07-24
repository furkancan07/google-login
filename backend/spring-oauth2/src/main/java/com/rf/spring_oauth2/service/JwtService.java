package com.rf.spring_oauth2.service;

import com.rf.spring_oauth2.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtService {
    private final UserService userService;

    @Value("${jwt.secret}")
    private String SECRET_KEY;

    @Value("${jwt.expiration}")
    private long expirationTime;

    public JwtService(UserService userService) {
        this.userService = userService;
    }

    // token oluştur
    public String createToken(String email) {
        return Jwts.builder()
                .setSubject(email).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+expirationTime))
                .signWith(SignatureAlgorithm.HS256,SECRET_KEY)
                .compact();
    }
    // validate token
    public boolean validateToken(String token) {
        User user=getUser(token);
        return user!=null && !isTokenExpired(token);
    }
    // geçerlilik süresi
    private boolean isTokenExpired(String token) {
        return extractClaims(token).getExpiration().before(new Date());
    }

    private Claims extractClaims(String token) {
       return Jwts.parserBuilder().setSigningKey(SECRET_KEY).build().parseClaimsJws(token).getBody();
    }
    // useri al
    public User getUser(String token) {
        String email=getEmail(token);
        return userService.findByEmail(email);
    }
    private String getEmail(String token) {
        return extractClaims(token).getSubject();
    }

}
