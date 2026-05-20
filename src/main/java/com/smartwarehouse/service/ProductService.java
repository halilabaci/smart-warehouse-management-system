package com.smartwarehouse.service;

import com.smartwarehouse.database.ProductDAO;
import com.smartwarehouse.model.Product;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class ProductService {

    private final ProductDAO productDAO = new ProductDAO();

    public void addProduct(Product product) throws SQLException {
        productDAO.addProduct(product);
    }

    public Optional<Product> getProduct(int productId) throws SQLException {
        return productDAO.getProductById(productId);
    }

    public List<Product> getAllProducts() throws SQLException {
        return productDAO.getAllProducts();
    }

    public boolean updateProduct(Product product) throws SQLException {
        return productDAO.updateProduct(product);
    }

    public boolean removeProduct(int productId) throws SQLException {
        return productDAO.deleteProduct(productId);
    }
}
