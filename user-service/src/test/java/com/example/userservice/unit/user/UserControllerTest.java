package com.example.userservice.unit.user;

import com.example.userservice.controller.users.UserController;
import com.example.userservice.controller.users.requests.CreateUserRequest;
import com.example.userservice.controller.users.response.CreateUserResponse;
import com.example.userservice.controller.users.response.GetUserInformationResponse;
import com.example.userservice.fixtures.UserFixture;
import com.example.userservice.model.users.User;
import com.example.userservice.service.users.IUserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;
import java.util.UUID;

import static org.springframework.test.util.AssertionErrors.assertEquals;

public class UserControllerTest {

    @Mock
    private IUserService userService;

    @InjectMocks
    private UserController userController;


    @Test
    void getMemberProfile() {
        String username = "testuser";
        User expectedUser = UserFixture.getUser();
        Mockito.when(userService.findUserInformation(username)).thenReturn(Optional.of(expectedUser));

        ResponseEntity<GetUserInformationResponse> response = userController.getUserInformation(username);

        assertEquals("STATUS", HttpStatus.OK, response.getStatusCode());
        GetUserInformationResponse responseBody = response.getBody();
        assertEquals("Email", expectedUser.getEmail(), responseBody.getEmail());
        assertEquals("Username", expectedUser.getUsername(), responseBody.getUsername());
    }

    @Test
    void getAllMembers(){

    }

    @Test
    void createNewMember(){
        CreateUserResponse user = UserFixture.getCreateMemberResponse();

        CreateUserRequest createUserRequest = UserFixture.getCreateMemberRequest();

//        Mockito.when(userService.saveUser(createUserRequest, "USER")).thenReturn();

    }

    @Test
    void deleteUserTest() {

//        //Arrange
//        UUID userID = UUID.randomUUID();
//        //Act
//        ResponseEntity<Void> responseEntity = userController.deleteUserByUUID(userID);
//        HttpStatus status = (HttpStatus) responseEntity.getStatusCode();
//        //Assert
//        Assertions.assertEquals(HttpStatus.NO_CONTENT,status);
    }


}
