package Repository;

public interface Repositor {
    public abstract void addClient();
    public abstract void findClient();
    public abstract void findAllClient();
    public abstract void modifyClient();
    public abstract void deleteClient();


    public abstract void addDepartment();
    public abstract void findDepartment();
    public abstract void findAllDepartments();
    public abstract void modifyDepartment();
    public abstract void deleteDepartment();

    public abstract void addEmployee();
    public abstract void findEmployee();
    public abstract void findAllEmployees();
    public abstract void modifyEmployee();
    public abstract void deleteEmployee();

    public abstract void addProject();
    public abstract void findProject();
    public abstract void findAllProjects();
    public abstract void modifyProject();
    public abstract void deleteProject();
}
