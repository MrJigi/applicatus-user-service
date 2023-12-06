package com.example.userservice.controller.users;

import com.example.userservice.controller.users.requests.CreateUserRequest;
import com.example.userservice.controller.users.response.CreateUserResponse;
import com.example.userservice.controller.users.response.GetUserInformationResponse;
import com.example.userservice.controller.users.response.GetUserProfileResponse;
import com.example.userservice.model.users.User;
import com.example.userservice.service.users.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.apache.coyote.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/user")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

//    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @GetMapping("/getAll")
    public List<User> getAllUsers() {
        LOGGER.info("User-service: get all users");
        List<User> allUsers = userService.getAllUsers();
        if(!allUsers.isEmpty()) {
            LOGGER.info("User-service: provided users");
            return userService.getAllUsers();
        }
        else{
            LOGGER.info("User-service: get all users empty error");
            return null;
        }
    }


    @GetMapping("/{userUsername}/info")
    public ResponseEntity<GetUserProfileResponse> getUserProfileWithoutAuthentication(@PathVariable String userUsername){
        LOGGER.info("User-service: get user info auth", userUsername);

        Optional<User> user = userService.findUserInformation(userUsername);
        if(user.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        else {
            User foundUser = user.get();
            GetUserProfileResponse response = new GetUserProfileResponse(
                    foundUser.getUsername(),
                    foundUser.getEmail(),
                    foundUser.getScreenName(),
                    foundUser.getFirstName(),
                    foundUser.getLastName(),
                    foundUser.getRole(),
                    foundUser.getIsActive()

            );
            return ResponseEntity.ok(response);
        }
//        return ResponseEntity.ok().build();
    }

    @PostMapping("/register")
    public ResponseEntity<CreateUserResponse> registerUser(@RequestBody @Valid CreateUserRequest request){
        LOGGER.info("User-service: user registration", request);

        CreateUserResponse response = userService.createUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/registerAdmin")
    public ResponseEntity<CreateUserResponse> registerAdmin(@RequestBody @Valid CreateUserRequest request){
        LOGGER.info("User-service: admin registration", request);

        CreateUserResponse response = userService.createAdmin(request, 2);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    @GetMapping("/{userUsername}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<GetUserInformationResponse> getUserInformation(@PathVariable String userUsername) {
        LOGGER.info("User-service: get user profile as admin", userUsername);

        Optional<User> user = userService.findUserInformation(userUsername);
        if(user.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        else{
            User madeUser = user.get();
            GetUserInformationResponse response = new GetUserInformationResponse(
                    madeUser.getUsername(),
                    madeUser.getEmail(),
                    madeUser.getScreenName(),
                    madeUser.getFirstName(),
                    madeUser.getLastName(),
                    madeUser.getRole(),
                    madeUser.getIsActive()
            );
            return ResponseEntity.ok(response);
        }
    }

   /* @GetMapping("/disable/{userID}")
    public ResponseEntity<GetUserInformationResponse> disableUser(@PathVariable UUID userID) {
        userService.dissableUser()
        Optional<User> user = userService.findUserInformation(userUsername);
        if(user.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        else{
            User madeUser = user.get();
            GetUserInformationResponse response = new GetUserInformationResponse(
                    madeUser.getUsername(),
                    madeUser.getEmail(),
                    madeUser.getFirstName(),
                    madeUser.getLastName(),
                    madeUser.getRole(),
                    madeUser.getIsActive()
            );
            return ResponseEntity.ok(response);
        }
    }*/

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/remove/{userID}")
    public ResponseEntity<Void> deleteUserByUUID(@PathVariable UUID userID) {
        LOGGER.info("User-service: delete user by UUID", userID);

        userService.deleteUser(userID);
        return ResponseEntity.noContent().build();
    }
    //Fix this up with a response object

    //This updates based on provided User object.
    //Meybe update another one based on UUID Path variable *Ask teacher on this
    @PutMapping("/update/{userID}")
    @ResponseStatus(HttpStatus.OK)
    public void updateUser(@RequestBody User user, @PathVariable UUID userID) {
        userService.updateUser(userID, user);
    }

    //Need additional logic to register the user with the UserDetails to set a profile with authorization

}
