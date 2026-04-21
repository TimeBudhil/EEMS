package Domain;

import java.util.List;

public class Department {
    private int id;
    private String name;
    private String city;
    private String annual_budget;

    //associations
    private List<Employee> employeeList;
    private List<Project> projects;

    public Department(int id, String name, String city, String annual_budget, List<Employee> employeeList, List<Project> projects) {
        this.id = id;
        this.name = name;
        this.city = city;
        this.annual_budget = annual_budget;
        this.employeeList = employeeList;
        this.projects = projects;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setAnnual_budget(String annual_budget) {
        this.annual_budget = annual_budget;
    }

    public void setEmployeeList(List<Employee> employeeList) {
        this.employeeList = employeeList;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    

}
