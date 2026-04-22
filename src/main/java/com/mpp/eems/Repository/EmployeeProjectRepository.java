package com.mpp.eems.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeProjectRepository extends Repository {

    public void linkEmployee(int projectId, int employeeId) throws SQLException {
        String sql = "INSERT INTO Employee_Project (project_id, employee_id) VALUES (?, ?)";

        try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {
            stmt.setInt(1, projectId);
            stmt.setInt(2, employeeId);
            stmt.executeUpdate();
        }
    }

    public void unlinkEmployee(int projectId, int employeeId) throws SQLException {
        String sql = "DELETE FROM Employee_Project WHERE project_id = ? AND employee_id = ?";

        try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {
            stmt.setInt(1, projectId);
            stmt.setInt(2, employeeId);
            stmt.executeUpdate();
        }
    }

    public List<Integer> findEmployeeIdsByProject(int projectId) {
        String sql = "SELECT employee_id FROM Employee_Project WHERE project_id = ?";
        List<Integer> ids = new ArrayList<>();

        try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {
            stmt.setInt(1, projectId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    ids.add(rs.getInt("employee_id"));
                }
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        return ids;
    }

    public List<Integer> findProjectIdsByEmployee(int employeeId) {
        String sql = "SELECT project_id FROM Employee_Project WHERE employee_id = ?";
        List<Integer> ids = new ArrayList<>();

        try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {
            stmt.setInt(1, employeeId);
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