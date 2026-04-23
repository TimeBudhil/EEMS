package com.mpp.eems.Services;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.mpp.eems.Domain.Client;
import com.mpp.eems.Domain.Project;
import com.mpp.eems.Repository.ClientProjectRepository;
import com.mpp.eems.Repository.ClientRepository;
import com.mpp.eems.Repository.ProjectRepository;

public class ClientService {

    private final ClientRepository clientRepository = new ClientRepository();
    private final ClientProjectRepository clientProjectRepository = new ClientProjectRepository();
    private final ProjectRepository projectRepository = new ProjectRepository();
    


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

    // Task 3: High-Value Client Identification
    public List<Client> findClientsByUpcomingProjectDeadline(int daysUntilDeadline) {
        LocalDate cutoff = LocalDate.now().plusDays(daysUntilDeadline);
        List<Client> allClients = clientRepository.findAllClient();
        List<Client> result = new ArrayList<>();

        for (Client client : allClients) {
            List<Integer> projectIds = clientProjectRepository.findProjectIdsByClient(client.getId());
            for (int projectId : projectIds) {
                try {
                    Project project = projectRepository.findProjectById(projectId);
                    LocalDate today = LocalDate.now();

                    if (project != null) {
                        LocalDate end = project.getEndDate();

                        if ((end.isEqual(today) || end.isAfter(today)) &&
                            (end.isEqual(cutoff) || end.isBefore(cutoff))) {
                            
                            result.add(client);
                            break;
                        }
                    }
                } catch (SQLException e) {
                    System.err.println(e.getMessage());
                }
            }
        }
        return result;
    }
}
