package com.hubert.springjwt.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JwtUtil {
  
  private String SECRET =  "hubert";

  public String extractUsername(String token){
    return extractClaim(token, Claims::getSubject);
  }

  public <T> T extractClaim(String token, Function<Claims, T> claimResolver){
    final Claims claims = extractAllClaims(token);
    return claimResolver.apply(claims);
  }

  private Claims extractAllClaims(String token){
    return Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();
  }

  public Date extractExpiration(String token){
    return extractClaim(token, Claims::getExpiration);
  }

  private Boolean isTokenExpired(String token){
    return extractExpiration(token).before(new Date());
  }

  public String generateToken(String username){
    Map<String, Object> claims = new HashMap<>();
    return createToken(claims, username);
  }

  private String createToken(Map<String, Object> claims, String subject){
    return Jwts.builder()
    .setClaims(claims)
    .setSubject(subject)
    .setIssuedAt(new Date(System.currentTimeMillis()))
    .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
    .signWith(SignatureAlgorithm.HS256, SECRET).compact();
  }

  public Boolean validateToken(String token, UserDetails userDetails){
    final String username = extractUsername(token);
    return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
  }

}
