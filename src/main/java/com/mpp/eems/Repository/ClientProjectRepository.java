package com.mpp.eems.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClientProjectRepository extends Repository {

    public void linkClient(int projectId, int clientId) throws SQLException {
        String sql = "INSERT INTO Client_Project (project_id, client_id) VALUES (?, ?)";

        try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {
            stmt.setInt(1, projectId);
            stmt.setInt(2, clientId);
            stmt.executeUpdate();
        }
    }

    public void unlinkClient(int projectId, int clientId) throws SQLException {
        String sql = "DELETE FROM Client_Project WHERE project_id = ? AND client_id = ?";

        try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {
            stmt.setInt(1, projectId);
            stmt.setInt(2, clientId);
            stmt.executeUpdate();
        }
    }

    public List<Integer> findClientIdsByProject(int projectId) {
        String sql = "SELECT client_id FROM Client_Project WHERE project_id = ?";
        List<Integer> ids = new ArrayList<>();

        try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {
            stmt.setInt(1, projectId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    ids.add(rs.getInt("client_id"));
                }
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        return ids;
    }

    public List<Integer> findProjectIdsByClient(int clientId) {
        String sql = "SELECT project_id FROM Client_Project WHERE client_id = ?";
        List<Integer> ids = new ArrayList<>();

        try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {
            stmt.setInt(1, clientId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    ids.add(rs.getInt("project_id"));
                }
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        return ids;
    }
}