package com.backend.service.impl;

import com.backend.model.Department;
import com.backend.repository.DepartmentRepository;
import com.backend.service.DepartmentImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class DepartmentService implements DepartmentImpl {

    private final DepartmentRepository departmentRepository;

    public List<Department> findAll() {
        return departmentRepository.findAll();
    }
}
