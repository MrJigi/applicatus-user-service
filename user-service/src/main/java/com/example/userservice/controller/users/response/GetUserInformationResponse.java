package com.example.userservice.controller.users.response;

import com.example.userservice.model.users.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetUserInformationResponse {
    private String username;
    private String email;
    private String screenName;
    private String firstName;
    private String lastName;
    private User.Role role;
    private Boolean isActive;
}
