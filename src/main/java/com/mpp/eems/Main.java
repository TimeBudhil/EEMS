package com.mpp.eems;

import com.mpp.eems.Controller.AppServer;

public class Main {
    public static void main(String[] args) throws Exception {
        try {
            AppServer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}