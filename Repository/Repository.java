package Repository;

import java.sql.Connection;
import java.sql.SQLException;

public class Repository {
    Connection connection;

    public Repository() {
        try {
            connection = DBConnection.getConnection();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }
    
}
