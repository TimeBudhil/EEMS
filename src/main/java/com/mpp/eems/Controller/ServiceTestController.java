package com.mpp.eems.Controller;

import java.util.List;

import com.mpp.eems.Domain.Client;
import com.mpp.eems.Domain.Employee;
import com.mpp.eems.Domain.Project;
import com.mpp.eems.Repository.ClientProjectRepository;
import com.mpp.eems.Repository.ClientRepository;
import com.mpp.eems.Repository.DepartmentRepository;
import com.mpp.eems.Repository.EmployeeProjectRepository;
import com.mpp.eems.Repository.EmployeeRepository;
import com.mpp.eems.Repository.ProjectDepartmentRepository;
import com.mpp.eems.Repository.ProjectRepository;
import com.mpp.eems.Services.ClientService;
import com.mpp.eems.Services.DepartmentService;
import com.mpp.eems.Services.EmployeeService;
import com.mpp.eems.Services.ProjectService;

public class ServiceTestController {

    public static void main(String[] args) throws Exception {

        // ── Wire up repositories ───────────────────────────────────────────
        DepartmentRepository    deptRepo     = new DepartmentRepository();
        EmployeeRepository      empRepo      = new EmployeeRepository();
        ProjectRepository       projRepo     = new ProjectRepository();
        ClientRepository        clientRepo   = new ClientRepository();
        EmployeeProjectRepository empProjRepo = new EmployeeProjectRepository();
        ClientProjectRepository   cliProjRepo = new ClientProjectRepository();
        ProjectDepartmentRepository projDeptRepo = new ProjectDepartmentRepository();

        // ── Wire up services ───────────────────────────────────────────────
        EmployeeService employeeService = new EmployeeService();

        ProjectService projectService = new ProjectService();

        ClientService clientService = new ClientService();

        DepartmentService departmentService = new DepartmentService();

        // ── Task 1: HR Cost Calculation ────────────────────────────────────
        System.out.println("\n── Task 1: HR Cost Calculation ──────────────────");
        int[] testProjectIds = {1, 2, 4}; // Platform Redesign, Mobile App, Data Pipeline
        for (int pid : testProjectIds) {
            double cost = projectService.calculateProjectHRCost(pid);
            Project p = projectService.getProjectById(pid);
            System.out.printf("  %-30s → $%,.2f%n", p.getName(), cost);
        }

        // ── Task 2: Department Project Report ─────────────────────────────
        System.out.println("\n── Task 2: Department Project Report ────────────");
        System.out.println("  Engineering — sorted by total_budget:");
        List<Project> engProjects = projectService.getProjectsByDepartment(1, "total_budget");
        engProjects.forEach(p -> System.out.printf("    %-30s $%,.2f  %s%n",
            p.getName(), p.getTotalBudget(), p.getStatus()));

        System.out.println("  Engineering — sorted by end_date:");
        List<Project> engByDate = projectService.getProjectsByDepartment(1, "end_date");
        engByDate.forEach(p -> System.out.printf("    %-30s %s%n",
            p.getName(), p.getEndDate()));

        // ── Task 3: High-Value Client Identification ───────────────────────
        System.out.println("\n── Task 3: Clients with deadline within N days ───");
        int[] deadlineWindows = {1, 180, 10000};
        for (int days : deadlineWindows) {
            List<Client> clients = clientService.findClientsByUpcomingProjectDeadline(days);
            System.out.printf("  Within %3d days: %s%n", days,
                clients.stream().map(Client::getName).toList());
        }

        // ── Task 4: Employee Transfer ──────────────────────────────────────
        System.out.println("\n── Task 4: Employee Transfer ─────────────────────");
        Employee alice = employeeService.getEmployeeById(1);
        System.out.println("  Before: " + alice.getFirstName() + " → " + alice.getDepartment().getName());

        employeeService.transferEmployeeToDepartment(1, 2); // move Alice to Marketing

        Employee aliceAfter = employeeService.getEmployeeById(1);
        System.out.println("  After:  " + aliceAfter.getFirstName() + " → " + aliceAfter.getDepartment().getName());

        // transfer back so data stays consistent
        employeeService.transferEmployeeToDepartment(1, 1);
        System.out.println("  Restored: " + employeeService.getEmployeeById(1).getDepartment().getName());

        System.out.println("\n✔ All service tests complete!");
        System.exit(0);
    }
}