package com.example.task1.controller;

import com.example.task1.model.Employee;
import com.example.task1.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/employee")
public class EmployeeController {
    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping(path = "/addEmployee")
    public void addEmployee(@RequestBody Employee employee) {
        employeeService.addEmployee(employee);
    }

    @GetMapping("/allEmployee")
    public List<Employee> getEmployees() {
        return employeeService.getEmployees();
    }

    @DeleteMapping(path = "/deleteEmployee/{employeeId}")
    public void deleteEmployee(@PathVariable("employeeId") Long employeeId) {
        employeeService.deleteEmployee(employeeId);
    }

    @PutMapping(path = "{employeeId}")
    public void updateEmployee(@PathVariable("employeeId") Long employeeId,
                               @RequestParam(required = false) String name,
                               @RequestParam(required = false) String gender,
                               @RequestParam(required = false) Integer age,
                               @RequestParam(required = false) Integer phoneNumber,
                               @RequestParam(required = false) Double baseSalary,
                               @RequestParam(required = false) String role) {
        employeeService.updateEmployee(employeeId, name, gender, age, phoneNumber, baseSalary, role);

    }

    //pagination By page number and size of the page
    @GetMapping("/employees/pagination")
    public List<Employee> getEmployeesWithPagination(@RequestParam(defaultValue = "0") int page,
                                                     @RequestParam(defaultValue = "-1") int pageSize) {
        return employeeService.getEmployeesWithPagination(page, pageSize);
    }

    @GetMapping("/sort/{sort}")
    public List<Employee> getStudentsWithSort(@PathVariable("sort") String name) {
        return employeeService.getEmployeeSortedByName();
    }

    //get employees by gender
    @GetMapping("/gender")
    public List<Employee> getEmployeesByGender(@RequestParam String gender) {
        return employeeService.getEmployessBasedOnGender(gender);
    }

    // get all names of employees who work in the specific department.
    @GetMapping("/departmentId")
    public List<String> getEmployeeNamesByDepartment(@RequestParam Long department) {
        return employeeService.getEmployeeNamesByDepartment(department);
    }

    //Get Employees by Age Range:
    @GetMapping("/age")
    public List<Employee> getEmployeesByAgeRange(@RequestParam int minAge, @RequestParam int maxAge) {
        return employeeService.getEmployeesByAgeRange(minAge, maxAge);
    }

}
