package com.mpp.eems.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.mpp.eems.Domain.Project;
import com.mpp.eems.Domain.Status;

public class ProjectRepository extends Repository {

    private Project mapRowToProject(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String name = rs.getString("name");
        String description = rs.getString("description");

        LocalDate startDate = rs.getObject("start_date", LocalDate.class);
        LocalDate endDate = rs.getObject("end_date", LocalDate.class);

        double estimatedDurationHours = rs.getDouble("estimated_duration_hours");
        double totalBudget = rs.getDouble("total_budget");
        Status status = Status.valueOf(rs.getString("status").toUpperCase());

        Project p = new Project(id, name, description, startDate, endDate, estimatedDurationHours, totalBudget, status, new ArrayList<>(), new ArrayList<>());
        return p;
    }

    public Project addProject(Project project) throws SQLException {
        String sql = """
            INSERT INTO Project (name, description, start_date, end_date, estimated_duration_hours, total_budget, status)
            VALUES (?, ?, ?, ?, ?, ?, ?)
            """;

        try (PreparedStatement stmt = getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, project.getName());
            stmt.setString(2, project.getDescription());
            stmt.setObject(3, project.getStartDate());
            stmt.setObject(4, project.getEndDate());
            stmt.setDouble(5, project.getEstimatedDurationHours());
            stmt.setDouble(6, project.getTotalBudget());
            stmt.setString(7, project.getStatus().name().toLowerCase());

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
                        project.getEstimatedDurationHours(),
                        project.getTotalBudget(),
                        project.getStatus(),
                        new ArrayList<>(),
                        new ArrayList<>()
                    );
                }
            }
        } catch(SQLException e){
            System.err.println(e.getMessage());
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

        try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {
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

    public List<Project> findProjectsByDepartment(int departmentId) {
        String sql = """
                SELECT p.*
                FROM Project p
                JOIN Project_Department pd ON p.id = pd.project_id
                WHERE pd.department_id = ?
                """;
        List<Project> projects = new ArrayList<>();

        try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {
            stmt.setInt(1, departmentId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    projects.add(mapRowToProject(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        return projects;
    }

    public Project findProjectById(int id) throws SQLException {
        String sql = "SELECT * FROM Project WHERE id = ?";

        // PreparedStatement used here — never concatenate user input into SQL
        try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {
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

        try (Statement stmt = getConnection().createStatement();
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
            SET name                     = ?,
                description              = ?,
                start_date               = ?,
                end_date                 = ?,
                estimated_duration_hours = ?,
                total_budget             = ?,
                status                   = ?
            WHERE id = ?
            """;

        try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {
            stmt.setString(1, project.getName());
            stmt.setString(2, project.getDescription());
            stmt.setObject(3, project.getStartDate());
            stmt.setObject(4, project.getEndDate());
            stmt.setDouble(5, project.getEstimatedDurationHours());
            stmt.setDouble(6, project.getTotalBudget());
            stmt.setString(7, project.getStatus().name().toLowerCase());
            stmt.setInt(8, project.getId());

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected == 0) {
                throw new SQLException("Update failed — no project found with id: " + project.getId());
            }
        }
    }
    
    public void deleteProject(int projectId) throws SQLException {
        //delete all links!
        String deleteEmployeeLinks   = "DELETE FROM Employee_Project WHERE project_id = ?";
        String deleteClientLinks     = "DELETE FROM Client_Project WHERE project_id = ?";
        String deleteDepartmentLinks = "DELETE FROM Project_Department WHERE project_id = ?";
        String deleteProject         = "DELETE FROM Project WHERE id = ?";

        try (
            Connection connection = getConnection();
            PreparedStatement stmt1 = connection.prepareStatement(deleteEmployeeLinks);
            PreparedStatement stmt2 = connection.prepareStatement(deleteClientLinks);
            PreparedStatement stmt3 = connection.prepareStatement(deleteDepartmentLinks);
            PreparedStatement stmt4 = connection.prepareStatement(deleteProject)
        ) {
            stmt1.setInt(1, projectId);
            stmt1.executeUpdate();

            stmt2.setInt(1, projectId);
            stmt2.executeUpdate();

            stmt3.setInt(1, projectId);
            stmt3.executeUpdate();

            stmt4.setInt(1, projectId);
            int rowsAffected = stmt4.executeUpdate();

            if (rowsAffected == 0) {
                throw new SQLException("Delete failed — no project found with id: " + projectId);
            }
        }
    }

    public List<Project> findActiveProjectsByDepartment(int departmentId, String sortBy) {
        // Whitelist allowed columns to prevent SQL injection
        String orderColumn = switch (sortBy) {
            case "total_budget"              -> "p.total_budget";
            case "end_date"                  -> "p.end_date";
            case "start_date"                -> "p.start_date";
            case "estimated_duration_hours"  -> "p.estimated_duration_hours";
            case "name"                      -> "p.name";
            default                          -> "p.end_date"; // safe fallback
        };

        String sql = """
            SELECT p.*
            FROM Project p
            JOIN Project_Department pd ON p.id = pd.project_id
            WHERE pd.department_id = ?
            AND p.status = 'active'
            ORDER BY
            """ + orderColumn;

        List<Project> projects = new ArrayList<>();

        try {
            PreparedStatement stmt = getConnection().prepareStatement(sql);
            stmt.setInt(1, departmentId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                projects.add(mapRowToProject(rs));
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        return projects;
    }
}
