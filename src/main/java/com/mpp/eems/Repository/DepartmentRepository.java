package com.mpp.eems.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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
    

}