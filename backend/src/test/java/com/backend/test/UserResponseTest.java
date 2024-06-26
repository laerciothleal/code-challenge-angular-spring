package com.backend.test;

import com.backend.controller.response.UserResponse;
import com.backend.model.Department;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class UserResponseTest {

    @Test
    void shouldTestUserResponseAllArgsConstructor() {
        Department department = new Department(1L, "Financeiro");
        UserResponse response = new UserResponse(1L, "John", "Doe", "john.doe@example.com", department);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("John", response.getName());
        assertEquals("Doe", response.getSurname());
        assertEquals("john.doe@example.com", response.getEmail());
        assertEquals(department, response.getDepartment());
    }

    @Test
    void shouldTestUserResponseNoArgsConstructor() {
        UserResponse response = new UserResponse();
        response.setId(1L);
        response.setName("John");
        response.setSurname("Doe");
        response.setEmail("john.doe@example.com");
        response.setDepartment(new Department(1L, "Financeiro"));

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("John", response.getName());
        assertEquals("Doe", response.getSurname());
        assertEquals("john.doe@example.com", response.getEmail());
        assertNotNull(response.getDepartment());
        assertEquals(1L, response.getDepartment().getId());
        assertEquals("Financeiro", response.getDepartment().getName());
    }

    @Test
    void testUserResponseBuilder() {
        Department department = new Department(1L, "Financeiro");
        UserResponse response = UserResponse.builder()
                .id(1L)
                .name("John")
                .surname("Doe")
                .email("john.doe@example.com")
                .department(department)
                .build();

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("John", response.getName());
        assertEquals("Doe", response.getSurname());
        assertEquals("john.doe@example.com", response.getEmail());
        assertEquals(department, response.getDepartment());
    }
}
