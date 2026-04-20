package Domain;

import java.time.LocalDate;
import java.util.List;

public class Project{
    private int id;
    private String name;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private double totalBudget;
    private Status status;

    //associations
    private List<Employee> employees; //completed by
    private List<Client> clients; //requested by
    // private List<Department> departments; //completed by, can be gained by getting employees'departments!
}