package com.example.userservice.controller.users.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserResponse {
    private String username;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private String role;
    private Boolean isActive;
}
