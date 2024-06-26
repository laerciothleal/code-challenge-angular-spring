package com.backend.test;

import com.backend.config.GlobalExceptionHandler;
import com.backend.controller.response.ApiErrorResponse;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.server.ServerWebInputException;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.nio.file.AccessDeniedException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GlobalExceptionHandlerTest {

    @InjectMocks
    private GlobalExceptionHandler globalExceptionHandler;

    @Mock
    private ServerWebInputException serverWebInputException;

    @Mock
    private WebExchangeBindException webExchangeBindException;

    @Mock
    private TypeMismatchException typeMismatchException;

    @Mock
    private NoHandlerFoundException noHandlerFoundException;

    @Test
    void shouldHandleServerWebInputException() {
        when(serverWebInputException.getCause()).thenReturn(typeMismatchException);
        when(typeMismatchException.getPropertyName()).thenReturn("field");
        when(typeMismatchException.getValue()).thenReturn("value");

        ApiErrorResponse response = globalExceptionHandler.handleServerWebInputException(serverWebInputException);

        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
        assertEquals("Validation error", response.getMessage());
        assertEquals(1, response.getValidationErrors().size());
    }

    @Test
    void shouldHandleWebExchangeBindException() {
        ApiErrorResponse response = globalExceptionHandler.handleWebExchangeBindException(webExchangeBindException);

        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
        assertEquals("Validation error", response.getMessage());
        assertEquals(0, response.getValidationErrors().size()); // Because we don't mock field errors
    }

    @Test
    void shouldHandleHttpRequestMethodNotSupportedException() {
        ApiErrorResponse response = globalExceptionHandler.handleHttpRequestMethodNotSupportedException(new HttpRequestMethodNotSupportedException("GET"));

        assertEquals(HttpStatus.METHOD_NOT_ALLOWED.value(), response.getStatus());
    }

    @Test
    void shouldHandleHttpMediaTypeNotSupportedException() {
        ApiErrorResponse response = globalExceptionHandler.handleHttpMediaTypeNotSupportedException(new HttpMediaTypeNotSupportedException("application/json"));

        assertEquals(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value(), response.getStatus());
    }

    @Test
    void shouldHandleHttpMediaTypeNotAcceptableException() {
        ApiErrorResponse response = globalExceptionHandler.handleHttpMediaTypeNotAcceptableException(new HttpMediaTypeNotAcceptableException("application/json"));

        assertEquals(HttpStatus.NOT_ACCEPTABLE.value(), response.getStatus());
    }

    @Test
    void shouldHandleMissingServletRequestParameterException() {
        ApiErrorResponse response = globalExceptionHandler.handleMissingServletRequestParameterException(new MissingServletRequestParameterException("param", "String"));

        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
        assertEquals("Required request parameter 'param' for method parameter type String is not present", response.getMessage());
    }

    @Test
    void shouldHandleAccessDeniedException() {
        ApiErrorResponse response = globalExceptionHandler.handleAccessDeniedException(new AccessDeniedException("Access denied"));

        assertEquals(HttpStatus.FORBIDDEN.value(), response.getStatus());
        assertEquals("Access denied", response.getMessage());
    }

    @Test
    void shouldHandleNoHandlerFoundException() {
        ApiErrorResponse response = globalExceptionHandler.handleNoHandlerFoundException(noHandlerFoundException);

        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
    }

    @Test
    void shouldHandleEntityNotFoundException() {
        ApiErrorResponse response = globalExceptionHandler.handleEntityNotFoundException(new EntityNotFoundException("Entity not found"));

        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
    }

    @Test
    void shouldHandleGenericRepositoryException() {
        ApiErrorResponse response = globalExceptionHandler.handleGenericRepositoryException(new RuntimeException("Internal server error"));

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getStatus());
    }
}
