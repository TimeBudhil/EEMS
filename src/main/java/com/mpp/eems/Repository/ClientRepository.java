package com.mpp.eems.Repository;

import com.mpp.eems.Domain.Client;
import com.mpp.eems.Domain.Department;
import com.mpp.eems.Domain.Status;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class  ClientRepository extends Repository{

    private Client mapRowToClient(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String name = rs.getString("name");
        String industry = rs.getString("industry");
        String primaryContactName = rs.getString("primary_contact_name");
        String primaryContactPhone = rs.getString("primary_contact_phone");
        String primaryContactEmail = rs.getString("primary_contact_email");


        Client c = new Client(id, name, industry, primaryContactName, primaryContactPhone, primaryContactEmail, new ArrayList<>() );
        return c;
    }


    public Client addClient(Client client) throws SQLException {

        String sql = "INSERT INTO client (id, name, industry, primary_contact_name, primary_contact_phone, primary_contact_email) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS) ) {
            pstmt.setInt(1, client.getId());
            pstmt.setString(2, client.getName());
            pstmt.setString(3, client.getIndustry());
            pstmt.setString(4, client.getPrimaryContactName());
            pstmt.setString(5, client.getPrimaryContactPhone());
            pstmt.setString(6, client.getPrimaryContactEmail());
//            pstmt.

            if( pstmt.execute() )
                System.out.println("Client added successfully!");

            // Retrieve the Client-generated id and return a complete Project
            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int generatedId = generatedKeys.getInt(1);
                    return new Client(
                            generatedId,
                            client.getName(),
                            client.getIndustry(),
                            client.getPrimaryContactName(),
                            client.getPrimaryContactPhone(),
                            client.getPrimaryContactEmail(),
                            new ArrayList<>()
                    );
                }
            }

        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        throw new SQLException("Insert failed");
    }

    public Client findClient(int id) {
        String sql = "SELECT * FROM client WHERE id = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return mapRowToClient(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<Client> findAllClient() {
        List<Client> clients = new ArrayList<>();
        String sql = "SELECT * FROM client";

        try (PreparedStatement pstmt = connection.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                clients.add( mapRowToClient(rs) );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return clients;
    }

    public void modifyClient(Client client) {
        String sql = "UPDATE client SET name = ?, industry = ?, primary_contact_name = ?, primary_contact_phone = ?, primary_contact_email = ? WHERE id = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setString(1, client.getName());
            pstmt.setString(2, client.getIndustry());
            pstmt.setString(3, client.getPrimaryContactName());
            pstmt.setString(4, client.getPrimaryContactPhone());
            pstmt.setString(5, client.getPrimaryContactEmail());
            pstmt.setInt(6, client.getId());

            if( pstmt.execute() )
                System.out.println("Client updated!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteClient(int id) {
        String sql = "DELETE FROM client WHERE id = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setInt(1, id);

            if ( pstmt.execute() )
                System.out.println("Client deleted!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    


}
