package com.mpp.eems.Services;

import java.sql.SQLException;
import java.util.List;

import com.mpp.eems.Domain.Department;
import com.mpp.eems.Repository.DepartmentRepository;
import com.mpp.eems.Repository.ProjectDepartmentRepository;

public class DepartmentService {

    private final DepartmentRepository departmentRepository = new DepartmentRepository();
    private final ProjectDepartmentRepository projectDepartmentRepository = new ProjectDepartmentRepository();

    // ── CRUD ──────────────────────────────────────────────────────────────────

    public List<Department> getAllDepartments() {
        return departmentRepository.findAllDepartment();
    }

    public Department getDepartmentById(int id) {
        return departmentRepository.findDepartmentById(id);
    }

    public Department createDepartment(Department department) throws SQLException {
        return departmentRepository.addDepartment(department);
    }

    public void updateDepartment(Department department) throws SQLException {
        departmentRepository.modifyDepartment(department);
    }

    public void deleteDepartment(int id) throws SQLException {
        departmentRepository.deleteDepartment(id);
    }

    // ── Lookups ───────────────────────────────────────────────────────────────

    /**
     * Returns the department an employee belongs to.
     */
    public Department getDepartmentByEmployee(int employeeId) throws SQLException {
        return departmentRepository.findDepartmentByEmployee(employeeId);
    }

    /**
     * Returns all departments linked to a project.
     */
    public List<Department> getDepartmentsByProject(int projectId) throws SQLException {
        return departmentRepository.findDepartmentsByProject(projectId);
    }

        // ── Project links (Project_Department join table) ─────────────────────────

    /**
     * Links a department to a project.
     */
    public void linkToProject(int departmentId, int projectId) throws SQLException {
        projectDepartmentRepository.linkDepartment(projectId, departmentId);
    }

    /**
     * Removes a department from a project.
     */
    public void unlinkFromProject(int departmentId, int projectId) throws SQLException {
        projectDepartmentRepository.unlinkDepartment(projectId, departmentId);
    }

    /**
     * Returns all project IDs a department is assigned to.
     */
    public List<Integer> getProjectIdsForDepartment(int departmentId) {
        return projectDepartmentRepository.findProjectIdsByDepartment(departmentId);
    }
}
