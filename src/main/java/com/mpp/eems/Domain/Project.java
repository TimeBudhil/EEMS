package com.mpp.eems.Domain;

import java.time.LocalDate;
import java.util.List;

public class Project{
    private int id;
    private String name;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private double estimatedDurationHours;
    private double totalBudget;
    private Status status;

    //associations
    private List<Employee> employees; //completed by
    private List<Client> clients; //requested by

    //derived association
    private List<Department> departments; //completed by, can be gained by getting employees'departments!

    public Project(int id, String name, String description, LocalDate startDate, LocalDate endDate, double estimatedDurationHours, double totalBudget, Status status, List<Employee> employees, List<Client> clients) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.estimatedDurationHours = estimatedDurationHours;
        this.totalBudget = totalBudget;
        this.status = status;
        this.employees = employees;
        this.clients = clients;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public double getTotalBudget() {
        return totalBudget;
    }

    public Status getStatus() {
        return status;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public List<Client> getClients() {
        return clients;
    }
    
    public double getEstimatedDurationHours() {
        return estimatedDurationHours;
    }

    

    
}