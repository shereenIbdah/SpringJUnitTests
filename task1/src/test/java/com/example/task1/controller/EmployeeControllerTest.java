package com.example.task1.controller;

import static com.jayway.jsonpath.internal.path.PathCompiler.fail;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.task1.model.Employee;
import com.example.task1.service.EmployeeService;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDate;
import java.util.List;

@RunWith(SpringRunner.class)
@WebMvcTest(value = EmployeeController.class)
public class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService employeeService;

    @Test
    public void testAddEmployee() throws Exception {
        Employee employee = new Employee(2L, "ahmad", 25, "male", 123456789, 5000.0, LocalDate.of(2021, 1, 1), "developer", null, null);
        String inputInJson = this.mapToJson(employee);
        String URI = "/api/v1/employee/addEmployee"; // Add leading slash
        when(employeeService.addEmployee(Mockito.any(Employee.class))).thenReturn(employee);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post(URI).accept(MediaType.APPLICATION_JSON).content(inputInJson).contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();

        String outputInJson = response.getContentAsString();

        assertThat(outputInJson).isEqualTo(inputInJson);
        assertEquals(HttpStatus.OK.value(), response.getStatus());

    }

    @Test
    public void testGetEmployees() {
        // Prepare the data
        Employee employee = new Employee(2L, "ahmad", 25, "male", 123456789, 5000.0, LocalDate.of(2021, 1, 1), "developer", null, null);
        Employee employee1 = new Employee(3L, "shereen", 21, "female", 123456789, 5000.0, LocalDate.of(2021, 1, 1), "developer", null, null);
        List<Employee> employeeslist = List.of(employee, employee1);
        // Convert the list of employees to JSON
        String expectedJson;
        try {
            expectedJson = this.mapToJson(employeeslist);
        } catch (Exception e) {
            expectedJson = "{}"; // Default to empty JSON if there's an error
        }
        // Mock the service to return the list of employees
        when(employeeService.getEmployees()).thenReturn(employeeslist);

        // Define the URI of the endpoint
        String URI = "/api/v1/employee/allEmployee";

        // Build the GET request
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(URI).accept(MediaType.APPLICATION_JSON);
        try {
            // Perform the request
            MvcResult result = mockMvc.perform(requestBuilder).andReturn();
            MockHttpServletResponse response = result.getResponse();

            // Get the response content as JSON
            String outputInJson = response.getContentAsString();

            // Log the expected and actual outputs
            System.out.println("Expected JSON: " + expectedJson);
            System.out.println("Actual Output JSON: " + outputInJson);

            // Assert that the response matches the expected output
            assertThat(outputInJson).isEqualTo(expectedJson);
            assertEquals(HttpStatus.OK.value(), response.getStatus());
        } catch (Exception e) {
            fail("Test failed due to an exception: " + e.getMessage());
        }
    }

    @Test
    public void testDeleteEmployee() throws Exception {
        Long employeeId = 1L;
        // When: Mock the deleteAddress method to do nothing
        Mockito.doNothing().when(employeeService).deleteEmployee(employeeId);
        String URI = "/api/v1/employee/deleteEmployee/" + employeeId;
        // Perform the DELETE request
        RequestBuilder requestBuilder = MockMvcRequestBuilders.delete(URI).accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();

        // Then: Verify the response status is 200 OK (or 204 No Content)
        assertEquals(HttpStatus.OK.value(), response.getStatus());

        // Optionally, verify that the service method was called with the correct ID
        verify(employeeService, Mockito.times(1)).deleteEmployee(employeeId);

    }

    @Test
    public void testUpdateEmployee() throws Exception {
        // Prepare the update data
        Long employeeId = 1L;
        String name = "John Doe";
        String gender = "male";
        Integer age = 30;
        Integer phoneNumber = 123456789;
        Double baseSalary = 6000.0;
        String role = "manager";
        // When: Mock the updateAddress method to do nothing
        Mockito.doNothing().when(employeeService).updateEmployee(employeeId, name, gender, age, phoneNumber, baseSalary, role);

        // Perform the PUT request
        RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/api/v1/employee/{employeeId}", employeeId).param("name", name).param("gender", gender).param("age", String.valueOf(age)).param("phoneNumber", String.valueOf(phoneNumber)).param("baseSalary", String.valueOf(baseSalary)).param("role", role).accept(MediaType.APPLICATION_JSON);

        // Execute the request and verify the response
        mockMvc.perform(requestBuilder).andExpect(status().isOk());
        // Verify that the service method was called with the correct parameters
        verify(employeeService).updateEmployee(employeeId, name, gender, age, phoneNumber, baseSalary, role);
    }

    @Test
    public void testGetEmployeesWithPagination() throws Exception {
        Employee employee = new Employee(2L, "ahmad", 25, "male", 123456789, 5000.0, LocalDate.of(2021, 1, 1), "developer", null, null);
        Employee employee1 = new Employee(3L, "shereen", 21, "female", 123456789, 5000.0, LocalDate.of(2021, 1, 1), "developer", null, null);
        List<Employee> employeeslist = List.of(employee, employee1);
        // Convert the list of employees to JSON
        String expectedJson;
        try {
            expectedJson = this.mapToJson(employeeslist);
        } catch (Exception e) {
            expectedJson = "{}"; // Default to empty JSON if there's an error
        }
        // Mock the service to return the list of employees
        when(employeeService.getEmployeesWithPagination(0, 2)).thenReturn(employeeslist);

        // Define the URI of the endpoint
        String URI = "/api/v1/employee/employees/pagination";

        // Build the GET request with query parameters
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(URI).param("page", "0").param("pageSize", "2").accept(MediaType.APPLICATION_JSON); //expects the response to be in JSON format.
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();

        // Get the response content as JSON
        String outputInJson = response.getContentAsString();

        // Log the expected and actual outputs
        System.out.println("Expected JSON: " + expectedJson);
        System.out.println("Actual Output JSON: " + outputInJson);

        // Assert that the response matches the expected output
        assertThat(outputInJson).isEqualTo(expectedJson);
        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    public void testGetEmployeesWithSort() throws Exception {
        Employee employee = new Employee(2L, "ahmad", 25, "male", 123456789, 5000.0, LocalDate.of(2021, 1, 1), "developer", null, null);
        Employee employee1 = new Employee(3L, "shereen", 21, "female", 123456789, 5000.0, LocalDate.of(2021, 1, 1), "developer", null, null);
        List<Employee> employeeslist = List.of(employee, employee1);
        // Convert the list of employees to JSON
        String expectedJson;
        try {
            expectedJson = this.mapToJson(employeeslist);
        } catch (Exception e) {
            expectedJson = "{}"; // Default to empty JSON if there's an error
        }
        // Mock the service to return the list of employees
        when(employeeService.getEmployeeSortedByName()).thenReturn(employeeslist);
        String URI = "/api/v1/employee/sort";
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(URI).accept(MediaType.APPLICATION_JSON); //expects the response to be in JSON format.

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        // Get the response content as JSON
        String outputInJson = response.getContentAsString();

        // Log the expected and actual outputs
        System.out.println("Expected JSON: " + expectedJson);
        System.out.println("Actual Output JSON: " + outputInJson);

        // Assert that the response matches the expected output
        assertThat(outputInJson).isEqualTo(expectedJson);
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        verify(employeeService).getEmployeeSortedByName();
    }

    @Test
    public void testGetEmployeesByGender() {
        Employee employee = new Employee(2L, "ahmad", 25, "male", 123456789, 5000.0, LocalDate.of(2021, 1, 1), "developer", null, null);
        List<Employee> employeeslist = List.of(employee);
        // Mock the service to return employees with the specified gender
        when(employeeService.getEmployessBasedOnGender("male")).thenReturn(employeeslist);
        String expectedJson;
        try {
            expectedJson = this.mapToJson(employeeslist);
        } catch (Exception e) {
            expectedJson = "{}"; // Default to empty JSON if there's an error
        }
        String URI = "/api/v1/employee/gender";
        // Build the GET request with the gender query parameter
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(URI).param("gender", "male").accept(MediaType.APPLICATION_JSON);
        try {
            // Perform the request
            MvcResult result = mockMvc.perform(requestBuilder).andReturn();
            MockHttpServletResponse response = result.getResponse();

            // Get the response content as JSON
            String outputInJson = response.getContentAsString();

            // Log the expected and actual outputs
            System.out.println("Expected JSON: " + expectedJson);
            System.out.println("Actual Output JSON: " + outputInJson);

            // Assert that the response matches the expected output
            assertThat(outputInJson).isEqualTo(expectedJson);
            assertEquals(HttpStatus.OK.value(), response.getStatus());
        } catch (Exception e) {
            fail("Test failed due to an exception: " + e.getMessage());
        }
    }

    @Test
    public void testGetEmployeeNamesByDepartment() throws Exception {
        List<String> employeeNames = List.of("John Doe", "Jane Doe");
        // Mock the service to return the list of employee names for a specific department
        when(employeeService.getEmployeeNamesByDepartment(1L)).thenReturn(employeeNames);
        // Define the URI of the endpoint
        String URI = "/api/v1/employee/departmentId";

        // Convert the list of employee names to JSON
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String expectedJson = objectMapper.writeValueAsString(employeeNames);

        // Build the GET request with the department query parameter
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(URI).param("department", "1") // Pass department ID as a string
                .accept(MediaType.APPLICATION_JSON);

        // Perform the request
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();

        // Get the response content as JSON
        String outputInJson = response.getContentAsString();

        // Log the expected and actual outputs
        System.out.println("Expected JSON: " + expectedJson);
        System.out.println("Actual Output JSON: " + outputInJson);

        // Assert that the response matches the expected output
        assertThat(outputInJson).isEqualTo(expectedJson);
        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    public void testGetEmployeesByAgeRange() {
        Employee employee = new Employee(2L, "ahmad", 25, "male", 123456789, 5000.0, LocalDate.of(2021, 1, 1), "developer", null, null);
        Employee employee1 = new Employee(3L, "shereen", 21, "female", 123456789, 5000.0, LocalDate.of(2021, 1, 1), "developer", null, null);
        List<Employee> employeeslist = List.of(employee, employee1);
        // Convert the list of employees to JSON
        String expectedJson;
        try {
            expectedJson = this.mapToJson(employeeslist);
        } catch (Exception e) {
            expectedJson = "{}"; // Default to empty JSON if there's an error
        }
        // Mock the service to return the list of employees
        when(employeeService.getEmployeesByAgeRange(20, 30)).thenReturn(employeeslist);
        String URI = "/api/v1/employee/age";
        // Build the GET request with the age range query parameters
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(URI).param("minAge", "20").param("maxAge", "30").accept(MediaType.APPLICATION_JSON);
        try {
            // Perform the request
            MvcResult result = mockMvc.perform(requestBuilder).andReturn();
            MockHttpServletResponse response = result.getResponse();

            // Get the response content as JSON
            String outputInJson = response.getContentAsString();

            // Log the expected and actual outputs
            System.out.println("Expected JSON: " + expectedJson);
            System.out.println("Actual Output JSON: " + outputInJson);

            // Assert that the response matches the expected output
            assertThat(outputInJson).isEqualTo(expectedJson);
            assertEquals(HttpStatus.OK.value(), response.getStatus());
        } catch (Exception e) {
            fail("Test failed due to an exception: " + e.getMessage());
        }

    }


    /**
     * Maps an Object into a JSON String. Uses a Jackson ObjectMapper.
     */
    public String mapToJson(Object object) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "{}"; // Return an empty JSON object or handle as needed
        }
    }

}

