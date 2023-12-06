package com.example.userservice.service.users;

import com.example.userservice.controller.users.requests.CreateUserRequest;
import com.example.userservice.controller.users.response.CreateUserResponse;
import com.example.userservice.model.users.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IUserService {

    List<User> getAllUsers();
    CreateUserResponse createUser(CreateUserRequest request);

    CreateUserResponse createAdmin(CreateUserRequest user, int admin);

    User saveUser(CreateUserRequest request, int chosenRole);
    Optional<User> findUserInformation(String username);
    void deleteUser(UUID userID);
}
