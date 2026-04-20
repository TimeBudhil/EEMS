package Domain;

import java.util.List;

public class Client {
    private int id;
    private String name;
    private String industry;
    private String primaryContactName;
    private String primaryContactPhone;
    private String primaryContactEmail;

    //associations
    private List<Project> projects; //requests
}
