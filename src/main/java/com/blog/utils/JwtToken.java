package com.blog.utils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtToken{
	//ask
	//private static final long serialVersionUID = -2550185165626007488L;
	@Value("${jwt.secret}")
	private String secretKey;
	//private final long expireDuration=1000*60*60*24;
	//Date currentDate = new Date();
    //Date expireDate = new Date(currentDate.getTime() + expireDuration);

	
	public String TokenGeneration(UserDetails username) {
		Map<String,Object> claims=new HashMap<>();
		return GenerateToken(username.getUsername(),claims);
	}
	
	public String GenerateToken(String username,Map<String,Object> claims) {
		Date expiredTime=new Date(System.currentTimeMillis()+1000*60*60*24);
		return Jwts.builder().setClaims(claims).setSubject(username)
		.setIssuedAt(new Date(System.currentTimeMillis()))
		.setExpiration(expiredTime)
		.signWith(SignatureAlgorithm.HS256, secretKey)
		.compact();
	}
	
	public String getNameFromToken(String token) {
		return getClaimFromToken(token, Claims::getSubject);
	}
	
	public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }
	
	public Claims getAllClaimsFromToken(String token) {
		return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
	}
	
	public Date getExpirationTimeFromToken(String token) {
		return getClaimFromToken(token, Claims::getExpiration);
	}
	public boolean isTokenExpired(String token) {
		Date expiredDate=getExpirationTimeFromToken(token);
		return expiredDate.before(new Date(System.currentTimeMillis()));
	}
	public boolean ValidateToken(String token,UserDetails userDetails) {
		String userName=getNameFromToken(token);
		return (userDetails.getUsername().equals(userName) && !isTokenExpired(token));
	}
}
