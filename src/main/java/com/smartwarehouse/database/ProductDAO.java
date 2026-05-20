package com.smartwarehouse.database;

import com.smartwarehouse.model.Product;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductDAO {

    public void addProduct(Product product) throws SQLException {
        String sql = """
            INSERT INTO products (product_id, name, category, price, stock_quantity, expiration_date, shelf_id)
            VALUES (?, ?, ?, ?, ?, ?, ?)
            """;
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            bindProduct(statement, product);
            statement.executeUpdate();
        }
    }

    public Optional<Product> getProductById(int productId) throws SQLException {
        String sql = "SELECT product_id, name, category, price, stock_quantity, expiration_date, shelf_id FROM products WHERE product_id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, productId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(mapProduct(resultSet));
                }
            }
        }
        return Optional.empty();
    }

    public List<Product> getAllProducts() throws SQLException {
        String sql = "SELECT product_id, name, category, price, stock_quantity, expiration_date, shelf_id FROM products ORDER BY product_id";
        List<Product> products = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                products.add(mapProduct(resultSet));
            }
        }
        return products;
    }

    public boolean updateProduct(Product product) throws SQLException {
        String sql = """
            UPDATE products
            SET name = ?, category = ?, price = ?, stock_quantity = ?, expiration_date = ?, shelf_id = ?
            WHERE product_id = ?
            """;
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, product.getName());
            statement.setString(2, product.getCategory());
            statement.setBigDecimal(3, product.getPrice());
            statement.setInt(4, product.getStockQuantity());
            statement.setDate(5, product.getExpirationDate() == null ? null : Date.valueOf(product.getExpirationDate()));
            statement.setString(6, product.getShelfId());
            statement.setInt(7, product.getProductId());
            return statement.executeUpdate() > 0;
        }
    }

    public boolean deleteProduct(int productId) throws SQLException {
        String sql = "DELETE FROM products WHERE product_id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, productId);
            return statement.executeUpdate() > 0;
        }
    }

    public boolean existsById(int productId) throws SQLException {
        return getProductById(productId).isPresent();
    }

    private void bindProduct(PreparedStatement statement, Product product) throws SQLException {
        statement.setInt(1, product.getProductId());
        statement.setString(2, product.getName());
        statement.setString(3, product.getCategory());
        statement.setBigDecimal(4, product.getPrice());
        statement.setInt(5, product.getStockQuantity());
        statement.setDate(6, product.getExpirationDate() == null ? null : Date.valueOf(product.getExpirationDate()));
        statement.setString(7, product.getShelfId());
    }

    private Product mapProduct(ResultSet resultSet) throws SQLException {
        Date expirationDate = resultSet.getDate("expiration_date");
        return new Product(
            resultSet.getInt("product_id"),
            resultSet.getString("name"),
            resultSet.getString("category"),
            resultSet.getBigDecimal("price"),
            resultSet.getInt("stock_quantity"),
            expirationDate == null ? null : expirationDate.toLocalDate(),
            resultSet.getString("shelf_id")
        );
    }
}
