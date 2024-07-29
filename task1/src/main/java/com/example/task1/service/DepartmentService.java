package com.example.task1.service;

import com.example.task1.model.Department;
import com.example.task1.model.Employee;
import com.example.task1.repository.DepartmentRepository;
import jakarta.persistence.criteria.CriteriaBuilder;
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
    public void addDepartement(Department departement) {
        departmentRepository.save(departement);
    }
    public List<Department> getDepartements() {
        return departmentRepository.findAll();
    }
    public void deleteDepartement(Long departementId) {
        boolean exists = departmentRepository.existsById(departementId);
        if (!exists) {
            throw new IllegalStateException("departement with id " + departementId + " does not exists");
        }
        departmentRepository.deleteById(departementId);
    }

    public void updateDepartement(Long departementId, String name ) {
        Department departement = departmentRepository.findById(departementId)
                .orElseThrow(() -> new IllegalStateException(
                        "departement with id " + departementId + " does not exists"
                ));
        if (name != null) {departement.setName(name);}
        departmentRepository.save(departement);
    }

    public List<Employee> getManagers(){
        return departmentRepository.findManagers();
    }
}
