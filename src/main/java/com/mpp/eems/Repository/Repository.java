package com.mpp.eems.Repository;

import java.sql.Connection;
import java.sql.SQLException;

public class Repository {

    protected Connection getConnection() throws SQLException {
        return DBConnection.getConnection();
    }
}
