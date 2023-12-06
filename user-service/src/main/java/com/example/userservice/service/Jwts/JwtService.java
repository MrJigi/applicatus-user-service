package com.example.userservice.service.Jwts;

import com.example.userservice.model.users.User;
import com.example.userservice.model.users.UserCredentials;
import com.example.userservice.persistence.users.IUserRepo;
import com.netflix.discovery.converters.Auto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import io.jsonwebtoken.io.Decoders;
import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;
import java.util.function.Function;
import java.lang.String;

@Service
//@AllArgsConstructor
@Slf4j
public class JwtService {

    @Value("${applicatus.app.jwtSecret}")
    private String jwtSecret;
    @Value("${applicatus.app.jwtExpiration}")
    private Long jwtExpiration;
    @Value("${applicatus.app.jwtRefreshExpiration}")
    private Long refreshExpiration;

    public String extractUserID(String userToken){
        try{
            Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(userToken).getBody();
            return claims.getId();
        }
        catch (io.jsonwebtoken.ExpiredJwtException | io.jsonwebtoken.MalformedJwtException | io.jsonwebtoken.SignatureException e){
            log.error("Error extracting user ID from token", e.getMessage());
            return null;
        }

    }

    public String extractUsername(String token) { return extractClaim(token,Claims::getSubject);}

    public <T> T extractClaim(String token, Function<Claims,T> claimsTFunction){
        final Claims claims = extractAllClaims(token);
        return claimsTFunction.apply(claims);
    }

    private Claims extractAllClaims(String token){
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String generateToken(Map<String,Object> claimsMap, UserDetails userDetails){
        return buildJwtToken(claimsMap,userDetails,jwtExpiration);
    }
    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }
    private String buildJwtToken(Map<String,Object> claimsMap, UserDetails userDetails,Long jwtExpiration){
        return Jwts
                .builder()
                .setClaims(claimsMap)
                .setSubject(userDetails.getUsername())
                .claim("role",((User)userDetails).getRole())
                .claim("userID",((User)userDetails).getUserID())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+ jwtExpiration))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact()
                ;
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtSecret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }








    public String generateRefreshToken(UserDetails userDetails){return buildJwtToken(new HashMap<>(),userDetails,refreshExpiration);}
//    private String generateJwtToken(UUID uuid, String username, User.Role userRole) {
//
//        Claims claims = Jwts.claims()
//                .setSubject(username)
//                .setIssuer("userMan")
//                        .setId(uuid.toString());
////        claims.put("uuid", uuid);
//        claims.put("username",username);
//        claims.put("scope", userRole);
//        claims.setExpiration(Date.from(LocalDateTime.now().plusMinutes(30).toInstant(ZoneOffset.UTC)));
//
//        // Add any additional claims as needed
//
//        return Jwts.builder()
//                .setClaims(claims)
//                .setHeaderParam("typ","JWT")
//                .signWith(SignatureAlgorithm.HS512, jwtSecret)
//                .compact();
//    }


}
