package com.backend.test;

import com.backend.model.Department;
import com.backend.model.User;
import com.backend.repository.UserRepository;
import com.backend.service.impl.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void shouldFindAll() {
        List<User> users = new ArrayList<>();
        users.add(User.builder().id(1L).name("John Doe").email("john@example.com").build());

        when(userRepository.findAll()).thenReturn(users);

        List<User> result = userService.findAll();

        assertEquals(users, result);
    }

    @Test
    public void shouldFindByNameContaining() {
        List<User> users = new ArrayList<>();
        users.add(User.builder().id(1L).name("John Doe").email("john@example.com").build());

        when(userRepository.findByNameContaining("John")).thenReturn(users);

        List<User> result = userService.findByNameContaining("John");

        assertEquals(users, result);
    }

    @Test
    public void shouldFindById() {
        User user = User.builder().id(1L).name("John Doe").email("john@example.com").build();

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        Optional<User> result = userService.findById(1);

        assertTrue(result.isPresent());
        assertEquals(user, result.get());
    }

    @Test
    public void shouldSave() {
        User user = User.builder().id(1L).name("John Doe").email("john@example.com").build();

        when(userRepository.save(any(User.class))).thenReturn(user);

        User result = userService.save(user);

        assertEquals(user, result);
    }

    @Test
    public void shouldUpdate_UserExists() {
        User existingUser = User.builder().id(1L).name("John Doe").email("john@example.com").build();
        User updatedUser = User.builder().id(1L).name("Jane Doe").email("jane@example.com").build();

        when(userRepository.findById(1L)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(any(User.class))).thenReturn(updatedUser);

        User result = userService.update(1L, updatedUser);

        assertEquals(updatedUser, result);
    }

    @Test
    public void shouldUpdate_UserDoesNotExist() {
        User user = User.builder().id(1L).name("John Doe").email("john@example.com").build();

        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        User result = userService.update(1L, user);

        assertNull(result.getId());
    }

    @Test
    public void shouldDeleteById() {
        doNothing().when(userRepository).deleteById(1L);

        userService.deleteById(1L);

        verify(userRepository, times(1)).deleteById(1L);
    }

    @Test
    public void shouldDeleteAll() {
        doNothing().when(userRepository).deleteAll();

        userService.deleteAll();

        verify(userRepository, times(1)).deleteAll();
    }


    @Test
    public void shouldTestGetters() {
        Department department = Department.builder().id(1L).name("HR").build();
        User user = User.builder()
                .id(1L)
                .name("John")
                .surname("Doe")
                .email("john@example.com")
                .department(department)
                .build();
        assertEquals(1, user.getId());
        assertEquals("John", user.getName());
        assertEquals("Doe", user.getSurname());
        assertEquals("john@example.com", user.getEmail());
        assertEquals(department, user.getDepartment());
    }
}
