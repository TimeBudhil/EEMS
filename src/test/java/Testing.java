import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import com.mpp.eems.Domain.Project;
import com.mpp.eems.Domain.Status;
import com.mpp.eems.Repository.DatabaseInitializer;
import com.mpp.eems.Repository.ProjectRepository;

public class Testing {
    public static void main(String[] args) {
        //initialize repository
        ProjectRepository projectRepo = new ProjectRepository();
        // initialize schema first
        try {
            DatabaseInitializer.initialize();
            

            Project newProject = new Project(
                0,
                "Website Redesign",
                "Redesign the company site",
                LocalDate.of(2026, 1, 15),
                LocalDate.of(2026, 6, 30),
                75000.00,
                Status.ACTIVE,
                new ArrayList<>(),
                new ArrayList<>()
            );

            Project savedProject = projectRepo.addProject(newProject);
            System.out.println("Created project with id: " + savedProject.getId());
        } catch (IOException e) {
            System.err.println(e.getLocalizedMessage());
        } catch (SQLException e){
            System.err.println(e.getLocalizedMessage());
        }

    }
}
