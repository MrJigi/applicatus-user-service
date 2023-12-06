package com.example.userservice.unit.user;

import com.example.userservice.persistence.users.IUserRepo;
import com.example.userservice.service.users.UserService;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles
@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private IUserRepo userRepo;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;



}
