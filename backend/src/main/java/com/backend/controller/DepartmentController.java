package com.backend.controller;

import com.backend.controller.response.DepartmentResponse;
import com.backend.model.Department;
import com.backend.service.DepartmentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
@Tag(name = "Department Controller", description = "API for managing departments")
public class DepartmentController {

	private final DepartmentService departmentService;

	private static final ObjectMapper objectMapper = new ObjectMapper();

	public DepartmentController(DepartmentService departmentService) {
		this.departmentService = departmentService;

	}

	/**
	 * Retrieves a list of all departments.
	 *
	 * @return ResponseEntity containing a list of Department objects.
	 */
	@Operation(summary = "Get all departments", description = "Retrieve a list of all departments")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Successfully retrieved list",
					content = @Content(
							mediaType = "application/json",
							schema = @Schema(implementation = Department.class)))
	})
	@GetMapping("/department")
	public ResponseEntity<List<DepartmentResponse>> getAll() {
		objectMapper.registerModule(new Jdk8Module());
		return ResponseEntity.ok().body(
				departmentService.findAll()
						.stream()
						.map(department -> objectMapper.convertValue(department, DepartmentResponse.class))
						.collect(Collectors.toList()));
	}
}
