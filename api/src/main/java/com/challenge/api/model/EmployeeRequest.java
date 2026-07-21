package com.challenge.api.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
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
    @Pattern(regexp = "^[a-zA-Z]+$", message = "firstName must contain only letters")
    private String firstName;

    @NotBlank(message = "lastName is required")
    @Pattern(regexp = "^[a-zA-Z]+$", message = "lastName must contain only letters")
    private String lastName;

    @NotBlank(message = "fullName is required")
    @Pattern(regexp = "^[a-zA-Z]+(?: [a-zA-Z]+)+$", message = "fullName must contain only letters and spaces")
    private String fullName;

    @NotNull(message = "salary is required")
    @Min(value = 1, message = "salary must be greater than zero")
    private Integer salary;

    @NotNull(message = "age is required")
    @Min(value = 18, message = "age must be at least 18")
    @Max(value = 100, message = "age must be at most 100")
    private Integer age;

    @NotBlank(message = "jobTitle is required")
    private String jobTitle;

    @NotBlank(message = "email is required")
    @Email(message = "email must be a valid email address")
    @Pattern(regexp = "^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,}$", message = "email must be lowercase")
    private String email;

    private Instant contractHireDate;
    private Instant contractTerminationDate;
}
