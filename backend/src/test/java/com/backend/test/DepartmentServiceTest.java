package com.backend.test;

import com.backend.model.Department;
import com.backend.repository.DepartmentRepository;
import com.backend.service.impl.DepartmentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class DepartmentServiceTest {

    @InjectMocks
    private DepartmentService departmentService;

    @Mock
    private DepartmentRepository departmentRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void shouldFindAll() {
        // Prepare mock data
        List<Department> departments = new ArrayList<>();
        departments.add(Department.builder().id(1L).name("Recursos humanos").build());
        departments.add(Department.builder().id(2L).name("Tecnologia da informação").build());

        // Mock the behavior of departmentRepository.findAll() to return the mock data
        when(departmentRepository.findAll()).thenReturn(departments);

        // Call the service method
        List<Department> result = departmentService.findAll();

        // Verify the results
        assertEquals(departments, result);
    }

    @Test
    public void shouldTestGetters() {
        Department department = Department.builder()
                .id(1L)
                .name("RH")
                .build();
        assertEquals(1, department.getId());
        assertEquals("RH", department.getName());
    }

}
