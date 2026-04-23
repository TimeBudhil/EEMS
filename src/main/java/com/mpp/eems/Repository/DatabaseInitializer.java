package com.mpp.eems.Repository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseInitializer {

    public static void initialize() throws SQLException, IOException {
        String sql = Files.readString(Paths.get("schema.sql"));
        String[] statements = sql.split(";");

        try (Statement stmt = DBConnection.getConnection().createStatement()) {
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

    public static void resetDatabase() throws SQLException {
        String sql = """
            DROP TABLE IF EXISTS Employee_Project CASCADE;
            DROP TABLE IF EXISTS Client_Project CASCADE;
            DROP TABLE IF EXISTS Project_Department CASCADE;
            DROP TABLE IF EXISTS Employee CASCADE;
            DROP TABLE IF EXISTS Project CASCADE;
            DROP TABLE IF EXISTS Client CASCADE;
            DROP TABLE IF EXISTS Department CASCADE;
        """;

        // Don't put DBConnection.getConnection() in try-with-resources
        // — it closes the singleton and breaks subsequent calls
        Connection conn = DBConnection.getConnection();
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("✔ Database reset");
        }
    }
}