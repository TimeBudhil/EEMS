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
    private Connection connection;

    // constructor initializes the connection
    private DBConnection() throws SQLException {
        this.connection = DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // static method that creates/returns the singleton
    public static DBConnection getInstance() throws SQLException {
        if (instance == null) {
            instance = new DBConnection(); // constructor is called here
        }
        return instance;
    }

    // static method to get the connection directly
    public static Connection getConnection() throws SQLException {
        return getInstance().connection;
    }
    public static void main(String[] args) throws Exception {
        Connection conn = DBConnection.getConnection();
        System.out.println(conn);
    }
}