package com.mpp.eems.Repository;

import com.mpp.eems.Domain.Employee;
import com.mpp.eems.Domain.Department;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeRepository extends Repository {

    // =========================
    // INSERT (AUTO ID)
    // =========================
    public Employee add(Employee emp) {

        String sql = """
            INSERT INTO employee
            (first_name, last_name, title, date_hired, salary, department_id)
            VALUES (?, ?, ?, ?, ?, ?)
        """;

        try (PreparedStatement stmt = connection.prepareStatement(
                sql,
                Statement.RETURN_GENERATED_KEYS
        )) {

            stmt.setString(1, emp.getFirstName());
            stmt.setString(2, emp.getLastName());
            stmt.setString(3, emp.getTitle());
            stmt.setDate(4, Date.valueOf(emp.getHiredDate()));
            stmt.setDouble(5, emp.getSalary());
            stmt.setInt(6, emp.getDepartment().getId());

            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            rs.next();

            int id = rs.getInt(1);

            return new Employee(
                    id,
                    emp.getFirstName(),
                    emp.getLastName(),
                    emp.getTitle(),
                    emp.getHiredDate(),
                    emp.getSalary(),
                    emp.getDepartment(),
                    null
            );

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // =========================
    // FIND BY ID
    // =========================
    public Employee findById(int id) {

        String sql = """
            SELECT e.*, d.id AS d_id, d.name, d.city, d.annual_budget
            FROM employee e
            JOIN department d ON e.department_id = d.id
            WHERE e.id = ?
        """;

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (!rs.next()) return null;

            Department dept = new Department(
                    rs.getInt("d_id"),
                    rs.getString("name"),
                    rs.getString("city"),
                    rs.getDouble("annual_budget"),
                    null
            );

            return new Employee(
                    rs.getInt("id"),
                    rs.getString("first_name"),
                    rs.getString("last_name"),
                    rs.getString("title"),
                    rs.getDate("date_hired").toLocalDate(),
                    rs.getDouble("salary"),
                    dept,
                    null
            );

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // =========================
    // FIND ALL
    // =========================
    public List<Employee> findAll() {

        List<Employee> list = new ArrayList<>();

        String sql = "SELECT id FROM employee";

        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                list.add(findById(rs.getInt("id")));
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return list;
    }

    // =========================
    // UPDATE
    // =========================
    public void update(Employee emp) {

        String sql = """
            UPDATE employee
            SET first_name=?, last_name=?, title=?, date_hired=?, salary=?, department_id=?
            WHERE id=?
        """;

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, emp.getFirstName());
            stmt.setString(2, emp.getLastName());
            stmt.setString(3, emp.getTitle());
            stmt.setDate(4, Date.valueOf(emp.getHiredDate()));
            stmt.setDouble(5, emp.getSalary());
            stmt.setInt(6, emp.getDepartment().getId());
            stmt.setInt(7, emp.getId());

            stmt.executeUpdate();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // =========================
    // DELETE
    // =========================
    public void delete(int id) {

        String sql = "DELETE FROM employee WHERE id=?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}