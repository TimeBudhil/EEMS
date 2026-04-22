package com.mpp.eems.Services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.mpp.eems.Domain.Project;
import com.mpp.eems.Domain.Status;
import com.mpp.eems.Repository.ProjectRepository;

public class ProjectService extends Services{

    ProjectRepository projRepo = new ProjectRepository();

    //for now we can print the values,
    public static double calculateProjectHRCost(int projectId){
        //find project time

        //For every employee assigned to the project, calculate their weighted cost:
        //e.salary/12 * months * allocationpercentage/100
        return 0;
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
