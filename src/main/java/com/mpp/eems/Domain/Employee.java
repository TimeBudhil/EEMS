package com.mpp.eems.Domain;

import java.time.LocalDate;
import java.util.List;

/**
 * employee entity maps directly to db schema
 */
public class Employee{
    private int id;
    private String firstName;
    private String lastName;
    private String title;
    private LocalDate hiredDate;
    private double salary;

    //associations
    private Department department;
    private List<Project> projects;

    public Employee(int id, String firstName, String lastName, String title, LocalDate hiredDate, double salary, Department department, List<Project> projects) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.title = title;
        this.hiredDate = hiredDate;
        this.salary = salary;
        this.department = department;
        this.projects = projects;
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getTitle() {
        return title;
    }

    public LocalDate getHiredDate() {
        return hiredDate;
    }

    public double getSalary() {
        return salary;
    }

    public Department getDepartment() {
        return department;
    }

    public List<Project> getProjects() {
        return projects;
    }

    

    
}