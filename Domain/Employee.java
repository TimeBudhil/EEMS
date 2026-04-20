package Domain;

import java.time.LocalDate;
import java.util.List;

public class Employee{
    private String firstName;
    private String lastName;
    private String title;
    private LocalDate hiredDate;
    private double salary;

    //associations
    private Department department;
    private List<Project> projects;
}