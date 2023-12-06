package com.example.userservice.persistence.security;

import com.example.userservice.model.security.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface IRefreshTokenRepo extends JpaRepository<RefreshToken, UUID> {

    List<RefreshToken> findAllByUser_UserIDAndExpiredIsFalseAndRevokedIsFalse(UUID userId);

    Optional<RefreshToken> findByToken(String token);
}
