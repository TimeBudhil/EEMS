package com.mpp.eems.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.mpp.eems.Domain.Department;

public class DepartmentRepository extends Repository{
    
    private Department mapRowToDepartment(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String name = rs.getString("name");
        String city = rs.getString("city");

        double annual_budget = rs.getDouble("annual_budget");

        Department d = new Department(id, name, city, annual_budget, new ArrayList<>());

        return d;
    }

    
    public Department addDepartment(Department department) throws SQLException {
        String sql = """
                INSERT INTO Department (name, city, annual_budget)
                VALUES (?, ?, ?)
                """;

        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, department.getName());
            stmt.setString(2, department.getCity());
            stmt.setObject(3, department.getAnnual_budget());   // LocalDate maps cleanly via setObject

            stmt.executeUpdate();

            // Retrieve the DB-generated id and return a complete Project
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int generatedId = generatedKeys.getInt(1);
                    return new Department(
                        generatedId,
                        department.getName(),
                        department.getCity(),
                        department.getAnnual_budget(),
                        new ArrayList<>()
                    );
                }
            }
        }
        throw new SQLException("Insert failed — no ID returned");
    }
    
    public Department findDepartmentById(int departmentId){
        String sql = "SELECT * FROM Department WHERE id = ?";

        try(PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setInt(1, departmentId);
            try(ResultSet rs = stmt.executeQuery(sql)){
                if(rs.next()){
                    return mapRowToDepartment(rs);
                }
            }
        } catch(SQLException e){
            System.err.println(e.getMessage());
        }
        return null;
    }
    
    public void findAllDepartment(){

    }
    
    public void modifyDepartment(){

    }
    
    public void deleteDepartment(){

    }


    public List<Department> findDepartmentByProject(int projectId) throws SQLException {
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