package com.challenge.api.service;

import com.challenge.api.model.DefaultEmployee;
import com.challenge.api.model.Employee;
import com.challenge.api.model.EmployeeRequest;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class InMemoryEmployeeService implements EmployeeService {

    private final List<Employee> employees = new ArrayList<>(List.of(createSeedEmployee()));

    @Override
    public List<Employee> getAllEmployees() {
        return List.copyOf(employees);
    }

    @Override
    public Employee getEmployeeByUuid(UUID uuid) {
        return employees.stream()
                .filter(employee -> uuid.equals(employee.getUuid()))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not found"));
    }

    @Override
    public Employee createEmployee(EmployeeRequest request) {
        Employee employee = DefaultEmployee.builder()
                .uuid(UUID.randomUUID())
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
        employees.add(employee);
        return employee;
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
}
