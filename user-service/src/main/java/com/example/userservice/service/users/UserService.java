package com.example.userservice.service.users;

import com.example.userservice.configuration.exceptionHandler.UserNotFoundException;
import com.example.userservice.controller.users.requests.CreateUserRequest;
import com.example.userservice.controller.users.response.CreateUserResponse;
import com.example.userservice.model.users.User;
import com.example.userservice.persistence.users.IUserRepo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
@Slf4j
//@EnableWebFluxSecurity
public class UserService implements IUserService {
    //    @EventListener(ApplicationReadyEvent.class)
//    public Users addTestUsers() {
//        Users users = new Users(
//                UUID.randomUUID(),
//                "Usagi",
//                "Minako",
//                "usagi@gmail.com",
//                "Usagi",
//                "password",
//                (long)2);
//        return IUserRepository.save(users);
//    }
    private final IUserRepo userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User.Role provideRole(int chosenRole) {
        if(chosenRole == 1){
            return User.Role.ADMIN;
        }
        if(chosenRole == 2) {
            return User.Role.USER;
        }
        return null;
    }
    @Override
    public CreateUserResponse createUser(CreateUserRequest user) {
        User newUser = saveUser(user,2);
        return CreateUserResponse.builder()
                .username(newUser.getUsername())
                .password(newUser.getPassword())
                .email(newUser.getEmail())
                .screenName(newUser.getScreenName())
                .firstName(newUser.getFirstName())
                .lastName(newUser.getLastName())
                .role(newUser.getRole())
                .isActive(newUser.getIsActive())
                .build();
    }

    @Override
    public CreateUserResponse createAdmin(CreateUserRequest user, int admin) {
        User newUser = saveUser(user, 1);
        return CreateUserResponse.builder()
                .username(newUser.getUsername())
                .password(newUser.getPassword())
                .email(newUser.getEmail())
                .screenName(newUser.getScreenName())
                .firstName(newUser.getFirstName())
                .lastName(newUser.getLastName())
                .role(newUser.getRole())
                .isActive(newUser.getIsActive())
                .build();
    }
    @Override
    public User saveUser(CreateUserRequest request, int chosenRole) {
        userRepository.findByUsername(request.getUsername()).ifPresent(user -> {
            throw new UserNotFoundException("User with username:" + request.getUsername() + "already exists");
        });
        userRepository.findByEmail(request.getEmail()).ifPresent(user -> {
            throw new UserNotFoundException("User with email:" + request.getUsername() + "already exists");
        });
        User newUser = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .email(request.getEmail())
                .firstName(request.getFirstName())
                .screenName(request.getScreenName())
                .lastName(request.getLastName())
                .role(provideRole(chosenRole))
                .isActive(request.getIsActive())
                .build();
        return userRepository.save(newUser);

    }

    @Override
    public Optional<User> findUserInformation(String username) {
        return userRepository.findByUsername(username);
    }
    @Override
    public void deleteUser(UUID userID) {
        this.userRepository.deleteByUserID(userID);
    }

//    @Override
//    public void disableUser(UUID userID) {
//        User existingUser = this.userRepository.findUsersByUserID(userID);
//        if (existingUser != null) {
//            existingUser.getIsActive();
//        }
//    }

    public User updateBuilder(User foundUser,User updatedUserInfo){
        foundUser.setEmail(updatedUserInfo.getEmail());
        foundUser.setFirstName(updatedUserInfo.getFirstName());
        foundUser.setLastName(updatedUserInfo.getLastName());
        foundUser.setUsername(updatedUserInfo.getUsername());
        return foundUser;
    }

    public User updateUser(UUID userID, User user) {
        User existingUser = userRepository.findUsersByUserID(userID);
        if (existingUser != null) {
            updateBuilder(existingUser,user);
            userRepository.save(user);
            log.info("User " + existingUser + " has been updated");
            return existingUser;
        }
        log.info("User " + existingUser + " is incorrect");
        throw new UserNotFoundException("User with name " + user.getUsername() + " Not found");

    }

}
