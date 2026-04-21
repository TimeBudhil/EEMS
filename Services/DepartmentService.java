package Services;

import Domain.Project;
import java.util.ArrayList;
import java.util.List;

public class DepartmentService extends Services{
    public static List<Project> getProjectsByDepartment(int departmentId, String sortBy){
        //find all projects where status = active, sorted by the sorting specification
        return new ArrayList<>();
    }
}
