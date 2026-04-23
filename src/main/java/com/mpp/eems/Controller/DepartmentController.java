package com.mpp.eems.Controller;

import com.mpp.eems.Domain.Department;
import com.mpp.eems.Services.DepartmentService;

import java.sql.SQLException;
import java.util.List;

public class DepartmentController {

    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    // ── CRUD ──────────────────────────────────────────────────────────────────

    public List<Department> getAllDepartments() {
        return departmentService.getAllDepartments();
    }

    public Department getDepartmentById(int id) {
        return departmentService.getDepartmentById(id);
    }

    public Department createDepartment(Department department) throws SQLException {
        return departmentService.createDepartment(department);
    }

    public void updateDepartment(Department department) throws SQLException {
        departmentService.updateDepartment(department);
    }

    public void deleteDepartment(int id) throws SQLException {
        departmentService.deleteDepartment(id);
    }

    // ── Lookups ───────────────────────────────────────────────────────────────

    public Department getDepartmentByEmployee(int employeeId) throws SQLException {
        return departmentService.getDepartmentByEmployee(employeeId);
    }

    public List<Department> getDepartmentsByProject(int projectId) throws SQLException {
        return departmentService.getDepartmentsByProject(projectId);
    }

    // ── Project links ─────────────────────────────────────────────────────────

    public void linkToProject(int departmentId, int projectId) throws SQLException {
        departmentService.linkToProject(departmentId, projectId);
    }

    public void unlinkFromProject(int departmentId, int projectId) throws SQLException {
        departmentService.unlinkFromProject(departmentId, projectId);
    }

    public List<Integer> getProjectIdsForDepartment(int departmentId) {
        return departmentService.getProjectIdsForDepartment(departmentId);
    }
}
