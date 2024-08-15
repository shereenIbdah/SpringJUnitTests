package com.example.task1.service;

import com.example.task1.model.Employee;
import com.example.task1.repository.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {
    @Mock
    private EmployeeRepository employeeRepository;
    @InjectMocks
    private EmployeeService employeeService;


    @Test
    public void testAddEmployee() {
        Employee employee = new Employee(2L, "ahmad", 25, "male", 123456789, 5000.0, LocalDate.of(2021, 1, 1), "developer", null, null);
        // Act
        employeeService.addEmployee(employee);
        // Assert
        verify(employeeRepository, times(1)).save(employee);
    }

    @Test
    public void getAllEmployee() {
        //Arrange
        Employee employee = new Employee(2L, "ahmad", 25, "male", 123456789, 5000.0, LocalDate.of(2021, 1, 1), "developer", null, null);
        Employee employee1 = new Employee(3L, "shereen", 21, "female", 123456789, 5000.0, LocalDate.of(2021, 1, 1), "developer", null, null);
        List<Employee> employees = List.of(employee, employee1);
        //Mock
        when(employeeRepository.findAll()).thenReturn(employees);
        //Act
        List<Employee> result = employeeService.getEmployees();
        //Assert
        assertEquals(2, result.size());
        assertEquals(employee, result.get(0));
        assertEquals(employee1, result.get(1));

    }

    @Test
    public void testDeleteEmployee() {
        Long employeeId = 1L;
        when(employeeRepository.existsById(employeeId)).thenReturn(true);
        // Act
        employeeService.deleteEmployee(employeeId);
        // Assert
        verify(employeeRepository, times(1)).deleteById(employeeId);
    }

    @Test
    public void testUpdateEmployee() {
        //Arrange
        Employee existingEmployee = new Employee(1L, "ahmad", 25, "male", 123456789, 5000.0, LocalDate.of(2021, 1, 1), "developer", null, null);
        // Arrange
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(existingEmployee));
        // Act
        employeeService.updateEmployee(1L, "Jane Doe", "Female", 35, 987654321, 6000.0, null);
        // Assert
        verify(employeeRepository, times(1)).save(existingEmployee);
        assertEquals("Jane Doe", existingEmployee.getName());
        assertEquals("developer", existingEmployee.getRole());
    }

    @Test
    public void testUpdateEmployeeException() {
        Long employeeId = 1L;
        String role = "Admin";
        // Mock the repository behavior
        when(employeeRepository.findById(employeeId)).thenReturn(java.util.Optional.empty());
        // Act & Assert
        assertThrows(IllegalStateException.class, () -> employeeService.updateEmployee(employeeId, "shereen", null, null, null, null, role));
    }

    @Test
    public void testGetEmployeesWithPagination() {
        // Arrange
        Employee employee = new Employee(2L, "ahmad", 25, "male",
                123456789, 5000.0, LocalDate.of(2021, 1, 1),
                "developer", null, null);
        Employee employee1 = new Employee(3L, "shereen",
                21, "female", 123456789, 5000.0,
                LocalDate.of(2021, 1, 1), "developer",
                null, null);
        List<Employee> employees = List.of(employee, employee1);
        Page<Employee> pagedEmployees = new PageImpl<>(employees);
        when(employeeRepository.findAll(PageRequest.of(0, 2))).thenReturn(pagedEmployees);
        //Act
        List<Employee> result = employeeService.getEmployeesWithPagination(0, 2);
        //Assert
        assertEquals(2, result.size());
        assertEquals(employee, result.get(0));
        assertEquals(employee1, result.get(1));
    }

    @Test
    public void TestGetEmployeeSortedByName() {
        //Arrange
        Employee employee1 = new Employee(2L, "ahmad", 25, "male", 123456789, 5000.0, LocalDate.of(2021, 1, 1), "developer", null, null);
        Employee employee = new Employee(3L, "shereen", 21, "female", 123456789, 5000.0, LocalDate.of(2021, 1, 1), "developer", null, null);
        List<Employee> employees = List.of(employee, employee1);
        List<Employee> sortedEmployees = List.of(employee1, employee);
        when(employeeRepository.findAll(Sort.by(Sort.Direction.ASC, "name"))).thenReturn(sortedEmployees);
        //Act
        List<Employee> result = employeeService.getEmployeeSortedByName();
        //Assert
        assertEquals(sortedEmployees, result);
    }

    @Test
    public void TestGetEmployeesBasedOnGender() {
        Employee employee1 = new Employee(2L, "muna", 25, "female", 123456789, 5000.0, LocalDate.of(2021, 1, 1), "developer", null, null);
        Employee employee = new Employee(3L, "shereen", 21, "female", 123456789, 5000.0, LocalDate.of(2021, 1, 1), "developer", null, null);
        List<Employee> expectedResult = List.of(employee, employee1);
        when(employeeRepository.findEmployeeByGender("female")).thenReturn(expectedResult);
        //Act
        List<Employee> result = employeeService.getEmployessBasedOnGender("female");
        //Assert
        assertEquals(expectedResult, result);
        assertEquals(2, result.size());
    }

    @Test
    public void TestGetEmployeesByAgeRange() {
        Employee employee1 = new Employee(2L, "muna", 25, "female", 123456789, 5000.0, LocalDate.of(2021, 1, 1), "developer", null, null);
        Employee employee = new Employee(3L, "shereen", 21, "female", 123456789, 5000.0, LocalDate.of(2021, 1, 1), "developer", null, null);
        List<Employee> expectedResult = List.of(employee1);
        when(employeeRepository.findEmployeeByAgeRange(22, 30)).thenReturn(expectedResult);
        //Act
        List<Employee> result = employeeService.getEmployeesByAgeRange(22, 30);
        //Assert
        assertEquals(expectedResult, result);
    }

    @Test
    public void TestGetEmployeeNamesByDepartment() {
        String name = "muna";
        String name1 = "shereen";
        List<String> expectedResult = List.of(name, name1);
        when(employeeRepository.findEmployeeNamesByDepartment(1L)).thenReturn(expectedResult);
        //Act
        List<String> result = employeeService.getEmployeeNamesByDepartment(1L);
        //Assert
        assertEquals(expectedResult, result);
    }

}
