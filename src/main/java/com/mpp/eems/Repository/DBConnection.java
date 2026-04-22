package com.mpp.eems.Repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import io.github.cdimascio.dotenv.Dotenv;

public final class DBConnection {

    static Dotenv dotenv = Dotenv.load();

    private static final String URL = dotenv.get("DB_URL");
    private static final String USER = dotenv.get("DB_USER");
    private static final String PASSWORD = dotenv.get("DB_PASSWORD");

    // singleton variable
    private static DBConnection instance;
    private final Connection connection;

    // constructor initializes the connection
    private DBConnection() throws SQLException {
        this.connection = DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // static method that creates/returns the singleton
    private static DBConnection getInstance() throws SQLException {
        //only create a new instance if it doesn' texist..
        if (instance == null) {
            instance = new DBConnection(); // constructor is called here
        }
        return instance;
    }

    // static method to get the connection directly
    static Connection getConnection() throws SQLException {
        //gets the connectino of the instance
        return getInstance().connection;
    }

    //testing purpose main 
    public static void main(String[] args) throws Exception {
        Connection conn = DBConnection.getConnection();
        System.out.println(conn);
    }
}