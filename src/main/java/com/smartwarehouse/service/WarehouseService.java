package com.smartwarehouse.service;

public class WarehouseService {

    private final ProductService productService = new ProductService();
    private final ShipmentService shipmentService = new ShipmentService();

    public void performDailyMaintenance() {
        System.out.println("Daily maintenance completed.");
    }
}
