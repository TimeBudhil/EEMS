package com.mpp.eems.Repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import io.github.cdimascio.dotenv.Dotenv;

/**
 * connect to the db using this class. 
 * singelton, so only one connectin can exist at a time. 
 * certain cases like — if the instance already exists or if the connectino exists is also taken care of here
 */
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
        if (instance == null || instance.connection.isClosed()) {
            instance = new DBConnection();
        }
        return instance;
    }

    // static method to get the connection directly
    static Connection getConnection() throws SQLException {
        if (instance == null || instance.connection.isClosed()) {
            instance = new DBConnection(); // opens a fresh connection
        }
        return instance.connection;
    }

    //testing purpose main 
    public static void main(String[] args) throws Exception {
        Connection conn = DBConnection.getConnection();
        System.out.println(conn);
    }
}