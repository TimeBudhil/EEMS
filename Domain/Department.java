package Domain;

import java.util.List;

public class Department {
    private String name;
    private String city;
    private String annual_budget;

    //associations
    private List<Employee> employeeList;
    private List<Project> projects;

}
