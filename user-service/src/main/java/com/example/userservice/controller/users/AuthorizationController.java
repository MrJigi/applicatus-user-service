package com.example.userservice.controller.users;

import com.example.userservice.controller.users.requests.CreateUserRequest;
import com.example.userservice.controller.users.response.CreateUserResponse;
import com.example.userservice.model.users.User;
import com.example.userservice.model.users.UserCredentials;
import com.example.userservice.persistence.users.IUserRepo;
//import com.example.userservice.service.Jwts.AuthenticationService;
import com.example.userservice.service.Jwts.JwtService;
import com.example.userservice.service.users.UserService;
import com.netflix.discovery.converters.Auto;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.TextCodec;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import io.jsonwebtoken.Claims;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/auth")
public class AuthorizationController {



    @Autowired
    private UserService userService;
    @Autowired
    private JwtService jwtService;

    public AuthorizationController() {
    }

    @GetMapping("/getUserIdentification/{userToken}")
    public ResponseEntity<String> getUserDetails(@PathVariable String userToken){
        return ResponseEntity.ok().body(jwtService.extractUserID(userToken));
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody UserCredentials userCredentials) {


        // Return the token as the response

        return ResponseEntity.ok().body(jwtService.provideToken(userCredentials));
    }

    @PostMapping("/register")
    public ResponseEntity<CreateUserResponse> registerUser(@RequestBody @Valid CreateUserRequest userRequest){
        CreateUserResponse userResponse = userService.createUser(userRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(userResponse);

    }

}
