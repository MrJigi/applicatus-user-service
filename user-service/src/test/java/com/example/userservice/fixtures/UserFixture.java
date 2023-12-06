package com.example.userservice.fixtures;

import com.example.userservice.controller.users.requests.CreateUserRequest;
import com.example.userservice.controller.users.response.CreateUserResponse;
import com.example.userservice.model.users.User;
import com.example.userservice.controller.users.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class UserFixture {

    public static User getUser() {
        User user = new User();
        user.setUserID(UUID.randomUUID());
        user.setFirstName("TestFirst");
        user.setLastName("TestLast");
        user.setEmail("test@gmail.com");
        user.setRole(User.Role.USER);
        user.setUsername("UsernameTest");
        user.setPassword("test");
        user.setIsActive(true);
        return user;
    }

    public static User getUserWithId(UUID Id) {
        User user = new User();
        user.setUserID(Id);
        user.setFirstName("TestFirst");
        user.setLastName("TestLast");
        user.setEmail("test@gmail.com");
        user.setRole(User.Role.USER);
        user.setUsername("UsernameTest");
        user.setPassword("test");
        user.setIsActive(true);
        return user;
    }

    public static CreateUserRequest getCreateMemberRequest() {
        return new CreateUserRequest(
                "TestFirst",
                "TestLast",
                "test@gmail.com",
                "test.com",

                "MEMBER",
                "UsernameTest",
                User.Role.USER,
                true
        );
    }

//    public static CreateUserResponse getCreateMemberResponse() {
//        return new CreateUserResponse(
//                "TestFirst",
//                "TestLast",
//                "test@gmail.com",
//                "test.com",
//                User.Role.USER,
//                "TestScreenName",
//                "UsernameTest"
//        );
//    }
//    public static UpdateMemberDetailRequest getUpdateMemberRequest() {
//        return new UpdateMemberDetailRequest(
//                "test_edit@gmail.com",
//                "EditedScreenName",
//                "UsernameTest"
//        );
//    }
//    public static UpdateStateAccountRequest getUpdateStateAccountRequest() {
//        return new UpdateStateAccountRequest(
//                false
//        );
//    }


    public static User getUser(final Integer index) {
        User user = getUser();
        user.setFirstName(String.format("TestFirst-%s", index));
        user.setLastName(String.format("TestLast-%s", index));
        user.setEmail(String.format("test-%s@gmail.com", index));
        user.setRole(User.Role.valueOf(String.format(String.valueOf(User.Role.USER), index)));
        user.setUsername(String.format("test-%s", index));
        user.setPassword("test");
        user.setIsActive(true);
        return user;
    }

    public static List<User> getUsers(Integer amount){
        if(amount == 0){
            return new ArrayList<>();
        }
        return Stream.iterate(0, i -> i + 1)
                .limit(amount)
                .map(UserFixture::getUser)
                .collect(Collectors.toList());
    }
}
