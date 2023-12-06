package com.example.userservice.controller.users.requests;

import com.example.userservice.model.users.User;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserRequest {
    private String username;
    private String password;
    @Email
    private String email;
    private String screenName;
    private String firstName;
    private String lastName;
    private User.Role roles;
    private Boolean isActive;
}
