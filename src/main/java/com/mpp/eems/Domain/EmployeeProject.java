package com.mpp.eems.Domain;

public class EmployeeProject {
    private int employeeId;
    private int projectId;
    private double hoursAllocated;

    public EmployeeProject(int employeeId, int projectId, double hoursAllocated) {
        this.employeeId = employeeId;
        this.projectId = projectId;
        this.hoursAllocated = hoursAllocated;
    }

    // Getters
    public int getEmployeeId() { return employeeId; }
    public int getProjectId() { return projectId; }
    public double getHoursAllocated() { return hoursAllocated; }
} 
