package com.example.task1.controller;

import com.example.task1.model.Employee;
import com.example.task1.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Employee> addEmployee(@RequestBody Employee employee) {
        Employee employees= employeeService.addEmployee(employee);
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    @GetMapping("/allEmployee")
    public ResponseEntity<List<Employee>> getEmployees() {
        List<Employee> employees = employeeService.getEmployees();
        return new ResponseEntity<>(employees, HttpStatus.OK);
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
    public ResponseEntity<List<Employee>>  getEmployeesWithPagination(@RequestParam(defaultValue = "0") int page,
                                                     @RequestParam(defaultValue = "-1") int pageSize) {
          List<Employee> employees = employeeService.getEmployeesWithPagination(page, pageSize);
            return new ResponseEntity<>(employees, HttpStatus.OK);
    }


    @GetMapping("/sort")
    public ResponseEntity<List<Employee>>  getEmployeesWithSort() {
        List<Employee> employees = employeeService.getEmployeeSortedByName();
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    //get employees by gender
    @GetMapping("/gender")
    public ResponseEntity<List<Employee>> getEmployeesByGender(@RequestParam String gender) {
        List<Employee> employees =  employeeService.getEmployessBasedOnGender(gender);
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    // get all names of employees who work in the specific department.
    @GetMapping("/departmentId")
    public  ResponseEntity<List<String>> getEmployeeNamesByDepartment(@RequestParam Long department) {
        List<String>  names =  employeeService.getEmployeeNamesByDepartment(department);
        return  new ResponseEntity<>(names, HttpStatus.OK);
    }

    //Get Employees by Age Range:
    @GetMapping("/age")
    public  ResponseEntity<List<Employee>> getEmployeesByAgeRange(@RequestParam int minAge, @RequestParam int maxAge) {
        List<Employee> employees =  employeeService.getEmployeesByAgeRange(minAge, maxAge);
        return  new ResponseEntity<>(employees, HttpStatus.OK);

    }

}
