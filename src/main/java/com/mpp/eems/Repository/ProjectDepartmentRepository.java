package com.mpp.eems.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * CRUD operatinos for project_department table
 */
public class ProjectDepartmentRepository extends Repository{
    public void linkDepartment(int projectId, int departmentId) throws SQLException {
        String sql = "INSERT INTO Project_Department (project_id, department_id) VALUES (?, ?)";

        try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {
            stmt.setInt(1, projectId);
            stmt.setInt(2, departmentId);
            stmt.executeUpdate();
        }
    }

    public void unlinkDepartment(int projectId, int departmentId) throws SQLException {
        String sql = "DELETE FROM Project_Department WHERE project_id = ? AND department_id = ?";

        try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {
            stmt.setInt(1, projectId);
            stmt.setInt(2, departmentId);
            stmt.executeUpdate();
        }
    }

    public List<Integer> findDepartmentIdsByProject(int projectId) {
        String sql = "SELECT department_id FROM Project_Department WHERE project_id = ?";
        List<Integer> ids = new ArrayList<>();

        try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {
            stmt.setInt(1, projectId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    ids.add(rs.getInt("department_id"));
                }
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        return ids;
    }

    public List<Integer> findProjectIdsByDepartment(int departmentId) {
        String sql = "SELECT project_id FROM Project_Department WHERE department_id = ?";
        List<Integer> ids = new ArrayList<>();

        try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {
            stmt.setInt(1, departmentId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    ids.add(rs.getInt("project_id"));
                }
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        return ids;
    }

    
}
