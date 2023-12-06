package com.example.userservice.service.Jwts;


import com.example.userservice.configuration.exceptionHandler.UserNotFoundException;
import com.example.userservice.controller.security.AuthenticationRequest;
import com.example.userservice.controller.security.AuthenticationResponse;
import com.example.userservice.model.security.RefreshToken;
import com.example.userservice.model.security.TokenType;
import com.example.userservice.model.users.User;
import com.example.userservice.persistence.security.IRefreshTokenRepo;
import com.example.userservice.persistence.users.IUserRepo;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final IUserRepo userRepo;
    private final IRefreshTokenRepo iRefreshTokenRepo;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse authentication(AuthenticationRequest request){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        var user = userRepo.findByUsername(request.getUsername())
                .orElseThrow(()-> new UserNotFoundException("user not found"));

        boolean isActiveState = user.getIsActive();
        if(!isActiveState){
            throw new UserNotFoundException("User has been deactivated");
        }

        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);

        revokeAllUserTokens(user);
        saveUserToken(user,jwtToken);

        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    public void saveUserToken(User user, String jwtToken) {
        var token = RefreshToken.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        iRefreshTokenRepo.save(token);

    }

    public void revokeAllUserTokens(User user) {
        var validUserTokens = iRefreshTokenRepo.findAllByUser_UserIDAndExpiredIsFalseAndRevokedIsFalse(user.getUserID());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        iRefreshTokenRepo.saveAll(validUserTokens);
    }

}
