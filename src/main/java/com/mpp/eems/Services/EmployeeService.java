package com.mpp.eems.Services;

import com.mpp.eems.Domain.Employee;
import com.mpp.eems.Repository.EmployeeRepository;

import java.util.List;

public class EmployeeService extends Services{


    private final EmployeeRepository employeeRepo;


    public EmployeeService() {
        this.employeeRepo = new EmployeeRepository();
    }

    // =========================
    // CREATE EMPLOYEE
    // =========================
    public Employee createEmployee(Employee emp) {

        if (emp.getDepartment() == null) {
            throw new RuntimeException("Department is required");
        }

        return employeeRepo.add(emp);
    }

    // =========================
    // GET EMPLOYEE BY ID
    // =========================
    public Employee getEmployeeById(int id) {
        return employeeRepo.findById(id);
    }

    // =========================
    // GET ALL EMPLOYEES
    // =========================
    public List<Employee> getAllEmployees() {
        return employeeRepo.findAll();
    }

    // =========================
    // UPDATE EMPLOYEE
    // =========================
    public void updateEmployee(Employee emp) {

        if (emp.getId() <= 0) {
            throw new RuntimeException("Invalid employee ID");
        }

        employeeRepo.update(emp);
    }

    // =========================
    // DELETE EMPLOYEE
    // =========================
    public void deleteEmployee(int id) {

        if (id <= 0) {
            throw new RuntimeException("Invalid employee ID");
        }

        employeeRepo.delete(id);
    }







    //updates employeeID and department record
    public static void transferEmployeeToDepartment(int employeeId, int newDepartmentId){

        // Perform a transaction to update an employee's department ID. This requires interacting with
        // both the Employee and Department records. Implement validation to ensure the transfer is
        // possible before committing the changes.

    }

}
