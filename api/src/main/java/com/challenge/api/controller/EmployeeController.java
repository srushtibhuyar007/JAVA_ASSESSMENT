package com.challenge.api.controller;

import com.challenge.api.model.DefaultEmployee;
import com.challenge.api.model.Employee;
import com.challenge.api.model.EmployeeRequest;
import com.challenge.api.model.EmployeeResponse;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
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
 * Fill in the missing aspects of this Spring Web REST Controller. Don't forget to add a Service layer.
 */
@RestController
@RequestMapping("/api/v1/employee")
public class EmployeeController {

    private final List<Employee> employees = new ArrayList<>(List.of(createSeedEmployee()));

    /**
     * @implNote Need not be concerned with an actual persistence layer. Generate mock Employee models as necessary.
     * @return One or more Employees.
     */
    @GetMapping
    public List<EmployeeResponse> getAllEmployees() {
        return employees.stream().map(this::toResponse).toList();
    }

    /**
     * @implNote Need not be concerned with an actual persistence layer. Generate mock Employee model as necessary.
     * @param uuid Employee UUID
     * @return Requested Employee if exists
     */
    @GetMapping("/{uuid}")
    public EmployeeResponse getEmployeeByUuid(@PathVariable UUID uuid) {
        return employees.stream()
                .filter(employee -> uuid.equals(employee.getUuid()))
                .findFirst()
                .map(this::toResponse)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not found"));
    }

    /**
     * @implNote Need not be concerned with an actual persistence layer.
     * @param requestBody hint!
     * @return Newly created Employee
     */
    @PostMapping
    public ResponseEntity<EmployeeResponse> createEmployee(@RequestBody EmployeeRequest requestBody) {
        Employee employee = toDomain(requestBody);
        employee.setUuid(UUID.randomUUID());
        employees.add(employee);
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
