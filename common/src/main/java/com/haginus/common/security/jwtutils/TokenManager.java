package com.haginus.common.security.jwtutils;

import com.haginus.common.security.MyUserDetails;
import com.haginus.common.security.SecurityProperties;

import java.util.*;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class TokenManager {
  private final long TOKEN_VALIDITY = 1000 * 60 * 60 * 24 * 30L;
  private final String jwtSecret;

  public TokenManager(SecurityProperties securityProperties) {
    this.jwtSecret = securityProperties.getSecret();
  }

  public String generateJwtToken(MyUserDetails userDetails, TokenIssuer tokenIssuer) {
    userDetails.setIssuer(tokenIssuer);
    Map<String, Object> claims = new HashMap<>();
    claims.put("id", userDetails.getId());
    claims.put("firstName", userDetails.getFirstName());
    claims.put("lastName", userDetails.getLastName());
    claims.put("iss", userDetails.getIssuer());

    return Jwts.builder().setClaims(claims).setSubject(userDetails.getUsername())
      .setIssuedAt(new Date(System.currentTimeMillis()))
      .setExpiration(new Date(System.currentTimeMillis() + TOKEN_VALIDITY))
      .signWith(SignatureAlgorithm.HS512, jwtSecret).compact();
  }
  public Boolean validateJwtToken(String token, UserDetails userDetails) {
    String username = getUsernameFromToken(token);
    Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
    Boolean isTokenExpired = claims.getExpiration().before(new Date());
    return (username.equals(userDetails.getUsername()) && !isTokenExpired);
  }

  public MyUserDetails getUserFromToken(String token) {
    final Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
    Long id = claims.get("id", Long.class);
    String email = claims.getSubject();
    String firstName = claims.get("firstName", String.class);
    String lastName = claims.get("lastName", String.class);
    TokenIssuer issuer = TokenIssuer.valueOf(claims.get("iss", String.class));
    return new MyUserDetails(id, email, firstName, lastName, issuer);
  }

  public String getUsernameFromToken(String token) {
    final Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
    return claims.getSubject();
  }
}
