package com.mpp.eems.Services;

import java.sql.SQLException;
import java.util.List;

import com.mpp.eems.Domain.Employee;
import com.mpp.eems.Domain.Project;
import com.mpp.eems.Repository.DepartmentRepository;
import com.mpp.eems.Repository.EmployeeProjectRepository;
import com.mpp.eems.Repository.EmployeeRepository;
import com.mpp.eems.Repository.ProjectRepository;

/**
 * all CRUD oeprations and all services related to employee
 * uses employee, employee_project, project and department repositories to establish links, create, and find!
 */
public class EmployeeService extends Services {
    protected final EmployeeRepository employeeRepository = new EmployeeRepository();
    protected  final DepartmentRepository departmentRepository = new DepartmentRepository();
    protected final ProjectRepository projectRepository = new ProjectRepository();
    protected final EmployeeProjectRepository employeeProjectRepository = new EmployeeProjectRepository();
     // ── CRUD ──────────────────────────────────────────────────────────────────

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public Employee getEmployeeById(int id) {
        return employeeRepository.findById(id);
    }

    public Employee createEmployee(Employee employee) {
        return employeeRepository.add(employee);
    }

    public void updateEmployee(Employee employee) {
        employeeRepository.update(employee);
    }

    public void deleteEmployee(int id) {
        employeeRepository.delete(id);
    }

    // ── Project assignments ───────────────────────────────────────────────────

    /**
     * Returns all projects the employee is assigned to.
     */
    public List<Project> getProjectsForEmployee(int employeeId) {
        return projectRepository.findProjectsByEmployee(employeeId);
    }

    /**
     * Assigns an employee to a project with allocated hours.
     * Uses upsert — safe to call again to update hours.
     */
    public void assignToProject(int employeeId, int projectId, double hoursAllocated) {
        employeeProjectRepository.assignProjectToEmployee(employeeId, projectId, hoursAllocated);
    }

    /**
     * Removes an employee from a project.
     */
    public void removeFromProject(int employeeId, int projectId) throws SQLException {
        employeeProjectRepository.unlinkEmployee(projectId, employeeId);
    }

    /**
     * Returns the percentage of an employee's total allocated hours
     * dedicated to a specific project.
     */
    public double getProjectHoursPercentage(int employeeId, int projectId) {
        return employeeProjectRepository.calculateEmployeePercentageHours(employeeId, projectId);
    }

    public void transferEmployeeToDepartment(int employeeId, int newDepartmentId){
        // Perform a transaction to update an employee's department ID. This requires interacting with
        // both the Employee and Department records. Implement validation to ensure the transfer is
        // possible before committing the changes.



        //update employee to have new department
        Employee e = employeeRepository.findById(employeeId);
        if (e == null) throw new IllegalArgumentException("Employee not found: " + employeeId);

        // update department to have different employees
        if (e.getDepartment().getId() == newDepartmentId) return; // already there, no-op

        //update employee
        Employee newE = new Employee(employeeId, e.getFirstName(), e.getLastName(), e.getTitle(), e.getHiredDate(), e.getSalary(), departmentRepository.findDepartmentById(newDepartmentId), e.getProjects());
        employeeRepository.update(newE);

    }
}
