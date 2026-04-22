package com.mpp.eems.Services;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mpp.eems.Domain.Client;
import com.mpp.eems.Domain.Project;
import com.mpp.eems.Repository.ClientRepository;

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



    //lists all clients where their projects have currentDate - project.deadline < daysUntilDeadline
    public static List<Client> findClientsByUpcomingProjectDeadline(int daysUntilDeadline){
        //Get Clients[] using repository
        List<Project> projects = new ArrayList<>();  //TEMPORARY 
        List<Client> clients = new ArrayList<>(); //TEMPORARY WHILE REPO LAYER IS GETTING WORKED ON
        
        // return Arrays.stream(projects)
        //     .filter(p -> LocalDate.now() - p.getDeadline() < daysUntilDeadline)
        //     .flatMap(p -> p.getClients())
        //     .distinct().
        return clients;
    }

}
