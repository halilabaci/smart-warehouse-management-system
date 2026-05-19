package com.smartwarehouse.ui;

import com.smartwarehouse.database.DatabaseConnection;

public class ConsoleApplication {

    public void start() {
        System.out.println("Smart Warehouse Inventory and Shipment Management System");
        if (DatabaseConnection.testConnection()) {
            System.out.println("Database connection established.");
        } else {
            System.out.println("Database connection not available. Check DB configuration.");
        }
        System.out.println("Console menu starter is ready for implementation.");
    }
}
