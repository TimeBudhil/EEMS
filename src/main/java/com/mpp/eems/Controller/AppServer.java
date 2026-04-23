package com.mpp.eems.Controller;

import com.sun.net.httpserver.HttpServer;
import java.net.InetSocketAddress;
import com.sun.net.httpserver.HttpServer;

// Import your repositories and services here — adjust to match your actual constructors
import com.mpp.eems.Repository.*;
import com.mpp.eems.Services.*;

public class AppServer {
    public static void start() throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

        // ── Wire up services (inject your real DB/repository instances here) ──
        EmployeeService employeeService     = new EmployeeService(
                new EmployeeRepository(), new EmployeeProjectRepository(),
                new ProjectRepository(),  new DepartmentRepository());

        ClientService clientService         = new ClientService(
                new ClientRepository(), new ClientProjectRepository());

        DepartmentService departmentService = new DepartmentService(
                new DepartmentRepository(), new ProjectDepartmentRepository());

        ProjectService projectService       = new ProjectService(
                new ProjectRepository(), new ClientProjectRepository(),
                new ProjectDepartmentRepository());

        // ── Wire up controllers ───────────────────────────────────────────────
        EmployeeController  emp  = new EmployeeController(employeeService);
        ClientController    cli  = new ClientController(clientService);
        DepartmentController dep = new DepartmentController(departmentService);
        ProjectController   proj = new ProjectController(projectService);

        // ── Register routes ───────────────────────────────────────────────────
        server.createContext("/employees",  emp::handle);
        server.createContext("/clients",   cli::handle);
        server.createContext("/departments", dep::handle);
        server.createContext("/projects",    proj::handle);

        server.setExecutor(java.util.concurrent.Executors.newFixedThreadPool(4));
        server.start();
        System.out.println("Server running at http://localhost:8080");
    }
}