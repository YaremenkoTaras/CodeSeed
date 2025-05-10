package com.tyaremenko.userservice.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tyaremenko.userservice.dto.validators.CreateUserValidationGroup;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Value;
import lombok.With;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
@With
public class UserRequestDto {
    @NotBlank(message = "Name is required")
    @Size(max = 100, message = "Name cannot exceed 100 characters")
    String name;

    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    String email;

    @NotBlank(message = "Address is required")
    String address;

    @NotBlank(message = "Date of birth is required")
    String dateOfBirth;

    @NotBlank(groups = CreateUserValidationGroup.class, message = "Registered date is required")
    String registeredDate;
}
