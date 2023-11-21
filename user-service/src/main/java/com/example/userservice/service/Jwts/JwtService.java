package com.example.userservice.service.Jwts;

import com.example.userservice.model.users.User;
import com.example.userservice.model.users.UserCredentials;
import com.example.userservice.persistence.users.IUserRepo;
import com.netflix.discovery.converters.Auto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
@Slf4j
public class JwtService {

//    public static final String SECRET = "lkjkajgsd";
//    public Jws<Claims> validateToken(final String token) {
//        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(getSignKey()).build().parse();
//    }


    final private PasswordEncoder passwordEncoder;


    private final IUserRepo userRepository;


//    @Value("${applicatus.secret}")
    private final String JwtSecret = "SecretKeySecretKeySecretKey";

//    @Bean
//    public String JwtSecret(){
//        return JwtSecret;
//    }

    public String extractUserID(String userToken){
        try{
            Claims claims = Jwts.parser().setSigningKey(JwtSecret).parseClaimsJws(userToken).getBody();
            return claims.getId();
        }
        catch (io.jsonwebtoken.ExpiredJwtException | io.jsonwebtoken.MalformedJwtException | io.jsonwebtoken.SignatureException e){
            log.error("Error extracting user ID from token", e.getMessage());
            return null;
        }

    }

    public String provideToken(UserCredentials userCredentials){
        // Retrieve user from the database based on username
        //Make it inherit from user as userDescription
        Optional<User> user = userRepository.findByUsername(userCredentials.getUsername());

        // Check if user exists and password is correct
        if (user == null || !passwordEncoder.matches(userCredentials.getPassword(), user.get().getPassword())) {
            return null;
            //return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
//        String token = Jwts.builder()
//                .setSubject(user.get().getUsername())
//                .claim("authorities", user.get().getRole())
//                .claim("principal", user.get().getUserID())
//                .setIssuedAt(new Date())
//                .setIssuer()


        // Generate JWT token
        String token = generateJwtToken(user.get().getUserID(), user.get().getUsername(),user.get().getRole());

        return token;
    }
    private String generateJwtToken(UUID uuid, String username, User.Role userRole) {

        Claims claims = Jwts.claims()
                .setSubject(username)
                .setIssuer("userMan")
                        .setId(uuid.toString());
//        claims.put("uuid", uuid);
        claims.put("username",username);
        claims.put("scope", userRole);
        claims.setExpiration(Date.from(LocalDateTime.now().plusMinutes(30).toInstant(ZoneOffset.UTC)));

        // Add any additional claims as needed

        return Jwts.builder()
                .setClaims(claims)
                .setHeaderParam("typ","JWT")
                .signWith(SignatureAlgorithm.HS512, JwtSecret)
                .compact();
    }


}
