package Repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class DBConnection {

    private static final String URL = "jdbc:postgresql://localhost:5432/your_db";
    private static final String USER = "user";
    private static final String PASSWORD = "password";

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
}