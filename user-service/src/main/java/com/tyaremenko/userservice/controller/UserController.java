package com.tyaremenko.userservice.controller;

import com.tyaremenko.userservice.dto.UserRequestDto;
import com.tyaremenko.userservice.dto.UserResponseDto;
import com.tyaremenko.userservice.dto.validators.CreateUserValidationGroup;
import com.tyaremenko.userservice.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.groups.Default;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
@Tag(name = "User", description = "API for managing users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    @Operation(summary = "Get users")
    public ResponseEntity<List<UserResponseDto>> getPatients() {
        List<UserResponseDto> patients = userService.getPatients();
        return ResponseEntity.ok().body(patients);
    }

    @PostMapping
    @Operation(summary = "Create a new user")
    public ResponseEntity<UserResponseDto> createPatient(
            @Validated({Default.class, CreateUserValidationGroup.class})
            @RequestBody UserRequestDto UserRequestDto) {

        UserResponseDto UserResponseDto = userService.createPatient(
                UserRequestDto);

        return ResponseEntity.ok().body(UserResponseDto);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update user")
    public ResponseEntity<UserResponseDto> updatePatient(@PathVariable UUID id,
                                                         @Validated({Default.class}) @RequestBody UserRequestDto UserRequestDto) {

        UserResponseDto UserResponseDto = userService.updatePatient(id,
                                                                    UserRequestDto);

        return ResponseEntity.ok().body(UserResponseDto);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a user")
    public ResponseEntity<Void> deletePatient(@PathVariable UUID id) {
        userService.deletePatient(id);
        return ResponseEntity.noContent().build();
    }
}
