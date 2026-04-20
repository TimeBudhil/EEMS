package Services;

import Domain.Project;

public class ProjectService {

    //for now we can print the values,
    public static double calculateProjectHRCost(int projectId){
        //find project time

        //For every employee assigned to the project, calculate their weighted cost:
        //e.salary/12 * months * allocationpercentage/100
        return 0;
    }

    public static Project[] getProjectsByDepartment(int departmentId, String sortBy){
        //find all projects where status = active, sorted by the sorting specification
        return new Project[0];
    }
}
