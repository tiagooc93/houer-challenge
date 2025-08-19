package com.example.houer_challenge.service;

import com.example.houer_challenge.model.UserInfo;
import com.example.houer_challenge.repository.UserInfoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserInfoServiceTest {

    @Mock
    private UserInfoRepository userRepository;
    @InjectMocks
    private UserInfoService userService;

    @Test
    public void createUserTest(){
        //Arrange
        UserInfo user = new UserInfo();
        user.setEmail("joao@test.com");
        user.setPassword("12345");

        when(userRepository.save(user)).thenReturn(user);

        //Act
        UserInfo createdUser = userService.addUser((user));

        //Assert
        assertEquals(user.getEmail(),createdUser.getEmail());
        assertEquals(user.getPassword(),createdUser.getPassword());
    }

    @Test
    public void createUserWithSameEmail(){
        //Arrange
        UserInfo user = new UserInfo();
        user.setEmail("joao@test.com");
        user.setPassword("12345");

        when(userRepository.existsByEmail(user.getEmail())).thenReturn(true);

        //Act and Assert
        RuntimeException exception = assertThrows(RuntimeException.class,(() -> userService.addUser(user)));
        assertEquals("Email already in use !", exception.getMessage());
    }

}