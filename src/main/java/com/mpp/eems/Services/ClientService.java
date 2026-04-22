package com.mpp.eems.Services;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import com.mpp.eems.Domain.Client;
import com.mpp.eems.Repository.ClientProjectRepository;
import com.mpp.eems.Repository.ClientRepository;
import com.mpp.eems.Repository.ProjectRepository;

public class ClientService  extends Services{

    //repo for creating things
   ClientRepository clientrepo = new ClientRepository();

   /*
    CRUD OPERAITONS in service
    
    */
    public Client addClient(Client client){
        Client addedClient = null;
        //verify client data

        try {
            addedClient = clientrepo.addClient(client);
        } catch (SQLException e) {
            System.err.println(e.getLocalizedMessage());
        }

        return addedClient;
    }

    public Client findClientByID(int id){
        return clientrepo.findClient(id);
    }

    public void updateClient(Client client){
        clientrepo.modifyClient(client);
    }

    public void deleteClient(Client client){
        clientrepo.deleteClient(client.getId());
    }

    public List<Client> findAllClients(){
        return clientrepo.findAllClient();
    }


    //lists all clients where their projects have currentDate - project.deadline < daysUntilDeadline
    public List<Client> findClientsByUpcomingProjectDeadline(int daysUntilDeadline){
        //Get Clients[] using repository
        List<Client> clients = findAllClients();
        ClientProjectRepository cpr = new ClientProjectRepository();
        ProjectRepository pr = new ProjectRepository();
        return clients.stream()
            .filter(c -> cpr.findProjectIdsByClient(c.getId()).stream()
                        .anyMatch(p -> 
                            {
                                try {
                                    return pr.findProjectById(p)
                                    .getEndDate()
                                    .isAfter(LocalDate.now().minusDays(daysUntilDeadline));
                                } catch (SQLException e) {
                                    // TODO Auto-generated catch block
                                    e.printStackTrace();
                                }
                                
                                //in case it didn't work out
                                return false;
                            }))
            .toList();
    }

}
