package com.backend.test;

import com.backend.controller.response.ApiErrorResponse;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ApiErrorResponseTest {

    @Test
    void shouldTestApiErrorResponseBuilder() {
        ApiErrorResponse response = ApiErrorResponse.builder()
                .status(400)
                .code("BAD_REQUEST")
                .message("Invalid input")
                .validationErrors(List.of("Field1 is required", "Field2 must be a number"))
                .stackTrace(new String[]{"com.backend.controller.SomeClass.method(SomeClass.java:10)"})
                .build();

        assertNotNull(response);
        assertEquals(400, response.getStatus());
        assertEquals("BAD_REQUEST", response.getCode());
        assertEquals("Invalid input", response.getMessage());
        assertNotNull(response.getValidationErrors());
        assertEquals(2, response.getValidationErrors().size());
        assertNotNull(response.getStackTrace());
        assertEquals(1, response.getStackTrace().length);
    }
}
