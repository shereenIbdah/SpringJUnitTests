package com.example.task1.repository;

import com.example.task1.model.Department;
import com.example.task1.model.Employee;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest  // Spring Boot will auto-configure an H2 database for testing
@ActiveProfiles("test")  // Activate the 'test' profile

public class EmployeeRepositoryTest {

    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private DepartmentRepository departmentRepository;

    @Test
    public void testFindAll() {
        Employee employee = new Employee(1L, "Alice", 25, "female", 123456789, 5000.0, LocalDate.of(2021, 1, 1), "developer", null, null);
        employeeRepository.save(employee);

        List<Employee> employees = employeeRepository.findAll();

        assertThat(employees).isNotNull();
        assertThat(employees.size()).isEqualTo(1);
    }

    @Test
    public void testFindEmployeeByGender() {
        Employee employee = new Employee(2L, "ahmad", 25, "male", 123456789, 5000.0, LocalDate.of(2021, 1, 1), "developer", null, null);
        Employee employee1 = new Employee(3L, "shereen", 21, "female", 123456789, 5000.0, LocalDate.of(2021, 1, 1), "developer", null, null);
        List<Employee> employees = List.of(employee, employee1);
        employeeRepository.saveAll(employees);
        List<Employee> result = employeeRepository.findEmployeeByGender("male");
        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(1);

    }

    @Test
    public void testFindEmployeeNamesByDepartment() {
        Department department1 = new Department(1L, "IT",
                null, null);
        Department dep = departmentRepository.save(department1);

        Employee employee = new Employee(2L, "ahmad", 25, "male",
                123456789, 5000.0,
                LocalDate.of(2021, 1, 1), "developer",
                null, List.of(dep));
        Employee emp = employeeRepository.save(employee);
        dep.setEmployees(List.of(emp));
        List<String> result = employeeRepository.findEmployeeNamesByDepartment(dep.getId());
        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(1);
        assertThat(result).containsExactly("ahmad");
    }

    @Test
    public void testFindEmployeeByAgeRange() {
        Employee employee = new Employee(2L, "ahmad", 25, "male", 123456789, 5000.0, LocalDate.of(2021, 1, 1), "developer", null, null);
        Employee employee1 = new Employee(3L, "shereen", 21, "female", 123456789, 5000.0, LocalDate.of(2021, 1, 1), "developer", null, null);
        List<Employee> employees = List.of(employee, employee1);
        employeeRepository.saveAll(employees);
        List<Employee> result = employeeRepository.findEmployeeByAgeRange(20, 25);
        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(2);
    }

    // Other tests...
}
