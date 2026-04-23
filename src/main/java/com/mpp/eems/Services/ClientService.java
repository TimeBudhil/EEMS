package com.mpp.eems.Services;

import com.mpp.eems.Domain.Client;
import com.mpp.eems.Repository.ClientProjectRepository;
import com.mpp.eems.Repository.ClientRepository;

import java.sql.SQLException;
import java.util.List;

public class ClientService {

    private final ClientRepository clientRepository;
    private final ClientProjectRepository clientProjectRepository;

    public ClientService(ClientRepository clientRepository,
                         ClientProjectRepository clientProjectRepository) {
        this.clientRepository = clientRepository;
        this.clientProjectRepository = clientProjectRepository;
    }

    // ── CRUD ──────────────────────────────────────────────────────────────────

    public List<Client> getAllClients() {
        return clientRepository.findAllClient();
    }

    public Client getClientById(int id) {
        return clientRepository.findClient(id);
    }

    public Client createClient(Client client) throws SQLException {
        return clientRepository.addClient(client);
    }

    public void updateClient(Client client) {
        clientRepository.modifyClient(client);
    }

    public void deleteClient(int id) {
        clientRepository.deleteClient(id);
    }

    // ── Project links (Client_Project join table) ─────────────────────────────

    /**
     * Links a client to a project.
     */
    public void linkToProject(int clientId, int projectId) throws SQLException {
        clientProjectRepository.linkClient(projectId, clientId);
    }

    /**
     * Removes a client from a project.
     */
    public void unlinkFromProject(int clientId, int projectId) throws SQLException {
        clientProjectRepository.unlinkClient(projectId, clientId);
    }

    /**
     * Returns all project IDs a client is linked to.
     */
    public List<Integer> getProjectIdsForClient(int clientId) {
        return clientProjectRepository.findProjectIdsByClient(clientId);
    }

    /**
     * Returns all client IDs linked to a project.
     */
    public List<Integer> getClientIdsForProject(int projectId) {
        return clientProjectRepository.findClientIdsByProject(projectId);
    }
}
