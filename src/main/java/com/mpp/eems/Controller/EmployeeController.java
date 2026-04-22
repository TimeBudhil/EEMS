package com.mpp.eems.Controller;

import com.mpp.eems.Domain.Employee;

import com.mpp.eems.Services.EmployeeService;
import com.sun.net.httpserver.HttpExchange;

import java.io.*;
import java.util.stream.Collectors;
import com.fasterxml.jackson.databind.ObjectMapper;

public class EmployeeController {

    private EmployeeService service = new EmployeeService();

    public void handle(HttpExchange exchange) throws IOException {

        String method = exchange.getRequestMethod();
        String path = exchange.getRequestURI().getPath();

        if ("GET".equals(method) && path.equals("/employees")) {
            send(exchange, service.getAllEmployees().toString());
        }

        else if ("GET".equals(method) && path.startsWith("/employees/")) {
            int id = Integer.parseInt(path.split("/")[2]);
            send(exchange, service.getEmployeeById(id).toString());
        }

        else if ("POST".equals(method)) {
            String body = new BufferedReader(new InputStreamReader(exchange.getRequestBody()))
                    .lines().collect(Collectors.joining());

            // Simple JSON parsing (matches your structure)


            ObjectMapper mapper = new ObjectMapper();

            try {
                Employee emp = mapper.readValue(body, Employee.class);
                send(exchange, service.createEmployee(emp).toString());
            } catch (Exception e) {
                send(exchange, "Invalid JSON format");
            }
//            send(exchange, service.createEmployee(emp).toString());
        }

        else if ("DELETE".equals(method) && path.startsWith("/employees/")) {
            int id = Integer.parseInt(path.split("/")[2]);
            service.deleteEmployee(id);
            send(exchange, "Deleted");
        }
    }

    private void send(HttpExchange ex, String response) throws IOException {
        ex.sendResponseHeaders(200, response.length());
        OutputStream os = ex.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}