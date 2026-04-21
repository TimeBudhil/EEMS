package com.mpp.eems.Repository;

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

    public void addProject(){

    }
    
    public void findProject(){

    }
    
    public List<Project> findAllProject(){
        String sql = "SELECT * FROM Project";
        List<Project> projects = new ArrayList<>();

        try (Statement stmt = DBConnection.getConnection().createStatement();
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
    
    public void modifyProject(){

    }
    
    public void deleteProject(){

    }

    public List<Employee> findAssociatedEmployees(int projectId){
        return new ArrayList<>();
    }

    public List<Department> findAssociatedDepartments(int projectId){
        List<Employee> emps = findAssociatedEmployees(projectId);
       

        return  emps.stream()
            .map(e -> e.getDepartment())
            .distinct()
            .toList();
    }
    
}
