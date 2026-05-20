package com.smartwarehouse.service;

import com.smartwarehouse.database.ProductDAO;
import com.smartwarehouse.model.Product;

import java.sql.SQLException;
import java.util.Optional;

public class ProductService {

    private final ProductDAO productDAO = new ProductDAO();

    public Optional<Product> getProduct(int id) throws SQLException {
        return productDAO.findById(id);
    }

    public void createOrUpdate(Product product) throws SQLException {
        productDAO.save(product);
    }

    public void removeProduct(int id) throws SQLException {
        productDAO.delete(id);
    }
}
