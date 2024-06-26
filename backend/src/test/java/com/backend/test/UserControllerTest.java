package com.backend.test;

import com.backend.controller.UserController;
import com.backend.controller.response.UserResponse;
import com.backend.model.User;
import com.backend.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

public class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    public void shouldGetUserByIdFound() {
        User user = User.builder().id(1L).name("John Doe").email("john@example.com").build();
        when(userService.findById(1L)).thenReturn(Optional.of(user));

        ResponseEntity<UserResponse> response = userController.getUserById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(user.getName(), response.getBody().getName());
    }

    @Test
    public void shouldGetUserByIdNotFound() {
        when(userService.findById(1L)).thenReturn(Optional.empty());
        ResponseEntity<UserResponse> response = userController.getUserById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    public void shouldCreateUser() {
        User user = User.builder().id(1L).name("John Doe").email("john@example.com").build();
        when(userService.save(user)).thenReturn(user);

        ResponseEntity<User> response = userController.createUser(user);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(user, response.getBody());
    }

    @Test
    public void shouldUpdateUser() {
        User user = User.builder().id(1L).name("John Doe").email("john@example.com").build();
        when(userService.update(1L, user)).thenReturn(user);

        ResponseEntity<User> response = userController.updateUser(1L, user);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(user, response.getBody());
    }

    @Test
    public void shouldDeleteById() {
        doNothing().when(userService).deleteById(1L);

        ResponseEntity<HttpStatus> response = userController.deleteById(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    public void shouldDeleteAllUsers() {
        doNothing().when(userService).deleteAll();

        ResponseEntity<HttpStatus> response = userController.deleteAllUsers();

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }


    @Test
    public void shouldGetAllUsers() {
        when(userService.findAll()).thenReturn(List.of());

        ResponseEntity<List<UserResponse>> response = userController.getAllUsers(null);

        assertNotNull(response);
        assertEquals(0, response.getBody().size());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void shouldGetAllUsersWithName() {
        var user = User.builder().id(1L).name("John Doe").email("john@example.com").build();
        when(userService.findAll()).thenReturn(List.of(user));

        ResponseEntity<List<UserResponse>> response = userController.getAllUsers("John");

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
