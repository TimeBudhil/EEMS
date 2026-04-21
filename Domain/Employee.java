package Domain;

import java.time.LocalDate;
import java.util.List;

public class Employee{
    private int id;
    private String firstName;
    private String lastName;
    private String title;
    private LocalDate hiredDate;
    private double salary;

    //associations
    private Department department;
    private List<Project> projects;

    public Employee(int id, String firstName, String lastName, String title, LocalDate hiredDate, double salary, Department department, List<Project> projects) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.title = title;
        this.hiredDate = hiredDate;
        this.salary = salary;
        this.department = department;
        this.projects = projects;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setHiredDate(LocalDate hiredDate) {
        this.hiredDate = hiredDate;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    
}