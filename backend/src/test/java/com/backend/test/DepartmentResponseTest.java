package com.backend.test;

import com.backend.controller.response.DepartmentResponse;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class DepartmentResponseTest {

    @Test
    void shouldTestDepartmentResponseAllArgsConstructor() {
        DepartmentResponse response = new DepartmentResponse(1L, "Financeiro");

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("Financeiro", response.getName());
    }

    @Test
    void shouldTestDepartmentResponseNoArgsConstructor() {
        DepartmentResponse response = new DepartmentResponse();
        response.setId(1L);
        response.setName("Financeiro");

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("Financeiro", response.getName());
    }

    @Test
    void shouldTestDepartmentResponseBuilder() {
        DepartmentResponse response = DepartmentResponse.builder()
                .id(1L)
                .name("Financeiro")
                .build();

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("Financeiro", response.getName());
    }
}
