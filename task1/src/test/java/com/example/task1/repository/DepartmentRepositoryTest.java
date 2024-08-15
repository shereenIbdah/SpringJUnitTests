package com.example.task1.repository;

import com.example.task1.model.Department;
import com.example.task1.model.Employee;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest  // Spring Boot will auto-configure an H2 database for testing
@ActiveProfiles("test")  // Activate the 'test' profile

public class DepartmentRepositoryTest {
    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Test
    public void testFindAll() {
        Department department = new Department(1L, "IT", null, null);
        departmentRepository.save(department);
        List<Department> departments = departmentRepository.findAll();
        assertThat(departments).isNotNull();
        assertThat(departments.size()).isEqualTo(1);
    }
    @Test
    public void testFindManagers() {
        Employee employee = new Employee( "Alice", 25,  123456789,"female", 5000.0, LocalDate.of(2021, 1, 1), "developer", null, null);
        Employee emp = employeeRepository.save(employee);
        Department department = new Department(1L, "IT", null, employee);
        Department dep = departmentRepository.save(department);
        List<Employee> managers = departmentRepository.findManagers();
        System.out.println(managers);
        assertThat(managers).isNotNull();
        assertThat(managers.size()).isEqualTo(1);
    }
    @Test
    public void testFindDepartmentsByManager() {
        Employee employee = new Employee( "ahmad", 25, 123456789, "male",5000.0, LocalDate.of(2021, 1, 1), "developer", null, null);
        Employee emp1=employeeRepository.save(employee);
        Department department = new Department(2L, "IT", null, emp1);
        departmentRepository.save(department);
        List<Department> departments = departmentRepository.findDepartmentsByManager(emp1.getId());
        System.out.println(departments);
        assertThat(departments).isNotNull();
        assertThat(departments.size()).isEqualTo(1);
    }
    @Test
    public void testGetTotalEmployeesInEachDepartment() {
        Department department1 = new Department( "IT",
                null, null);
        Department dep =  departmentRepository.save(department1);
        Employee employee = new Employee( "shereen", 25,
                123456789,"female", 5000.0,
                LocalDate.of(2021, 1, 1), "developer",
                null, List.of(dep));
        Employee employee2 = new Employee( "ahmad", 25,
                123456789, "male",5000.0,
                LocalDate.of(2021, 1, 1), "developer",
                null, List.of(dep));
        Employee emp= employeeRepository.save(employee);
        Employee emp2= employeeRepository.save(employee2);
        dep.setEmployees(List.of(emp,emp2));
        List<Object[]> result = departmentRepository.getTotalEmployeesInEachDepartment();
        // take the second element in the list
        System.out.println(result.toString());
        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(1);
    }
}
