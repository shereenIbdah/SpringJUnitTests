package com.example.task1.service;

import com.example.task1.model.Department;
import com.example.task1.model.Employee;
import com.example.task1.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentService {
    private final DepartmentRepository departmentRepository;

    @Autowired
    public DepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    public Department addDepartment(Department department) {
        departmentRepository.save(department);
        return department;
    }

    public List<Department> getDepartments() {
        return departmentRepository.findAll();
    }

    public void deleteDepartment(Long departmentId) {
        boolean exists = departmentRepository.existsById(departmentId);
        if (!exists) {
            throw new IllegalStateException("department with id " + departmentId + " does not exists");
        }
        departmentRepository.deleteById(departmentId);
    }

    public void updateDepartment(Long departmentId, String name) {
        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new IllegalStateException(
                        "department with id " + departmentId + " does not exists"
                ));
        if (name != null) {
            department.setName(name);
        }
        departmentRepository.save(department);
    }

    public List<Employee> getManagers() {
        return departmentRepository.findManagers();
    }

    public List<Department> getDepartmentsByManager(Long employeeId) {
        return departmentRepository.findDepartmentsByManager(employeeId);
    }

    //Get Total Number of Employees in Each Department:
    public List<Object[]> getTotalEmployeesInEachDepartment() {
        return departmentRepository.getTotalEmployeesInEachDepartment();
    }
}
