package com.mpp.eems.Controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.mpp.eems.Domain.Employee;
import com.mpp.eems.Domain.Project;
import com.mpp.eems.Services.EmployeeService;
import com.sun.net.httpserver.HttpExchange;

public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    // ── CRUD ──────────────────────────────────────────────────────────────────

    public List<Employee> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    public Employee getEmployeeById(int id) {
        return employeeService.getEmployeeById(id);
    }

    public Employee createEmployee(Employee employee) {
        return employeeService.createEmployee(employee);
    }

    public void updateEmployee(Employee employee) {
        employeeService.updateEmployee(employee);
    }

    public void deleteEmployee(int id) {
        employeeService.deleteEmployee(id);
    }

    // ── Project assignments ───────────────────────────────────────────────────

    public List<Project> getProjectsForEmployee(int employeeId) {
        return employeeService.getProjectsForEmployee(employeeId);
    }

    public void assignToProject(int employeeId, int projectId, double hoursAllocated) {
        employeeService.assignToProject(employeeId, projectId, hoursAllocated);
    }

    public void removeFromProject(int employeeId, int projectId) throws SQLException {
        employeeService.removeFromProject(employeeId, projectId);
    }

    public double getProjectHoursPercentage(int employeeId, int projectId) {
        return employeeService.getProjectHoursPercentage(employeeId, projectId);
    }

    public void transferEmployeeToDepartment(int employeeId, int newDepartmentId){
        employeeService.transferEmployeeToDepartment(employeeId,newDepartmentId);
    }

    public void handle(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod();
        String path   = exchange.getRequestURI().getPath(); // e.g. /employees/5/projects

        try {
            String[] segments = path.replaceAll("^/+", "").split("/");
            // segments[0] = "employees"
            // segments[1] = id  (optional)
            // segments[2] = sub-resource  (optional)

            boolean hasId  = segments.length >= 2 && !segments[1].isEmpty();
            boolean hasSub = segments.length >= 3;
            int id = hasId ? Integer.parseInt(segments[1]) : -1;
            String sub = hasSub ? segments[2] : "";

            if (!hasId) {
                // /employees
                switch (method) {
                    case "GET"  -> HttpUtils.sendJson(exchange, 200, employeeService.getAllEmployees());
                    case "POST" -> {
                        Employee body = HttpUtils.GSON.fromJson(HttpUtils.readBody(exchange), Employee.class);
                        HttpUtils.sendJson(exchange, 201, employeeService.createEmployee(body));
                    }
                    default -> HttpUtils.sendStatus(exchange, 405);
                }
            } else if (!hasSub) {
                // /employees/{id}
                switch (method) {
                    case "GET" -> {
                        Employee e = employeeService.getEmployeeById(id);
                        if (e == null) HttpUtils.sendStatus(exchange, 404);
                        else HttpUtils.sendJson(exchange, 200, e);
                    }
                    case "PUT" -> {
                        Employee body = HttpUtils.GSON.fromJson(HttpUtils.readBody(exchange), Employee.class);
                        employeeService.updateEmployee(body);
                        HttpUtils.sendStatus(exchange, 204);
                    }
                    case "DELETE" -> {
                        employeeService.deleteEmployee(id);
                        HttpUtils.sendStatus(exchange, 204);
                    }
                    default -> HttpUtils.sendStatus(exchange, 405);
                }
            } else {
                // /employees/{id}/projects  or  /employees/{id}/hours  or  /employees/{id}/department
                switch (sub) {
                    case "projects" -> handleProjects(exchange, method, id);
                    case "hours"    -> handleHours(exchange, method, id);
                    case "department" -> handleDepartment(exchange, method, id);
                    default -> HttpUtils.sendStatus(exchange, 404);
                }
            }

        } catch (NumberFormatException e) {
            HttpUtils.sendJson(exchange, 400, Map.of("error", "Invalid ID format"));
        } catch (Exception e) {
            HttpUtils.sendJson(exchange, 500, Map.of("error", e.getMessage()));
        }
    }

    private void handleProjects(HttpExchange exchange, String method, int employeeId) throws Exception {
        switch (method) {
            case "GET" -> {
                List<Project> projects = employeeService.getProjectsForEmployee(employeeId);
                HttpUtils.sendJson(exchange, 200, projects);
            }
            case "POST" -> {
                // body: { "projectId": 3, "hoursAllocated": 20.0 }
                Map body = HttpUtils.GSON.fromJson(HttpUtils.readBody(exchange), Map.class);
                int projectId = ((Double) body.get("projectId")).intValue();
                double hours  = (Double) body.get("hoursAllocated");
                employeeService.assignToProject(employeeId, projectId, hours);
                HttpUtils.sendStatus(exchange, 204);
            }
            case "DELETE" -> {
                int projectId = HttpUtils.queryParam(exchange, "projectId");
                if (projectId == -1) { HttpUtils.sendStatus(exchange, 400); return; }
                employeeService.removeFromProject(employeeId, projectId);
                HttpUtils.sendStatus(exchange, 204);
            }
            default -> HttpUtils.sendStatus(exchange, 405);
        }
    }

    private void handleHours(HttpExchange exchange, String method, int employeeId) throws IOException {
        if (!"GET".equals(method)) { HttpUtils.sendStatus(exchange, 405); return; }
        int projectId = HttpUtils.queryParam(exchange, "projectId");
        if (projectId == -1) { HttpUtils.sendStatus(exchange, 400); return; }
        double pct = employeeService.getProjectHoursPercentage(employeeId, projectId);
        HttpUtils.sendJson(exchange, 200, Map.of("percentage", pct));
    }

    private void handleDepartment(HttpExchange exchange, String method, int employeeId) throws IOException {
        if (!"PUT".equals(method)) { HttpUtils.sendStatus(exchange, 405); return; }
        // body: { "departmentId": 2 }
        Map body = HttpUtils.GSON.fromJson(HttpUtils.readBody(exchange), Map.class);
        int deptId = ((Double) body.get("departmentId")).intValue();
        employeeService.transferEmployeeToDepartment(employeeId, deptId);
        HttpUtils.sendStatus(exchange, 204);
    }
}
