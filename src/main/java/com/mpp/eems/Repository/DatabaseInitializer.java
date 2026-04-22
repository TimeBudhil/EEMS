package com.mpp.eems.Repository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseInitializer {

    public static void initialize(Connection connection) throws SQLException, IOException {
        String sql = Files.readString(Paths.get("schema.sql"));
        String[] statements = sql.split(";");

        try (Statement stmt = connection.createStatement()) {
            for (String statement : statements) {
                String trimmed = statement.trim();
                if (!trimmed.isEmpty()) {
                    System.out.println("Executing: " + trimmed.substring(0, Math.min(60, trimmed.length())));
                    stmt.execute(trimmed);
                }
            }
            System.out.println("Schema initialized successfully.");
        }
        // No catch — let the real SQLException bubble up fully
    }
}