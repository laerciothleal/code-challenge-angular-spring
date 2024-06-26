package com.backend.controller.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserRequest {
    private Long id;
    private String name;
    private String surname;
    private String email;
    private DepartmentRequest department;
}
