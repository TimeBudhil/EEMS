package com.mpp.eems.Services;

import com.mpp.eems.Domain.Project;
import com.mpp.eems.Repository.ClientProjectRepository;
import com.mpp.eems.Repository.ProjectDepartmentRepository;
import com.mpp.eems.Repository.ProjectRepository;

import java.sql.SQLException;
import java.util.List;

public class ProjectService {

    private final ProjectRepository projectRepository;
    private final ClientProjectRepository clientProjectRepository;
    private final ProjectDepartmentRepository projectDepartmentRepository;

    public ProjectService(ProjectRepository projectRepository,
                          ClientProjectRepository clientProjectRepository,
                          ProjectDepartmentRepository projectDepartmentRepository) {
        this.projectRepository = projectRepository;
        this.clientProjectRepository = clientProjectRepository;
        this.projectDepartmentRepository = projectDepartmentRepository;
    }

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
}
