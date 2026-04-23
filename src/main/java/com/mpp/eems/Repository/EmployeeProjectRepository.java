package com.mpp.eems.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


/**
 * CRUD operatinos for the employee_project connection!
 * 
 */
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

    /**
     * Assigns a project to an employee with hours. If the assignment already
     * exists, it updates the hours instead.
     */
    public void assignProjectToEmployee(int employeeId, int projectId, double hoursAllocated) {
        String sql = """
            INSERT INTO Employee_Project (employee_id, project_id, hours_allocated)
            VALUES (?, ?, ?)
            ON CONFLICT (employee_id, project_id)
            DO UPDATE SET hours_allocated = EXCLUDED.hours_allocated
        """;

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, employeeId);
            stmt.setInt(2, projectId);
            stmt.setDouble(3, hoursAllocated);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Failed to assign project to employee", e);
        }
    }

    /**
     * Calculates what percentage of an employee's total allocated hours
     * are dedicated to a specific project.
     *
     * e.g. Employee has 30h on Project A, 70h on Project B → Project A = 30%
     */
    public double calculateEmployeePercentageHours(int employeeId, int projectId) {
        String sql = """
            SELECT
                ep.hours_allocated AS project_hours,
                SUM(all_ep.hours_allocated) AS total_hours
            FROM Employee_Project ep
            JOIN Employee_Project all_ep ON all_ep.employee_id = ep.employee_id
            WHERE ep.employee_id = ?
              AND ep.project_id = ?
            GROUP BY ep.hours_allocated
        """;

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, employeeId);
            stmt.setInt(2, projectId);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                double projectHours = rs.getDouble("project_hours");
                double totalHours   = rs.getDouble("total_hours");

                if (totalHours == 0) return 0.0;
                return (projectHours / totalHours) * 100.0;
            }

            return 0.0; // employee not assigned to this project

        } catch (SQLException e) {
            throw new RuntimeException("Failed to calculate employee percentage hours", e);
        }
    }
}