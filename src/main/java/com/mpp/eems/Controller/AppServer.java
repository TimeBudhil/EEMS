package com.mpp.eems.Controller;

import java.net.InetSocketAddress;

import com.mpp.eems.Services.ClientService;
import com.mpp.eems.Services.DepartmentService;
import com.mpp.eems.Services.EmployeeService;
import com.mpp.eems.Services.ProjectService;
import com.sun.net.httpserver.HttpServer;

public class AppServer {
    public static void start() throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

        // ── Wire up services (inject your real DB/repository instances here) ──
        EmployeeService employeeService     = new EmployeeService();

        ClientService clientService         = new ClientService();

        DepartmentService departmentService = new DepartmentService();

        ProjectService projectService       = new ProjectService();

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