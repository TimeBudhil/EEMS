package com.mpp.eems.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.mpp.eems.Domain.Department;
import com.mpp.eems.Domain.Employee;
import com.mpp.eems.Domain.Project;
import com.mpp.eems.Domain.Status;

public class ProjectRepository extends Repository {

    private Project mapRowToProject(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String name = rs.getString("name");
        String description = rs.getString("description");

        LocalDate startDate = rs.getObject("start_date", LocalDate.class);
        LocalDate endDate = rs.getObject("end_date", LocalDate.class);

        double totalBudget = rs.getDouble("total_budget");
        Status status = Status.valueOf(rs.getString("status"));

        Project p = new Project(id, name, description, startDate, endDate, totalBudget, status, new ArrayList<>(), new ArrayList<>());

        return p;
    }

    public Project addProject(Project project) throws SQLException {
        String sql = """
                INSERT INTO Project (name, description, start_date, end_date, total_budget, status)
                VALUES (?, ?, ?, ?, ?, ?)
                """;

        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, project.getName());
            stmt.setString(2, project.getDescription());
            stmt.setObject(3, project.getStartDate());   // LocalDate maps cleanly via setObject
            stmt.setObject(4, project.getEndDate());
            stmt.setDouble(5, project.getTotalBudget());
            stmt.setString(6, project.getStatus().name().toLowerCase());

            stmt.executeUpdate();

            // Retrieve the DB-generated id and return a complete Project
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int generatedId = generatedKeys.getInt(1);
                    return new Project(
                        generatedId,
                        project.getName(),
                        project.getDescription(),
                        project.getStartDate(),
                        project.getEndDate(),
                        project.getTotalBudget(),
                        project.getStatus(),
                        new ArrayList<>(),
                        new ArrayList<>()
                    );
                }
            }
        }
        throw new SQLException("Insert failed — no ID returned");
    }
    
    public List<Project> findProjectsByEmployee(int employeeId){
        String sql = """
            SELECT p.*
            FROM Project p
            JOIN Employee_Project ep ON p.id = ep.project_id
            WHERE ep.employee_id = ?
            """;

        List<Project> projects = new ArrayList<>();

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, employeeId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {                     // while — not if
                    projects.add(mapRowToProject(rs));
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return projects; // always reached — returns empty list if none found
    }

    public Project findById(int id) throws SQLException {
        String sql = "SELECT * FROM Project WHERE id = ?";

        // PreparedStatement used here — never concatenate user input into SQL
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapRowToProject(rs);
                }
            }
        } catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null; // or throw a NotFoundException if you prefer
    }
    
    public List<Project> findAllProjects(){
        String sql = "SELECT * FROM Project";
        List<Project> projects = new ArrayList<>();

        try (Statement stmt = connection.createStatement();
            //get the statement
             ResultSet rs = stmt.executeQuery(sql)) {

            //get each row and map to a project value
            while (rs.next()) {
                projects.add(mapRowToProject(rs));
            }
        } catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return projects;
    }
    
    public void modifyProject(Project project) throws SQLException {
        String sql = """
                UPDATE Project
                SET name        = ?,
                    description = ?,
                    start_date  = ?,
                    end_date    = ?,
                    total_budget = ?,
                    status      = ?
                WHERE id = ?
                """;

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, project.getName());
            stmt.setString(2, project.getDescription());
            stmt.setObject(3, project.getStartDate());
            stmt.setObject(4, project.getEndDate());
            stmt.setDouble(5, project.getTotalBudget());
            stmt.setString(6, project.getStatus().name().toLowerCase());
            stmt.setInt(7, project.getId());            // WHERE clause — must be last

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected == 0) {
                throw new SQLException("Update failed — no project found with id: " + project.getId());
            }
        }
    }
    
    public void deleteProject(int projectId) throws SQLException {
        String deleteEmployeeLinks = "DELETE FROM Employee_Project WHERE project_id = ?";
        String deleteClientLinks   = "DELETE FROM Client_Project WHERE project_id = ?";
        String deleteProject       = "DELETE FROM Project WHERE id = ?";

        try (
            PreparedStatement stmt1 = connection.prepareStatement(deleteEmployeeLinks);
            PreparedStatement stmt2 = connection.prepareStatement(deleteClientLinks);
            PreparedStatement stmt3 = connection.prepareStatement(deleteProject)
        ) {
            stmt1.setInt(1, projectId);
            stmt1.executeUpdate();

            stmt2.setInt(1, projectId);
            stmt2.executeUpdate();

            stmt3.setInt(1, projectId);
            int rowsAffected = stmt3.executeUpdate();

            if (rowsAffected == 0) {
                throw new SQLException("Delete failed — no project found with id: " + projectId);
            }
        }
    }

    public List<Employee> findAssociatedEmployees(int projectId) throws SQLException {
        String sql = """
                SELECT e.*
                FROM Employee e
                JOIN Employee_Project ep ON e.id = ep.employee_id
                WHERE ep.project_id = ?
                """;

        List<Employee> employees = new ArrayList<>();

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, projectId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    employees.add(mapRowToEmployee(rs));
                }
            }
        }
        return employees;
    }

    public List<Department> findAssociatedDepartments(int projectId) throws SQLException {
    String sql = """
            SELECT DISTINCT d.*
            FROM Department d
            JOIN Employee e ON e.department_id = d.id
            JOIN Employee_Project ep ON ep.employee_id = e.id
            WHERE ep.project_id = ?
            """;

    List<Department> departments = new ArrayList<>();

    try (PreparedStatement stmt = connection.prepareStatement(sql)) {
        stmt.setInt(1, projectId);
        try (ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                departments.add(mapRowToDepartment(rs));
            }
        }
    }
    return departments;
}
    
}
