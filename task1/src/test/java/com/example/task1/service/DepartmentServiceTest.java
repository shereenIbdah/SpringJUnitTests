package com.example.task1.service;

import com.example.task1.model.Department;
import com.example.task1.model.Employee;
import com.example.task1.repository.DepartmentRepository;
import net.bytebuddy.asm.Advice;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DepartmentServiceTest {
    @Mock
    private DepartmentRepository departmentRepository;
    @InjectMocks
    private DepartmentService departmentService;

    @Test
    public void TestAddDepartment(){
        //Arrange
        Department department = new Department(1L, "IT", null, null);
        //Act
        departmentService.addDepartment(department);
        //Assert
        verify(departmentRepository, times(1)).save(department);
    }
    @Test
    public void TestGetDepartments(){
        Department department = new Department(1L, "IT", null, null);
        Department department2 = new Department(2L, "Finance", null, null);
        List<Department> departments = List.of(department, department2);
        //Mock
        when(departmentRepository.findAll()).thenReturn(departments);
        //Act
        List<Department> result = departmentService.getDepartments();

        //Assert
        assertEquals(2, result.size());
        assertEquals(department, result.get(0));
    }
    @Test
    public void TestDeleteDepartment(){
        Long departmentId = 1L;
        //Act
        when(departmentRepository.existsById(departmentId)).thenReturn(true);
        // Act
        departmentService.deleteDepartment(departmentId);
        //Assert
        verify(departmentRepository, times(1)).deleteById(departmentId);
    }
    @Test
    public void testDeleteDepartment_ThrowsExceptionWhenDepartmentDoesNotExist() {
        // Arrange
        Long  departmentId = 1L;
        when(departmentRepository.existsById(departmentId)).thenReturn(false);
        // Act & Assert
        assertThrows(IllegalStateException.class, () -> departmentService.deleteDepartment(departmentId));
    }
    @Test
    public void testUpdateDepartment() {
        //Arrange
        Department existingDepartment = new Department(1L, "IT", null, null);
        // Arrange
        when(departmentRepository.findById(1L)).thenReturn(Optional.of(existingDepartment));
        // Act
        departmentService.updateDepartment(1L, "Finance");
        // Assert
        verify(departmentRepository, times(1)).save(existingDepartment);
        assertEquals("Finance", existingDepartment.getName());
    }
    @Test
    public void testUpdateDepartmentException(){
        Long departmentId = 1L;
        when(departmentRepository.findById(departmentId)).thenReturn(Optional.empty());
        assertThrows(IllegalStateException.class, () -> departmentService.updateDepartment(departmentId, "Finance"));
    }
    @Test
    public void TestGetManagers(){
        // Arrange
        Employee manager1 = new Employee(2L, "ahmad", 25, "male", 123456789, 5000.0, LocalDate.of(2021, 1, 1), "developer", null, null);
        Employee manager2 = new Employee(3L, "shereen", 21, "female", 123456789, 5000.0, LocalDate.of(2021, 1, 1), "developer", null, null);
        List<Employee> managers = List.of(manager2, manager1);
        when(departmentRepository.findManagers()).thenReturn(managers);
        //Act
        List<Employee> result =  departmentService.getManagers();
        // Assert
        assertEquals(managers,result);
    }
    @Test
    public void TestGetDepartmentsByManager(){
        // Arrange
        Department department1 = new Department(1L, "IT", null, null);
        Department department2 = new Department(2L, "Finance", null, null);
        List<Department> departments = List.of(department1, department2);
        when(departmentRepository.findDepartmentsByManager(1L)).thenReturn(departments);
        //Act
        List<Department> result = departmentService.getDepartmentsByManager(1L);
        // Assert
        assertEquals(departments,result);
    }
    @Test
    public void TestGetTotalEmployeesInEachDepartment(){
        // Arrange
        Object[] object1 = {"IT", 2L};
        Object[] object2 = {"Finance", 1L};
        List<Object[]> objects = List.of(object1, object2);
        when(departmentRepository.getTotalEmployeesInEachDepartment()).thenReturn(objects);
        //Act
        List<Object[]> result = departmentService.getTotalEmployeesInEachDepartment();
        // Assert
        assertEquals(objects,result);
    }
}
