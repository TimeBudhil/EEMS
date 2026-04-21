package com.mpp.eems.Controller;

import com.mpp.eems.Domain.Employee;
import com.mpp.eems.Services.EmployeeService;

import java.util.List;

public class EmployeeController extends Controller{


        private final EmployeeService employeeService;

        public EmployeeController() {
            this.employeeService = new EmployeeService();
        }

        // =========================
        // CREATE EMPLOYEE
        // =========================
        public Employee createEmployee(Employee emp) {
            return employeeService.createEmployee(emp);
        }

        // =========================
        // GET EMPLOYEE BY ID
        // =========================
        public Employee getEmployeeById(int id) {
            return employeeService.getEmployeeById(id);
        }

        // =========================
        // GET ALL EMPLOYEES
        // =========================
        public List<Employee> getAllEmployees() {
            return employeeService.getAllEmployees();
        }

        // =========================
        // UPDATE EMPLOYEE
        // =========================
        public void updateEmployee(Employee emp) {
            employeeService.updateEmployee(emp);
        }

        // =========================
        // DELETE EMPLOYEE
        // =========================
        public void deleteEmployee(int id) {
            employeeService.deleteEmployee(id);
        }
    }

