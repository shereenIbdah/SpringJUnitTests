package com.example.task1.service;

import com.example.task1.model.Employee;
import com.example.task1.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public void addEmployee(Employee employee) {
        employeeRepository.save(employee);

    }

    public List<Employee> getEmployees() {
        return employeeRepository.findAll();
    }

    public void deleteEmployee(Long employeeId) {
        boolean exists = employeeRepository.existsById(employeeId);
        if (!exists) {
            throw new IllegalStateException("employee with id " + employeeId + " does not exists");
        }
        employeeRepository.deleteById(employeeId);
    }

    //      (employeeId,name ,gender ,age,phoneNumber,baseSalary,role);
    public void updateEmployee(Long employeeId, String name, String gender, Integer age,
                               Integer phoneNumber, Double baseSalary, String role) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new IllegalStateException(
                        "employee with id " + employeeId + " does not exists"
                ));
        if (name != null) {
            employee.setName(name);

        }
        if (gender != null) {
            employee.setGender(gender);
        }
        if (age != null) {
            employee.setAge(age);
        }
        if (phoneNumber != null) {
            employee.setPhoneNumber(phoneNumber);
        }
        if (baseSalary != null) {
            employee.setBaseSalary(baseSalary);
        }

        if (role != null) {
            employee.setRole(role);
        }
        employeeRepository.save(employee);

    }

    public List<Employee> getEmployeesWithPagination(int page, int pageSize) {
        if (pageSize == -1) {
            return employeeRepository.findAll();
        } else {
            Page<Employee> employees = employeeRepository.findAll(PageRequest.of(page, pageSize));
            return employees.getContent();
        }
    }

    public List<Employee> getEmployeeSortedByName(String name) {
        List<Employee> employees = employeeRepository.findAll(Sort.by(Sort.Direction.ASC, "name"));
        return employees;
    }

    public List<Employee> getEmployessBasedOnGender(String gender) {
        List<Employee> employees = employeeRepository.findEmployeeByGender(gender);
        return employees;

    }

    public List<String> getEmployeeNamesByDepartment(Long department) {
        List<String> employees = employeeRepository.findEmployeeNamesByDepartment(department);
        return employees;
    }

    public List<Employee> getEmployeesByAgeRange(int minAge, int maxAge) {
        List<Employee> employees = employeeRepository.findEmployeeByAgeRange(minAge, maxAge);
        return employees;
    }

}
