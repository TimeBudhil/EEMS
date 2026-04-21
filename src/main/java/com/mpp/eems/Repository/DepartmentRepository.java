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
            stmt.setObject(3, department.getAnnualBudget());   // LocalDate maps cleanly via setObject

            stmt.executeUpdate();

            // Retrieve the DB-generated id and return a complete Project
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int generatedId = generatedKeys.getInt(1);
                    return new Department(
                        generatedId,
                        department.getName(),
                        department.getCity(),
                        department.getAnnualBudget(),
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
    
    public List<Department> findAllDepartment(){
        String sql = "SELECT * FROM Department";
        List<Department> departments = new ArrayList<>();

        //try statement
        try(Statement stmt = connection.createStatement()){
            //get all rows
            ResultSet rs = stmt.executeQuery(sql);

            //while there is a next row, add to list
            while(rs.next()){
                //current row is inserted into this function, which returns a department, and added to the list
                departments.add(mapRowToDepartment(rs));
            }
        }catch(SQLException e){
            //catch error in case anything happens
            System.err.println(e.getMessage());
        }

        return departments;
    }
    
    public void modifyDepartment(Department department) throws SQLException {
        String sql = """
                UPDATE Department
                SET name          = ?,
                    city          = ?,
                    annual_budget = ?
                WHERE id = ?
                """;

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, department.getName());
            stmt.setString(2, department.getCity());
            stmt.setDouble(3, department.getAnnualBudget());
            stmt.setInt(4, department.getId());         // WHERE clause — always last

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected == 0) {
                throw new SQLException("Update failed — no department found with id: " + department.getId());
            }

        } catch (SQLException e) {
            throw new SQLException("DB error in modifyDepartment: " + e.getMessage(), e);
        }
    }
    
    public void deleteDepartment(int departmentId) throws SQLException{
        String deleteEmployeeProjectLinks = 
            "DELETE FROM Employee_Project WHERE employee_id IN " +
            "(SELECT id FROM Employee WHERE department_id = ?)";
        String deleteEmployeeLinks = "DELETE FROM Employee WHERE department_id = ?";
        String deleteDepartment = "DELETE FROM Department WHERE id = ?";

        try(
            PreparedStatement stmt1 = connection.prepareStatement(deleteEmployeeProjectLinks);
        PreparedStatement stmt2 = connection.prepareStatement(deleteEmployeeLinks);
        PreparedStatement stmt3 = connection.prepareStatement(deleteDepartment);
        ) {
            //remove connections from table
            stmt1.setInt(1, departmentId);
            stmt1.executeUpdate();

            //remove department itself
            stmt2.setInt(1, departmentId);
            stmt2.executeUpdate();

            stmt3.setInt(1, departmentId);
            int rowsAffected = stmt3.executeUpdate();

            if(rowsAffected == 0){
                throw new SQLException("Delete failed — no department found with id: " + departmentId);
            }
        }

    }

    public Department findDepartmentByEmployee(int employeeId) throws SQLException{
        String sql = """
                SELECT d.*
                FROM Department d
                JOIN Employee e
                ON e.department_id = d.id
                where e.id = ?
                """;

        //prepared statement
        try(PreparedStatement stmt = connection.prepareStatement(sql)){
            //insert the id of the employee
            stmt.setInt(1, employeeId);

            //find row
            try(ResultSet rs =stmt.executeQuery()){
                if(rs.next()){
                    return mapRowToDepartment(rs);  
                }
                throw new SQLException("No department found for employee with id: " + employeeId);
            }catch(SQLException e){
                System.err.println();
            }

        } catch(SQLException e){
            throw new SQLException("DB error in findDepartmentByEmployee: " + e.getMessage(), e);
        }

        //unreachable in practice, but compiler won't let us not have this
        return null;
    }

    public List<Department> findDepartmentsByProject(int projectId) throws SQLException {
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
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return departments;
    } 

}