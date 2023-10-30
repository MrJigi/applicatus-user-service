package com.example.userservice.controller.users.requests;

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
    private String firstName;
    private String lastName;
    private String roles;
    private Boolean isActive;
}
