package com.example.task1.repository;

import com.example.task1.model.Department;
import com.example.task1.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {
    // get all managers of departments
    @Query("SELECT d.manager FROM Department d")
    List<Employee> findManagers();

    //getDepartmentsByManager
    @Query("SELECT d FROM Department d WHERE d.manager.id = ?1")
    List<Department> findDepartmentsByManager(Long employeeId);
    //Get Total Number of Employees in Each Department:

    @Query("SELECT d.name ,COUNT(e) FROM Department d JOIN d.employees e  GROUP BY d")
    List<Object[]> getTotalEmployeesInEachDepartment();

}
