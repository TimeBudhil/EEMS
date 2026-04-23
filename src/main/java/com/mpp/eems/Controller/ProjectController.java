package com.mpp.eems.Controller;

import com.mpp.eems.Domain.Project;
import com.mpp.eems.Services.ProjectService;

import java.sql.SQLException;
import java.util.List;

public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    // ── CRUD ──────────────────────────────────────────────────────────────────

    public List<Project> getAllProjects() {
        return projectService.getAllProjects();
    }

    public Project getProjectById(int id) throws SQLException {
        return projectService.getProjectById(id);
    }

    public Project createProject(Project project) throws SQLException {
        return projectService.createProject(project);
    }

    public void updateProject(Project project) throws SQLException {
        projectService.updateProject(project);
    }

    public void deleteProject(int id) throws SQLException {
        projectService.deleteProject(id);
    }

    // ── Lookups ───────────────────────────────────────────────────────────────

    public List<Project> getProjectsByEmployee(int employeeId) {
        return projectService.getProjectsByEmployee(employeeId);
    }

    public List<Project> getProjectsByDepartment(int departmentId) {
        return projectService.getProjectsByDepartment(departmentId);
    }

    // ── Client links ──────────────────────────────────────────────────────────

    public void linkClient(int projectId, int clientId) throws SQLException {
        projectService.linkClient(projectId, clientId);
    }

    public void unlinkClient(int projectId, int clientId) throws SQLException {
        projectService.unlinkClient(projectId, clientId);
    }

    public List<Integer> getClientIdsForProject(int projectId) {
        return projectService.getClientIdsForProject(projectId);
    }

    // ── Department links ──────────────────────────────────────────────────────

    public void linkDepartment(int projectId, int departmentId) throws SQLException {
        projectService.linkDepartment(projectId, departmentId);
    }

    public void unlinkDepartment(int projectId, int departmentId) throws SQLException {
        projectService.unlinkDepartment(projectId, departmentId);
    }

    public List<Integer> getDepartmentIdsForProject(int projectId) {
        return projectService.getDepartmentIdsForProject(projectId);
    }
}
