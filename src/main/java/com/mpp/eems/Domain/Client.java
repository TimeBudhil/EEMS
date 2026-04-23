package com.mpp.eems.Domain;

import java.util.List;

/**
 * client maps directly to db schema
 */
public class Client {
    private int id;
    private String name;
    private String industry;
    private String primaryContactName;
    private String primaryContactPhone;
    private String primaryContactEmail;

    //associations
    private List<Project> projects; //requests

    public Client(int id, String name, String industry, String primaryContactName, String primaryContactPhone, String primaryContactEmail, List<Project> projects) {
        this.id = id;
        this.name = name;
        this.industry = industry;
        this.primaryContactName = primaryContactName;
        this.primaryContactPhone = primaryContactPhone;
        this.primaryContactEmail = primaryContactEmail;
        this.projects = projects;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getIndustry() {
        return industry;
    }

    public String getPrimaryContactName() {
        return primaryContactName;
    }

    public String getPrimaryContactPhone() {
        return primaryContactPhone;
    }

    public String getPrimaryContactEmail() {
        return primaryContactEmail;
    }

    public List<Project> getProjects() {
        return projects;
    }

    
}
