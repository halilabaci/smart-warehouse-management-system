package com.smartwarehouse.service;

public class WarehouseService {

    private final ProductService productService = new ProductService();
    private final ShipmentService shipmentService = new ShipmentService();

    public void performDailyMaintenance() {
        throw new UnsupportedOperationException("performDailyMaintenance not implemented yet");
    }
}
