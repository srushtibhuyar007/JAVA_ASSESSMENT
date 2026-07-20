package com.challenge.api.controller;

import com.challenge.api.model.DefaultEmployee;
import com.challenge.api.model.Employee;
import com.challenge.api.model.EmployeeRequest;
import com.challenge.api.model.EmployeeResponse;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

/**
 * REST controller for employee operations.
 */
@RestController
@RequestMapping("/api/v1/employee")
@Slf4j
public class EmployeeController {

    private final List<Employee> employees = new ArrayList<>(List.of(createSeedEmployee()));

    /**
     * Retrieve all employees.
     */
    @GetMapping
    public List<EmployeeResponse> getAllEmployees() {
        log.info("Fetching all employees");
        return employees.stream().map(this::toResponse).toList();
    }

    /**
     * Retrieve a single employee by UUID.
     */
    @GetMapping("/{uuid}")
    public EmployeeResponse getEmployeeByUuid(@PathVariable UUID uuid) {
        log.info("Fetching employee with uuid={}", uuid);
        return employees.stream()
                .filter(employee -> uuid.equals(employee.getUuid()))
                .findFirst()
                .map(this::toResponse)
                .orElseThrow(() -> {
                    log.warn("Employee not found for uuid={}", uuid);
                    return new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not found");
                });
    }

    /**
     * Create a new employee.
     */
    @PostMapping
    public ResponseEntity<EmployeeResponse> createEmployee(@RequestBody EmployeeRequest requestBody) {
        log.info("Creating employee from request for email={}", requestBody.getEmail());
        Employee employee = toDomain(requestBody);
        employee.setUuid(UUID.randomUUID());
        employees.add(employee);
        log.info("Created employee with uuid={}", employee.getUuid());
        return ResponseEntity.status(HttpStatus.CREATED).body(toResponse(employee));
    }

    private static Employee createSeedEmployee() {
        return DefaultEmployee.builder()
                .uuid(UUID.fromString("00000000-0000-0000-0000-000000000001"))
                .firstName("Ada")
                .lastName("Lovelace")
                .fullName("Ada Lovelace")
                .salary(120000)
                .age(36)
                .jobTitle("Software Engineer")
                .email("ada.lovelace@example.com")
                .contractHireDate(Instant.parse("2020-01-15T00:00:00Z"))
                .contractTerminationDate(null)
                .build();
    }

    private EmployeeResponse toResponse(Employee employee) {
        return EmployeeResponse.builder()
                .uuid(employee.getUuid())
                .firstName(employee.getFirstName())
                .lastName(employee.getLastName())
                .fullName(employee.getFullName())
                .salary(employee.getSalary())
                .age(employee.getAge())
                .jobTitle(employee.getJobTitle())
                .email(employee.getEmail())
                .contractHireDate(employee.getContractHireDate())
                .contractTerminationDate(employee.getContractTerminationDate())
                .build();
    }

    private Employee toDomain(EmployeeRequest request) {
        return DefaultEmployee.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .fullName(request.getFullName())
                .salary(request.getSalary())
                .age(request.getAge())
                .jobTitle(request.getJobTitle())
                .email(request.getEmail())
                .contractHireDate(request.getContractHireDate())
                .contractTerminationDate(request.getContractTerminationDate())
                .build();
    }
}
