package com.petstagram.configuration;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.servlet.http.HttpServletRequest;

import com.petstagram.data.Users;
import com.petstagram.service.UserServiceImpl;
import io.jsonwebtoken.Jws;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenUtil implements Serializable {
	
	@Autowired
	private UserServiceImpl userService;
	
	private static final long serialVersionUID = -2550185165626007488L;
    public static final long JWT_TOKEN_VALIDITY = 6 * 60 * 60;
    @Value("${security.oauth2.resource.jwt.key-value}")
    private String secret;

    //retrieve useremail from jwt token
    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    //retrieve expiration date from jwt token
    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    //for retrieveing any information from token we will need the secret key
    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    //check if the token has expired
    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    //generate token for user
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        Map<String, Object> headers = new HashMap<>();
        headers.put("type", "JWT");
//        headers.put("algo", "HS512");
//        Long expiredTime = 1000 * 60l * 30; // 30분 만료시간
//        Date now = new Date();
//        now.setTime(now.getTime() + expiredTime);
        Users getUser = new Users();
        getUser.setUserEmail(userDetails.getUsername());
        Users user = userService.getUsers(getUser);
        System.out.println("TOKEN: "+user);
//        claims.put("exp", now);
        claims.put("userEmail", userDetails.getUsername());
        claims.put("userNickname", user.getUserNickname());
        return doGenerateToken(claims, headers);
    }

    //while creating the token -
	//1. Define  claims of the token, like Issuer, Expiration, Subject, and the ID
	//2. Sign the JWT using the HS512 algorithm and secret key.
	//3. According to JWS Compact Serialization(https://tools.ietf.org/html/draft-ietf-jose-json-web-signature-41#section-3.1)
	//   compaction of the JWT to a URL-safe string
//    private String doGenerateToken(Map<String, Object> claims, String subject) {
//        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
//            //.setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
//            .setExpiration(new Date(System.currentTimeMillis() + 5 * 1000))
//            .signWith(SignatureAlgorithm.HS512, secret).compact();
//    }
    
    private String doGenerateToken(Map<String, Object> claims, Map<String, Object> headers) {
    	return Jwts.builder()
    			.setHeader(headers)
    			.setClaims(claims)
    			.setIssuedAt(new Date(System.currentTimeMillis()))
    			.setExpiration(new Date(System.currentTimeMillis() + 1000 * JWT_TOKEN_VALIDITY))
    			.signWith(SignatureAlgorithm.HS512, secret)
    			.compact();
    }

    //validate token
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
    
    public String getTokenByRequest(HttpServletRequest request) {
    	String requestTokenHeader = request.getHeader("Authorization");
    	return requestTokenHeader.substring(7);
    }
    
    public String getNicknameByToken(String token) {
        Jws<Claims> claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
        return (String) claims.getBody().get("userNickname");
//    	Claims claims = Jwts.parser()
//    			.setSigningKey(secret)
//    			.parseClaimsJws(token)
//    			.getBody();
//    	return claims.get("username", String.class);
    }

    public String getEmailByToken(String token) {
        Jws<Claims> claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
        return (String) claims.getBody().get("userEmail");
//    	Claims claims = Jwts.parser()
//    			.setSigningKey(secret)
//    			.parseClaimsJws(token)
//    			.getBody();
//    	return claims.get("useremail", String.class);
    }
}
