package com.mpp.eems;

import com.mpp.eems.Controller.AppServer;

/**
 * API uses the controllers
 */
public class Main {
    public static void main(String[] args) throws Exception {
        try {
            AppServer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}