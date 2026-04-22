package com.mpp.eems.Controller;



import com.mpp.eems.Controller.*;
import com.sun.net.httpserver.HttpServer;

import java.net.InetSocketAddress;

public class AppServer {

    public static void start() throws Exception {

        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

        EmployeeController emp = new EmployeeController();
        //DepartmentController dept = new DepartmentController();

        server.createContext("/employees", emp::handle);
      //  server.createContext("/departments", dept::handle);

        server.start();

        System.out.println("Server running at http://localhost:8080");
    }
}