package com.mpp.eems.Services;

import com.mpp.eems.Repository.ClientProjectRepository;
import com.mpp.eems.Repository.ClientRepository;
import com.mpp.eems.Repository.DepartmentRepository;
import com.mpp.eems.Repository.EmployeeProjectRepository;
import com.mpp.eems.Repository.EmployeeRepository;
import com.mpp.eems.Repository.ProjectDepartmentRepository;
import com.mpp.eems.Repository.ProjectRepository;

/**
 * parent service, contains all repositories to be inherited by child classes
 * so that child classes can use repository functionality without having to update their fields!
 */
public class Services {
    protected final ProjectRepository projectRepository = new ProjectRepository();
    protected final ClientProjectRepository clientProjectRepository = new ClientProjectRepository();
    protected final ProjectDepartmentRepository projectDepartmentRepository = new ProjectDepartmentRepository();
    protected final EmployeeProjectRepository employeeProjectRepository = new EmployeeProjectRepository();
    protected final EmployeeRepository employeeRepository = new EmployeeRepository();
    protected final EmployeeService employeeService = new EmployeeService();
    protected  final DepartmentRepository departmentRepository = new DepartmentRepository();
    protected  final ClientRepository clientRepository = new ClientRepository();
}
