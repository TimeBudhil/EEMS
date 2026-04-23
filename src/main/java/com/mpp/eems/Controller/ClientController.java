package com.mpp.eems.Controller;

import com.mpp.eems.Domain.Client;
import com.mpp.eems.Services.ClientService;

import java.sql.SQLException;
import java.util.List;

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
}
