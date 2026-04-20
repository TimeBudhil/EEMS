package Services;

import Domain.Client;

public class ClientService  extends Services{

    //lists all clients where their projects have currentDate - project.deadline < daysUntilDeadline
    public static Client[] findClientsByUpcomingProjectDeadline(int daysUntilDeadline){

        return new Client[0];
    }

}
