package com.mpp.eems.Controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.mpp.eems.Domain.Project;
import com.mpp.eems.Services.ProjectService;
import com.sun.net.httpserver.HttpExchange;

public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    // ── CRUD ──────────────────────────────────────────────────────────────────

    public List<Project> getAllProjects() {
        return projectService.getAllProjects();
    }

    public Project getProjectById(int id) throws SQLException {
        return projectService.getProjectById(id);
    }

    public Project createProject(Project project) throws SQLException {
        return projectService.createProject(project);
    }

    public void updateProject(Project project) throws SQLException {
        projectService.updateProject(project);
    }

    public void deleteProject(int id) throws SQLException {
        projectService.deleteProject(id);
    }

    // ── Lookups ───────────────────────────────────────────────────────────────

    public List<Project> getProjectsByEmployee(int employeeId) {
        return projectService.getProjectsByEmployee(employeeId);
    }

    public List<Project> getProjectsByDepartment(int departmentId) {
        return projectService.getProjectsByDepartment(departmentId);
    }

    // ── Client links ──────────────────────────────────────────────────────────

    public void linkClient(int projectId, int clientId) throws SQLException {
        projectService.linkClient(projectId, clientId);
    }

    public void unlinkClient(int projectId, int clientId) throws SQLException {
        projectService.unlinkClient(projectId, clientId);
    }

    public List<Integer> getClientIdsForProject(int projectId) {
        return projectService.getClientIdsForProject(projectId);
    }

    // ── Department links ──────────────────────────────────────────────────────

    public void linkDepartment(int projectId, int departmentId) throws SQLException {
        projectService.linkDepartment(projectId, departmentId);
    }

    public void unlinkDepartment(int projectId, int departmentId) throws SQLException {
        projectService.unlinkDepartment(projectId, departmentId);
    }

    public List<Integer> getDepartmentIdsForProject(int projectId) {
        return projectService.getDepartmentIdsForProject(projectId);
    }

    public void handle(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod();
        String path   = exchange.getRequestURI().getPath();

        try {
            String[] segments = path.replaceAll("^/+", "").split("/");
            boolean hasId  = segments.length >= 2 && !segments[1].isEmpty();
            boolean hasSub = segments.length >= 3;
            int id = hasId ? Integer.parseInt(segments[1]) : -1;
            String sub = hasSub ? segments[2] : "";

            if (!hasId) {
                switch (method) {
                    case "GET"  -> HttpUtils.sendJson(exchange, 200, projectService.getAllProjects());
                    case "POST" -> {
                        Project body = HttpUtils.GSON.fromJson(HttpUtils.readBody(exchange), Project.class);
                        HttpUtils.sendJson(exchange, 201, projectService.createProject(body));
                    }
                    default -> HttpUtils.sendStatus(exchange, 405);
                }
            } else if (!hasSub) {
                switch (method) {
                    case "GET" -> {
                        Project p = projectService.getProjectById(id);
                        if (p == null) HttpUtils.sendStatus(exchange, 404);
                        else HttpUtils.sendJson(exchange, 200, p);
                    }
                    case "PUT" -> {
                        Project body = HttpUtils.GSON.fromJson(HttpUtils.readBody(exchange), Project.class);
                        projectService.updateProject(body);
                        HttpUtils.sendStatus(exchange, 204);
                    }
                    case "DELETE" -> {
                        projectService.deleteProject(id);
                        HttpUtils.sendStatus(exchange, 204);
                    }
                    default -> HttpUtils.sendStatus(exchange, 405);
                }
            } else {
                switch (sub) {
                    case "clients"     -> handleClients(exchange, method, id);
                    case "departments" -> handleDepartments(exchange, method, id);
                    default            -> HttpUtils.sendStatus(exchange, 404);
                }
            }

        } catch (NumberFormatException e) {
            HttpUtils.sendJson(exchange, 400, Map.of("error", "Invalid ID format"));
        } catch (Exception e) {
            HttpUtils.sendJson(exchange, 500, Map.of("error", e.getMessage()));
        }
    }

    private void handleClients(HttpExchange exchange, String method, int projectId) throws Exception {
        switch (method) {
            case "GET" -> HttpUtils.sendJson(exchange, 200,
                    projectService.getClientIdsForProject(projectId));
            case "POST" -> {
                Map body = HttpUtils.GSON.fromJson(HttpUtils.readBody(exchange), Map.class);
                int clientId = ((Double) body.get("clientId")).intValue();
                projectService.linkClient(projectId, clientId);
                HttpUtils.sendStatus(exchange, 204);
            }
            case "DELETE" -> {
                int clientId = HttpUtils.queryParam(exchange, "clientId");
                if (clientId == -1) { HttpUtils.sendStatus(exchange, 400); return; }
                projectService.unlinkClient(projectId, clientId);
                HttpUtils.sendStatus(exchange, 204);
            }
            default -> HttpUtils.sendStatus(exchange, 405);
        }
    }

    private void handleDepartments(HttpExchange exchange, String method, int projectId) throws Exception {
        switch (method) {
            case "GET" -> HttpUtils.sendJson(exchange, 200,
                    projectService.getDepartmentIdsForProject(projectId));
            case "POST" -> {
                Map body = HttpUtils.GSON.fromJson(HttpUtils.readBody(exchange), Map.class);
                int deptId = ((Double) body.get("departmentId")).intValue();
                projectService.linkDepartment(projectId, deptId);
                HttpUtils.sendStatus(exchange, 204);
            }
            case "DELETE" -> {
                int deptId = HttpUtils.queryParam(exchange, "departmentId");
                if (deptId == -1) { HttpUtils.sendStatus(exchange, 400); return; }
                projectService.unlinkDepartment(projectId, deptId);
                HttpUtils.sendStatus(exchange, 204);
            }
            default -> HttpUtils.sendStatus(exchange, 405);
        }
    }
}