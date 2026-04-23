package com.mpp.eems.Services;

import java.util.List;

import com.mpp.eems.Domain.Employee;
import com.mpp.eems.Repository.EmployeeRepository;

public class EmployeeService extends Services {


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

}
