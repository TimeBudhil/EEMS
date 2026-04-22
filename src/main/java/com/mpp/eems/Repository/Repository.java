package com.mpp.eems.Repository;

import java.sql.Connection;
import java.sql.SQLException;

public class Repository {

    //singleton connection that can't be used by non-repository layer
    protected Connection getConnection() throws SQLException {
        return DBConnection.getConnection();
    }
}
