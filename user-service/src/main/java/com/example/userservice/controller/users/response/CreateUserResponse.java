package com.example.userservice.controller.users.response;

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
public class CreateUserResponse {
    private String username;
    private String password;
    private String email;
    private String screenName;
    private String firstName;
    private String lastName;
    private User.Role role;
    private Boolean isActive;
}
