package com.mpp.eems.Services;

import java.sql.SQLException;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.List;

import com.mpp.eems.Domain.Employee;
import com.mpp.eems.Domain.Project;
import com.mpp.eems.Domain.Status;
import com.mpp.eems.Repository.EmployeeProjectRepository;
import com.mpp.eems.Repository.EmployeeRepository;
import com.mpp.eems.Repository.ProjectRepository;

public class ProjectService extends Services{

    ProjectRepository projRepo = new ProjectRepository();
    EmployeeRepository empRepo = new EmployeeRepository();
    EmployeeProjectRepository empProjRepo = new EmployeeProjectRepository();

    //for now we can print the values,
    public double calculateProjectHRCost(int projectId){
        //find project time
        Project p = null;
        try {
            p = projRepo.findProjectById(projectId);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        long months = ChronoUnit.MONTHS.between(
            p.getStartDate(),
            p.getEndDate()
        );

        List<Employee> employees = empProjRepo.findEmployeeIdsByProject(projectId).stream()
            .map(eid -> empRepo.findById(eid))
            .toList();
        
        double totalCost = 0;

        //incomplete, need to implement getAllocationPercentage!
        for(Employee e : employees){    
            //gets the percentage of work that employee did for that specific project, multiplied by that project
            double allocationPercentage = empProjRepo.calculateEmployeePercentageHours(e.getId(), projectId);
            totalCost += (e.getSalary() / 12.0) * months * (allocationPercentage / 100.0);
        }
        //For every employee assigned to the project, calculate their weighted cost:
        //e.salary/12 * months * allocationpercentage/100
        return totalCost;
    }


//     Retrieve a list of all Active projects associated with a specific department. The list must be
// sorted by a specified field (e.g., project_budget, end_date).
    public List<Project> getProjectsByDepartment(int departmentId, String sortBy){
        //find all projects where status = active, sorted by the sorting specification
        Comparator<Project> comparator;

        switch (sortBy.toLowerCase()) {
            case "id" -> comparator = Comparator.comparing(Project::getId);
            case "name" -> comparator = Comparator.comparing(Project::getName);
            case "description" -> comparator = Comparator.comparing(Project::getDescription);
            case "end_date", "enddate" -> comparator = Comparator.comparing(Project::getEndDate);
            case "startdate", "start_date" -> comparator = Comparator.comparing(Project::getStartDate);
            case "totalbudget", "total_budget" -> comparator = Comparator.comparing(Project::getTotalBudget);
            case "status" -> comparator = Comparator.comparing(Project::getStatus);

            default -> throw new IllegalArgumentException("Invalid sort field: " + sortBy);
        }

        return projRepo.findProjectsByDepartment(departmentId).stream()
            .filter(p -> p.getStatus().equals(Status.ACTIVE))
            .sorted(comparator)
            .toList();
    }

}
