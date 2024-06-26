package com.backend.test;

import com.backend.controller.DepartmentController;
import com.backend.controller.response.DepartmentResponse;
import com.backend.model.Department;
import com.backend.service.impl.DepartmentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class DepartmentControllerTest {

    @InjectMocks
    private DepartmentController departmentController;

    @Mock
    private DepartmentService departmentService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void shouldGetAllDepartments() {
        // Prepare mock data
        List<Department> departments = new ArrayList<>();
        departments.add(Department.builder().id(1L).name("Tecnologia da informação").build());
        departments.add(Department.builder().id(2L).name("Recursos Humanos").build());

        // Mock the behavior of departmentService.findAll() to return the mock data
        when(departmentService.findAll()).thenReturn(departments);

        // Call the controller method
        ResponseEntity<List<DepartmentResponse>> responseEntity = departmentController.getAll();

        // Verify the results
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(departments.size() == 2, responseEntity.getBody().size() == 2);
    }
}
