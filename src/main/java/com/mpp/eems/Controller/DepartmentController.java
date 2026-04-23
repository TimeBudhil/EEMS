package com.mpp.eems.Controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.mpp.eems.Domain.Department;
import com.mpp.eems.Services.DepartmentService;
import com.sun.net.httpserver.HttpExchange;

public class DepartmentController {

    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    // ── CRUD ──────────────────────────────────────────────────────────────────

    public List<Department> getAllDepartments() {
        return departmentService.getAllDepartments();
    }

    public Department getDepartmentById(int id) {
        return departmentService.getDepartmentById(id);
    }

    public Department createDepartment(Department department) throws SQLException {
        return departmentService.createDepartment(department);
    }

    public void updateDepartment(Department department) throws SQLException {
        departmentService.updateDepartment(department);
    }

    public void deleteDepartment(int id) throws SQLException {
        departmentService.deleteDepartment(id);
    }

    // ── Lookups ───────────────────────────────────────────────────────────────

    public Department getDepartmentByEmployee(int employeeId) throws SQLException {
        return departmentService.getDepartmentByEmployee(employeeId);
    }

    public List<Department> getDepartmentsByProject(int projectId) throws SQLException {
        return departmentService.getDepartmentsByProject(projectId);
    }

    // ── Project links ─────────────────────────────────────────────────────────

    public void linkToProject(int departmentId, int projectId) throws SQLException {
        departmentService.linkToProject(departmentId, projectId);
    }

    public void unlinkFromProject(int departmentId, int projectId) throws SQLException {
        departmentService.unlinkFromProject(departmentId, projectId);
    }

    public List<Integer> getProjectIdsForDepartment(int departmentId) {
        return departmentService.getProjectIdsForDepartment(departmentId);
    }

    public void handle(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod();
        String path   = exchange.getRequestURI().getPath();

        try {
            String[] segments = path.replaceAll("^/+", "").split("/");
            boolean hasId  = segments.length >= 2 && !segments[1].isEmpty();
            boolean hasSub = segments.length >= 3;
            int id = hasId ? Integer.parseInt(segments[1]) : -1;

            if (!hasId) {
                switch (method) {
                    case "GET"  -> HttpUtils.sendJson(exchange, 200, departmentService.getAllDepartments());
                    case "POST" -> {
                        Department body = HttpUtils.GSON.fromJson(HttpUtils.readBody(exchange), Department.class);
                        HttpUtils.sendJson(exchange, 201, departmentService.createDepartment(body));
                    }
                    default -> HttpUtils.sendStatus(exchange, 405);
                }
            } else if (!hasSub) {
                switch (method) {
                    case "GET" -> {
                        Department d = departmentService.getDepartmentById(id);
                        if (d == null) HttpUtils.sendStatus(exchange, 404);
                        else HttpUtils.sendJson(exchange, 200, d);
                    }
                    case "PUT" -> {
                        Department body = HttpUtils.GSON.fromJson(HttpUtils.readBody(exchange), Department.class);
                        departmentService.updateDepartment(body);
                        HttpUtils.sendStatus(exchange, 204);
                    }
                    case "DELETE" -> {
                        departmentService.deleteDepartment(id);
                        HttpUtils.sendStatus(exchange, 204);
                    }
                    default -> HttpUtils.sendStatus(exchange, 405);
                }
            } else {
                // /departments/{id}/projects
                switch (method) {
                    case "GET" -> HttpUtils.sendJson(exchange, 200,
                            departmentService.getProjectIdsForDepartment(id));
                    case "POST" -> {
                        Map body = HttpUtils.GSON.fromJson(HttpUtils.readBody(exchange), Map.class);
                        int projectId = ((Double) body.get("projectId")).intValue();
                        departmentService.linkToProject(id, projectId);
                        HttpUtils.sendStatus(exchange, 204);
                    }
                    case "DELETE" -> {
                        int projectId = HttpUtils.queryParam(exchange, "projectId");
                        if (projectId == -1) { HttpUtils.sendStatus(exchange, 400); return; }
                        departmentService.unlinkFromProject(id, projectId);
                        HttpUtils.sendStatus(exchange, 204);
                    }
                    default -> HttpUtils.sendStatus(exchange, 405);
                }
            }

        } catch (NumberFormatException e) {
            HttpUtils.sendJson(exchange, 400, Map.of("error", "Invalid ID format"));
        } catch (Exception e) {
            HttpUtils.sendJson(exchange, 500, Map.of("error", e.getMessage()));
        }
    }
}