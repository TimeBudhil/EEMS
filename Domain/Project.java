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
    private List<Department> departments; //completed by, can be gained by getting employees'departments!

    public Project(int id, String name, String description, LocalDate startDate, LocalDate endDate, double totalBudget, Status status, List<Employee> employees, List<Client> clients, List<Department> departments) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.totalBudget = totalBudget;
        this.status = status;
        this.employees = employees;
        this.clients = clients;
        this.departments = departments;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public void setTotalBudget(double totalBudget) {
        this.totalBudget = totalBudget;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    public void setClients(List<Client> clients) {
        this.clients = clients;
    }

    public void setDepartments(List<Department> departments) {
        this.departments = departments;
    }

    
}