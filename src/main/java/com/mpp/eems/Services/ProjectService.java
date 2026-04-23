package com.mpp.eems.Services;

import java.sql.SQLException;
import java.util.List;

import com.mpp.eems.Domain.Employee;
import com.mpp.eems.Domain.Project;
import com.mpp.eems.Repository.ClientProjectRepository;
import com.mpp.eems.Repository.EmployeeProjectRepository;
import com.mpp.eems.Repository.EmployeeRepository;
import com.mpp.eems.Repository.ProjectDepartmentRepository;
import com.mpp.eems.Repository.ProjectRepository;

/**
 * All crud operations relating to projects
 * ALL repositories as fields relating to projects, this includes essentially all repositories
 * contains also calculating costs and getting projects by departmetnt id with sort andwithout sort
 */
public class ProjectService extends Services {


    // ── CRUD ──────────────────────────────────────────────────────────────────

    public List<Project> getAllProjects() {
        return projectRepository.findAllProjects();
    }

    public Project getProjectById(int id) throws SQLException {
        return projectRepository.findProjectById(id);
    }

    public Project createProject(Project project) throws SQLException {
        return projectRepository.addProject(project);
    }

    public void updateProject(Project project) throws SQLException {
        projectRepository.modifyProject(project);
    }

    public void deleteProject(int id) throws SQLException {
        projectRepository.deleteProject(id);
    }

    // ── Lookups ───────────────────────────────────────────────────────────────

    /**
     * Returns all projects an employee is assigned to.
     */
    public List<Project> getProjectsByEmployee(int employeeId) {
        return projectRepository.findProjectsByEmployee(employeeId);
    }

    /**
     * Returns all projects linked to a department.
     */
    public List<Project> getProjectsByDepartment(int departmentId) {
        return projectRepository.findProjectsByDepartment(departmentId);
    }

    // ── Client links (Client_Project join table) ──────────────────────────────

    /**
     * Links a client to a project.
     */
    public void linkClient(int projectId, int clientId) throws SQLException {
        clientProjectRepository.linkClient(projectId, clientId);
    }

    /**
     * Removes a client from a project.
     */
    public void unlinkClient(int projectId, int clientId) throws SQLException {
        clientProjectRepository.unlinkClient(projectId, clientId);
    }

    /**
     * Returns all client IDs linked to a project.
     */
    public List<Integer> getClientIdsForProject(int projectId) {
        return clientProjectRepository.findClientIdsByProject(projectId);
    }

    // ── Department links (Project_Department join table) ──────────────────────

    /**
     * Links a department to a project.
     */
    public void linkDepartment(int projectId, int departmentId) throws SQLException {
        projectDepartmentRepository.linkDepartment(projectId, departmentId);
    }

    /**
     * Removes a department from a project.
     */
    public void unlinkDepartment(int projectId, int departmentId) throws SQLException {
        projectDepartmentRepository.unlinkDepartment(projectId, departmentId);
    }

    /**
     * Returns all department IDs linked to a project.
     */
    public List<Integer> getDepartmentIdsForProject(int projectId) {
        return projectDepartmentRepository.findDepartmentIdsByProject(projectId);
    }


    public double calculateProjectHRCost(int projectId) throws SQLException {
        Project project = projectRepository.findProjectById(projectId);
        if (project == null) throw new IllegalArgumentException("Project not found: " + projectId);

        // Duration in months, rounded up
        long days = java.time.temporal.ChronoUnit.DAYS.between(project.getStartDate(), project.getEndDate());
        int months = (int) Math.ceil(days / 30.0);

        // Get all employees on this project
        List<Employee> employees = employeeProjectRepository.findEmployeeIdsByProject(projectId).stream().map(eid -> employeeRepository.findById(eid)).toList();

        double totalCost = 0;
        for (Employee e : employees) {
            double monthlyRate = e.getSalary() / 12.0;
            double hoursAllocated = employeeService.getProjectHoursPercentage(e.getId(), projectId);
            double totalHours = project.getEstimatedDurationHours();
            double weight = (totalHours > 0) ? hoursAllocated / totalHours : 0;
            totalCost += monthlyRate * months * weight;
        }
        return totalCost;
    }


    public List<Project> getProjectsByDepartment(int departmentId, String sortBy) {
        return projectRepository.findActiveProjectsByDepartment(departmentId, sortBy);
    }
}
