package com.example.task1.controller;

import com.example.task1.model.Department;
import com.example.task1.model.Employee;
import com.example.task1.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/department")
public class DepartmentController {
    private final DepartmentService departmentService;

    @Autowired
    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @PostMapping(path = "/addDepartment")
    public ResponseEntity<Department> addDepartment(@RequestBody Department departement) {
        Department departments = departmentService.addDepartment(departement);
        return new ResponseEntity<>(departement, HttpStatus.OK);

    }

    @GetMapping("/allDepartment")
    public ResponseEntity<List<Department>>  getDepartments() {
        List<Department> departments =  departmentService.getDepartments();
        return new ResponseEntity<>(departments, HttpStatus.OK);
    }

    @DeleteMapping(path = "/deleteDepartment/{departmentId}")
    public void deleteDepartment(@PathVariable("departmentId") Long departementId) {
        departmentService.deleteDepartment(departementId);
    }

    @PutMapping(path = "/updateDepartment/{departmentId}")
    public void updateDepartment(@PathVariable("departmentId") Long departmentId,
                                  @RequestParam(required = false) String name) {
        departmentService.updateDepartment(departmentId, name);
    }

    //c- get all managers of departments
    @GetMapping("/managers")
    public ResponseEntity<List<Employee>>  getManagers() {
        List<Employee> employees = departmentService.getManagers();
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    //Departments Managed by a Specific Employee
    @GetMapping("/{employeeId}")
    public  ResponseEntity<List<Department>> getDepartmentsByManager(@PathVariable("employeeId") Long employeeId) {
        List<Department> departments =  departmentService.getDepartmentsByManager(employeeId);
        return new ResponseEntity<>(departments, HttpStatus.OK);
    }

    //Get Total Number of Employees in Each Department:
    @GetMapping("/totalEmployees")
    public List<Object[]> getTotalEmployeesInEachDepartment() {
        return departmentService.getTotalEmployeesInEachDepartment();
    }
}
