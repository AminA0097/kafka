package com.EmailVerfication.EmaiService.Service;

import io.jsonwebtoken.Claims;
import org.springframework.stereotype.Service;
import java.security.Key;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.Date;
import java.util.function.Function;

@Service
public class JwtService {
    private String secret = "Ece72f7qGgGHtL3iDzu4G9dTG8JEehJxq7Vdn7ElDuo";
    private long expiration = 1000 * 60 * 5;
    private String generateToken(String email,String userName){
        return Jwts.builder()
                .setSubject(email)
                .claim("name",userName)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }
    public String builToken(String email,String userName){
        return generateToken(email,userName);
    }
    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }
    public Claims validateToken(String token) {
        return
                Jwts.parser()
                        .setSigningKey(secret)
                        .parseClaimsJws(token)
                        .getBody();
    }
    public boolean isExpired(String token) {
        return extractExpiration(token).before(new Date());
    }
    private Claims extractAllCalims(String jwt) {
        return
                Jwts.parserBuilder()
                        .setSigningKey(getSigningKey())
                        .build()
                        .parseClaimsJws(jwt)
                        .getBody();
    }
    private <T> T extractClaim(String jwt, Function<Claims,T> claimsResolver) {
        final Claims claims = extractAllCalims(jwt);
        return claimsResolver.apply(claims);
    }
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
}
