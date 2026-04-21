package com.mpp.eems.Domain;

import java.util.List;

public class Department {
    private int id;
    private String name;
    private String city;
    private double annual_budget;

    //associations
    private List<Employee> employeeList;

    //derived association
    // private List<Project> projects;


    public Department(int id, String name, String city, double  annual_budget, List<Employee> employeeList) {
        this.id = id;
        this.name = name;
        this.city = city;
        this.annual_budget = annual_budget;
        this.employeeList = employeeList;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCity() {
        return city;
    }

    public double getAnnualBudget() {
        return annual_budget;
    }

    public List<Employee> getEmployeeList() {
        return employeeList;
    }

    
}
