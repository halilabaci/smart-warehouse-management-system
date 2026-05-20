package com.smartwarehouse.service;

import com.smartwarehouse.model.Product;

import java.sql.SQLException;

public class InventoryService {

    private final ProductService productService = new ProductService();

    public void addProduct(Product product) {
        try {
            productService.addProduct(product);
        } catch (SQLException exception) {
            throw new IllegalStateException("Product could not be added", exception);
        }
    }
}
