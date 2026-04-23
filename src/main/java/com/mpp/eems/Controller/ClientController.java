package com.mpp.eems.Controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.mpp.eems.Domain.Client;
import com.mpp.eems.Services.ClientService;
import com.sun.net.httpserver.HttpExchange;

public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    // ── CRUD ──────────────────────────────────────────────────────────────────

    public List<Client> getAllClients() {
        return clientService.getAllClients();
    }

    public Client getClientById(int id) {
        return clientService.getClientById(id);
    }

    public Client createClient(Client client) throws SQLException {
        return clientService.createClient(client);
    }

    public void updateClient(Client client) {
        clientService.updateClient(client);
    }

    public void deleteClient(int id) {
        clientService.deleteClient(id);
    }

    // ── Project links ─────────────────────────────────────────────────────────

    public void linkToProject(int clientId, int projectId) throws SQLException {
        clientService.linkToProject(clientId, projectId);
    }

    public void unlinkFromProject(int clientId, int projectId) throws SQLException {
        clientService.unlinkFromProject(clientId, projectId);
    }

    public List<Integer> getProjectIdsForClient(int clientId) {
        return clientService.getProjectIdsForClient(clientId);
    }

    public List<Integer> getClientIdsForProject(int projectId) {
        return clientService.getClientIdsForProject(projectId);
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
                    case "GET"  -> HttpUtils.sendJson(exchange, 200, clientService.getAllClients());
                    case "POST" -> {
                        Client body = HttpUtils.GSON.fromJson(HttpUtils.readBody(exchange), Client.class);
                        HttpUtils.sendJson(exchange, 201, clientService.createClient(body));
                    }
                    default -> HttpUtils.sendStatus(exchange, 405);
                }
            } else if (!hasSub) {
                switch (method) {
                    case "GET" -> {
                        Client c = clientService.getClientById(id);
                        if (c == null) HttpUtils.sendStatus(exchange, 404);
                        else HttpUtils.sendJson(exchange, 200, c);
                    }
                    case "PUT" -> {
                        Client body = HttpUtils.GSON.fromJson(HttpUtils.readBody(exchange), Client.class);
                        clientService.updateClient(body);
                        HttpUtils.sendStatus(exchange, 204);
                    }
                    case "DELETE" -> {
                        clientService.deleteClient(id);
                        HttpUtils.sendStatus(exchange, 204);
                    }
                    default -> HttpUtils.sendStatus(exchange, 405);
                }
            } else {
                // /clients/{id}/projects
                switch (method) {
                    case "GET" -> HttpUtils.sendJson(exchange, 200,
                            clientService.getProjectIdsForClient(id));
                    case "POST" -> {
                        Map body = HttpUtils.GSON.fromJson(HttpUtils.readBody(exchange), Map.class);
                        int projectId = ((Double) body.get("projectId")).intValue();
                        clientService.linkToProject(id, projectId);
                        HttpUtils.sendStatus(exchange, 204);
                    }
                    case "DELETE" -> {
                        int projectId = HttpUtils.queryParam(exchange, "projectId");
                        if (projectId == -1) { HttpUtils.sendStatus(exchange, 400); return; }
                        clientService.unlinkFromProject(id, projectId);
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
