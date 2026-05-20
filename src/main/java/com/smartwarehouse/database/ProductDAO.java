package com.smartwarehouse.database;

import com.smartwarehouse.model.Product;

import java.sql.SQLException;
import java.util.Optional;

public class ProductDAO {

    public Optional<Product> findById(int id) throws SQLException {
        throw new UnsupportedOperationException("findById not implemented yet");
    }

    public void save(Product product) throws SQLException {
        throw new UnsupportedOperationException("save not implemented yet");
    }

    public void delete(int id) throws SQLException {
        throw new UnsupportedOperationException("delete not implemented yet");
    }
}
