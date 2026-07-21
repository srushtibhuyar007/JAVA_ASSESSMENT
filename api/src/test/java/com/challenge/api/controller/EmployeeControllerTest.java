package com.challenge.api.controller;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.challenge.api.model.EmployeeRequest;
import com.challenge.api.service.InMemoryEmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(EmployeeController.class)
@Import(InMemoryEmployeeService.class)
class EmployeeControllerTest {

        @Autowired
        private MockMvc mockMvc;

        @Autowired
        private ObjectMapper objectMapper;

        @Test
        void createEmployee_withInvalidRequest_returnsBadRequest() throws Exception {
                EmployeeRequest request = EmployeeRequest.builder()
                                .firstName("")
                                .lastName("Smith")
                                .fullName("Jane Smith")
                                .salary(120000)
                                .age(30)
                                .jobTitle("Engineer")
                                .email("not-an-email")
                                .build();

                mockMvc.perform(post("/api/v1/employee")
                                .with(httpBasic("admin", "admin123"))
                                .with(csrf())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request)))
                                .andExpect(status().isBadRequest())
                                .andExpect(jsonPath("$.message", containsString("Validation failed")));
        }

        @Test
        void createEmployee_withInvalidNameAgeEmailAndSalary_returnsBadRequest() throws Exception {
                EmployeeRequest request = EmployeeRequest.builder()
                                .firstName("Jane2")
                                .lastName("Smith!")
                                .fullName("Jane 123")
                                .salary(0)
                                .age(17)
                                .jobTitle("Engineer")
                                .email("Jane@Example.com")
                                .build();

                mockMvc.perform(post("/api/v1/employee")
                                .with(httpBasic("admin", "admin123"))
                                .with(csrf())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request)))
                                .andExpect(status().isBadRequest())
                                .andExpect(jsonPath("$.message", containsString("Validation failed")));
        }

        @Test
        void createEmployee_withDuplicateEmail_returnsConflict() throws Exception {
                EmployeeRequest request = EmployeeRequest.builder()
                                .firstName("Jane")
                                .lastName("Smith")
                                .fullName("Jane Smith")
                                .salary(120000)
                                .age(30)
                                .jobTitle("Engineer")
                                .email("jane@example.com")
                                .build();

                mockMvc.perform(post("/api/v1/employee")
                                .with(httpBasic("admin", "admin123"))
                                .with(csrf())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request)))
                                .andExpect(status().isCreated());

                mockMvc.perform(post("/api/v1/employee")
                                .with(httpBasic("admin", "admin123"))
                                .with(csrf())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request)))
                                .andExpect(status().isConflict())
                                .andExpect(jsonPath("$.message", containsString("already exists")));
        }
}
