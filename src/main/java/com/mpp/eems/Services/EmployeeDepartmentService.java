package com.mpp.eems.Services;

import com.mpp.eems.Domain.Employee;
import com.mpp.eems.Repository.DepartmentRepository;
import com.mpp.eems.Repository.EmployeeRepository;

public class EmployeeDepartmentService {


    private final EmployeeRepository employeeRepo;
    private final DepartmentRepository departmentRepo;


    public EmployeeDepartmentService() {
        this.employeeRepo = new EmployeeRepository();
        this.departmentRepo = new DepartmentRepository();
    }

    //updates employeeID and department record
    public void transferEmployeeToDepartment(int employeeId, int newDepartmentId){
        // Perform a transaction to update an employee's department ID. This requires interacting with
        // both the Employee and Department records. Implement validation to ensure the transfer is
        // possible before committing the changes.



        //update employee to have new department 
        Employee e = employeeRepo.findById(employeeId);
        if (e == null) throw new IllegalArgumentException("Employee not found: " + employeeId);

        // update department to have different employees
        if (e.getDepartment().getId() == newDepartmentId) return; // already there, no-op

        //update employee
        Employee newE = new Employee(employeeId, e.getFirstName(), e.getLastName(), e.getTitle(), e.getHiredDate(), e.getSalary(), departmentRepo.findDepartmentById(newDepartmentId), e.getProjects());
        employeeRepo.update(newE);

    }
    
}
