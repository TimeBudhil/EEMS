package Services;

import Domain.Client;
import Domain.Project;

public class ClientService  extends Services{

    //lists all clients where their projects have currentDate - project.deadline < daysUntilDeadline
    public static Client[] findClientsByUpcomingProjectDeadline(int daysUntilDeadline){
        //Get Clients[] using repository
        Project[] projects = new Project[5];  //TEMPORARY 
        Client[] clients = new Client[5]; //TEMPORARY WHILE REPO LAYER IS GETTING WORKED ON
        
        // return Arrays.stream(projects)
        //     .filter(p -> LocalDate.now() - p.getDeadline() < daysUntilDeadline)
        //     .flatMap(p -> p.getClients())
        //     .distinct()
        //     .collect(Collectors.toList());
        return clients;
    }

}
