package com.example.task1.repository;

import com.example.task1.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository  extends JpaRepository<Employee, Long> {
    @Query("SELECT e FROM Employee e WHERE e.gender = ?1")
    List<Employee> findEmployeeByGender(String gender);
    //- get all names of employees who work in the specific department.
    @Query("SELECT e.name FROM Employee e JOIN e.departments d WHERE d.id = ?1")
    //@Query(value = "SELECT e.* FROM employee e ,department_employee d   where d.department_id=?1 and  e.id=d.employee_id;",nativeQuery = true)
    List<String> findEmployeeNamesByDepartment(Long departmentId);


}
