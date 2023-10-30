package com.example.userservice.controller.users;

import com.example.userservice.model.users.User;
import com.example.userservice.model.users.UserCredentials;
import com.example.userservice.persistence.users.IUserRepo;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import io.jsonwebtoken.Claims;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/auth")
public class AuthorizationController {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private IUserRepo userRepository;

    public AuthorizationController() {
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserCredentials userCredentials) {
        // Retrieve user from the database based on username
        //Make it inherit from user as userDescription
        Optional<User> user = userRepository.findByUsername(userCredentials.getUsername());

        // Check if user exists and password is correct
        if (user == null || !passwordEncoder.matches(userCredentials.getPassword(), user.get().getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        // Generate JWT token
        String token = generateJwtToken(user.get().getUserID(), user.get().getUsername());

        // Return the token as the response

        return ResponseEntity.ok(token);
    }

    private String generateJwtToken(UUID uuid, String username) {
        Claims claims = Jwts.claims().setSubject(username);
        claims.put("uuid", uuid);
        claims.put("username",username);
        // Add any additional claims as needed

        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, "yourSecretKey")
                .compact();
    }
}
