package com.example.userservice.persistence.users;

import com.example.userservice.model.users.User;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface IUserRepo extends JpaRepository<User, UUID> {
    void deleteByUserID(UUID userID);

    User findUsersByUserID(UUID userID);
    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String Email);

    
    User findByUsernameAndPassword(String username, String password);

}
