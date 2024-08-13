package com.example.task1.controller;

import com.example.task1.model.Department;
import com.example.task1.model.Employee;
import com.example.task1.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/departement")
public class DepartementController {
    private final DepartmentService departementService;

    @Autowired
    public DepartementController(DepartmentService departementService) {
        this.departementService = departementService;
    }

    @PostMapping(path = "/addDepartement")
    public void addDepartement(@RequestBody Department departement) {
        departementService.addDepartment(departement);
    }

    @GetMapping("/allDepartement")
    public List<Department> getDepartements() {
        return departementService.getDepartments();
    }

    @DeleteMapping(path = "/deleteDepartement/{departementId}")
    public void deleteDepartement(@PathVariable("departementId") Long departementId) {
        departementService.deleteDepartment(departementId);
    }

    @PutMapping(path = "/updateDepartement/{departementId}")
    public void updateDepartement(@PathVariable("departementId") Long departementId,
                                  @RequestParam(required = false) String name) {
        departementService.updateDepartment(departementId, name);
    }

    //c- get all managers of departments
    @GetMapping("/managers")
    public List<Employee> getManagers() {
        return departementService.getManagers();
    }

    //Departments Managed by a Specific Employee
    @GetMapping("/{employeeId}")
    public List<Department> getDepartmentsByManager(@PathVariable("employeeId") Long employeeId) {
        return departementService.getDepartmentsByManager(employeeId);
    }

    //Get Total Number of Employees in Each Department:
    @GetMapping("/totalEmployees")
    public List<Object[]> getTotalEmployeesInEachDepartment() {
        return departementService.getTotalEmployeesInEachDepartment();
    }
}
