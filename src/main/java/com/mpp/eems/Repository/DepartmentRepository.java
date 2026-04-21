package com.mpp.eems.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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

    public void addDepartment(){

    }
    
    public void findDepartment(){

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