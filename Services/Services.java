package Services;

import Domain.*;

public class Services {

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

    //lists all clients where their projects have currentDate - project.deadline < daysUntilDeadline
    public static Client[] findClientsByUpcomingProjectDeadline(int daysUntilDeadline){
        return new Client[0];
    }

    //updates employeeID and department record
    public static void transferEmployeeToDepartment(int employeeId, int newDepartmentId){

        // Perform a transaction to update an employee's department ID. This requires interacting with
        // both the Employee and Department records. Implement validation to ensure the transfer is
        // possible before committing the changes.

    }

}
