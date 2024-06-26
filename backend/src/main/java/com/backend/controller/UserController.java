package com.backend.controller;

import com.backend.controller.response.UserResponse;
import com.backend.model.User;
import com.backend.service.impl.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Tag(name = "User Controller", description = "API for managing user")
public class UserController {

    private final UserService userService;

    private static final ObjectMapper objectMapper = new ObjectMapper();



    /**
     * Retrieves a list of all users or a filtered list based on the provided name.
     *
     * @param name Optional parameter to filter users by name.
     * @return ResponseEntity containing a list of User objects.
     */
    @Operation(summary = "Get all user", description = "Retrieve a list of all user or filter by name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = User.class)))
    })
    @GetMapping("/user")
    public ResponseEntity<List<UserResponse>> getAllUsers(@Parameter(description = "Name to filter user by") @RequestParam(required = false) String name) {
        objectMapper.registerModule(new Jdk8Module());
        if (name == null) {
            return ResponseEntity.ok().body(
                    userService.findAll()
                            .stream()
                            .map(user -> objectMapper.convertValue(user, UserResponse.class))
                            .collect(Collectors.toList()));
        }

        return ResponseEntity.ok().body(
                userService.findByNameContaining(name)
                        .stream()
                        .map(user -> objectMapper.convertValue(user, UserResponse.class))
                        .collect(Collectors.toList()));

    }

    /**
     * Retrieves a user by their Id.
     *
     * @param id The Id of the user to retrieve.
     * @return ResponseEntity containing the User object, or NOT_FOUND if user does not exist.
     */
    @Operation(summary = "Get user by Id", description = "Retrieve a user by their Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved user",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = User.class))),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @GetMapping("/user/{id}")
    public ResponseEntity<UserResponse> getUserById(@Parameter(description = "Id of the user to retrieve") @PathVariable("id") long id) {
        objectMapper.registerModule(new Jdk8Module());
        return ResponseEntity.ok().body(objectMapper.convertValue(userService.findById(id), UserResponse.class));

    }

    /**
     * Creates a new user.
     *
     * @param user The User object to create.
     * @return ResponseEntity containing the created User object.
     */
    @Operation(summary = "Create a new user", description = "Create a new user with the provided details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created user",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = User.class)))
    })
    @PostMapping("/user")
    public ResponseEntity<User> createUser(@Parameter(description = "User object to create") @RequestBody User user) {
        objectMapper.registerModule(new Jdk8Module());
        return new ResponseEntity<>(userService.save(user), HttpStatus.CREATED);
    }

    /**
     * Updates an existing user by their Id.
     *
     * @param id   The Id of the user to update.
     * @param user The User object with updated information.
     * @return ResponseEntity containing the updated User object.
     */
    @Operation(summary = "Update a user", description = "Update an existing user by their Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated user",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = User.class))),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @PutMapping("/user/{id}")
    public ResponseEntity<User> updateUser(@Parameter(description = "Id of the user to update") @PathVariable("id") long id,
                                           @Parameter(description = "Updated user object") @RequestBody User user) {
        objectMapper.registerModule(new Jdk8Module());
        return new ResponseEntity<>(userService.update(id, user), HttpStatus.OK);
    }

    /**
     * Deletes a user by their Id.
     *
     * @param id The Id of the user to delete.
     * @return ResponseEntity with NO_CONTENT status.
     */
    @Operation(summary = "Delete a user by Id", description = "Delete a user by their Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully deleted user"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @DeleteMapping("/user/{id}")
    public ResponseEntity<HttpStatus> deleteById(@Parameter(description = "Id of the user to delete") @PathVariable("id") long id) {
        objectMapper.registerModule(new Jdk8Module());
        userService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Deletes all users.
     *
     * @return ResponseEntity with NO_CONTENT status.
     */
    @Operation(summary = "Delete all users", description = "Delete all users in the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully deleted all users")
    })
    @DeleteMapping("/user")
    public ResponseEntity<HttpStatus> deleteAllUsers() {
        objectMapper.registerModule(new Jdk8Module());
        userService.deleteAll();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
