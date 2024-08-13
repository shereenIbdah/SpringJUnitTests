package com.example.task1.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "department")
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @ManyToMany
    @JoinTable(
            name = "department_employee",
            joinColumns = @JoinColumn(name = "department_id"),
            inverseJoinColumns = @JoinColumn(name = "employee_id")
    )
    private List<Employee> employees;
    // the f.k of the employee will added to the departement table
    @OneToOne
    @JoinColumn(name = "manager_id", referencedColumnName = "id")
    private Employee manager;


    public Department() {
    }

    public Department(String name, List<Employee> employees, Employee manager) {
        this.name = name;
        this.manager = manager;
        this.employees = employees;
    }

    public Department(Long id, String name, List<Employee> employees, Employee manager) {
        this.id = id;
        this.name = name;
        this.employees = employees;
        this.manager = manager;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    public Employee getManager() {
        return manager;
    }

    public void setManager(Employee manager) {
        this.manager = manager;
    }


}
