package com.mpp.eems.Services;

import java.util.ArrayList;
import java.util.List;

import com.mpp.eems.Domain.Client;
import com.mpp.eems.Domain.Project;

public class ClientService  extends Services{

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
