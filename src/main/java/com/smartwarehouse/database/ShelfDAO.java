package com.smartwarehouse.database;

import com.smartwarehouse.model.Shelf;

import java.sql.SQLException;
import java.util.Optional;

public class ShelfDAO {

    public Optional<Shelf> findById(int id) throws SQLException {
        return Optional.empty();
    }

    public void save(Shelf shelf) throws SQLException {
        // Shelves are represented by the products.shelf_id value in the current schema.
    }

    public void delete(int id) throws SQLException {
        // No separate shelves table exists in the current schema.
    }
}
