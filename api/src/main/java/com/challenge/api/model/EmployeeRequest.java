package com.challenge.api.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeRequest {

    @NotBlank(message = "firstName is required")
    private String firstName;

    @NotBlank(message = "lastName is required")
    private String lastName;

    @NotBlank(message = "fullName is required")
    private String fullName;

    @NotNull(message = "salary is required") @Min(value = 0, message = "salary must be non-negative")
    private Integer salary;

    @NotNull(message = "age is required") @Min(value = 0, message = "age must be non-negative")
    private Integer age;

    @NotBlank(message = "jobTitle is required")
    private String jobTitle;

    @NotBlank(message = "email is required")
    @Email(message = "email must be a valid email address")
    private String email;

    private Instant contractHireDate;
    private Instant contractTerminationDate;
}
