package com.example.demo.service;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    private UserRepository userRepo;
    private UserService userService;

    @BeforeEach
    void setUp() {
        userRepo = Mockito.mock(UserRepository.class);
        userService = new UserService(userRepo);
    }

    @Test
    void signup_ShouldThrowException_WhenUsernameIsEmpty() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.signup("", "1234");
        });
        assertTrue(exception.getMessage().contains("Username cannot be empty"));
    }

    @Test
    void signup_ShouldThrowException_WhenPasswordTooShort() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.signup("john", "12");
        });
        assertTrue(exception.getMessage().contains("Password must be at least 4 characters"));
    }

    @Test
    void signup_ShouldReturnUser_WhenValidInput() {
        when(userRepo.findByUsername("john")).thenReturn(null);

        User savedUser = new User();
        savedUser.setId(1L);
        savedUser.setUsername("john");
        savedUser.setPassword("1234");

        when(userRepo.save(any(User.class))).thenReturn(savedUser);

        User result = userService.signup("john", "1234");

        assertNotNull(result);
        assertEquals("john", result.getUsername());
    }

    @Test
    void login_ShouldReturnUser_WhenCorrectCredentials() {
        User user = new User();
        user.setUsername("john");
        user.setPassword("1234");

        when(userRepo.findByUsername("john")).thenReturn(user);

        User result = userService.login("john", "1234");

        assertNotNull(result);
        assertEquals("john", result.getUsername());
    }

    @Test
    void login_ShouldReturnNull_WhenWrongPassword() {
        User user = new User();
        user.setUsername("john");
        user.setPassword("1234");

        when(userRepo.findByUsername("john")).thenReturn(user);

        User result = userService.login("john", "wrong");
        assertNull(result);
    }
}
