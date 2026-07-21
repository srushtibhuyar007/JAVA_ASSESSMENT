package com.challenge.api.service;

import com.challenge.api.model.DefaultEmployee;
import com.challenge.api.model.Employee;
import com.challenge.api.model.EmployeeRequest;
import com.challenge.api.util.UuidGenerator;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class InMemoryEmployeeService implements EmployeeService {

    private final Map<UUID, Employee> employeesByUuid = new ConcurrentHashMap<>();

    public InMemoryEmployeeService() {
        Employee seedEmployee = createSeedEmployee();
        employeesByUuid.put(seedEmployee.getUuid(), seedEmployee);
    }

    @Override
    public List<Employee> getAllEmployees() {
        return List.copyOf(employeesByUuid.values());
    }

    @Override
    public Employee getEmployeeByUuid(UUID uuid) {
        Employee employee = employeesByUuid.get(uuid);
        if (employee == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not found");
        }
        return employee;
    }

    @Override
    public Employee createEmployee(EmployeeRequest request) {
        String email = request.getEmail();
        if (email != null
                && employeesByUuid.values().stream().anyMatch(employee -> email.equals(employee.getEmail()))) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Employee with this email already exists");
        }

        Employee employee = DefaultEmployee.builder()
                .uuid(UuidGenerator.generate())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .fullName(request.getFullName())
                .salary(request.getSalary())
                .age(request.getAge())
                .jobTitle(request.getJobTitle())
                .email(email)
                .contractHireDate(request.getContractHireDate())
                .contractTerminationDate(request.getContractTerminationDate())
                .build();

        employeesByUuid.put(employee.getUuid(), employee);
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
