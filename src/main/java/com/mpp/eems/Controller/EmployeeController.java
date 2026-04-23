package com.mpp.eems.Controller;

import com.mpp.eems.Domain.Employee;
import com.mpp.eems.Domain.Project;
import com.mpp.eems.Services.EmployeeService;

import java.sql.SQLException;
import java.util.List;

public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    // ── CRUD ──────────────────────────────────────────────────────────────────

    public List<Employee> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    public Employee getEmployeeById(int id) {
        return employeeService.getEmployeeById(id);
    }

    public Employee createEmployee(Employee employee) {
        return employeeService.createEmployee(employee);
    }

    public void updateEmployee(Employee employee) {
        employeeService.updateEmployee(employee);
    }

    public void deleteEmployee(int id) {
        employeeService.deleteEmployee(id);
    }

    // ── Project assignments ───────────────────────────────────────────────────

    public List<Project> getProjectsForEmployee(int employeeId) {
        return employeeService.getProjectsForEmployee(employeeId);
    }

    public void assignToProject(int employeeId, int projectId, double hoursAllocated) {
        employeeService.assignToProject(employeeId, projectId, hoursAllocated);
    }

    public void removeFromProject(int employeeId, int projectId) throws SQLException {
        employeeService.removeFromProject(employeeId, projectId);
    }

    public double getProjectHoursPercentage(int employeeId, int projectId) {
        return employeeService.getProjectHoursPercentage(employeeId, projectId);
    }

    public void transferEmployeeToDepartment(int employeeId, int newDepartmentId){
        employeeService.transferEmployeeToDepartment(employeeId,newDepartmentId);
    }
}
