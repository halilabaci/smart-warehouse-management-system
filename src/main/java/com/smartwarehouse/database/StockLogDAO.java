package com.smartwarehouse.database;

import com.smartwarehouse.model.StockLog;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class StockLogDAO {

    public void addLog(StockLog log) throws SQLException {
        String sql = "INSERT INTO stock_logs (product_id, action, quantity_change) VALUES (?, ?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            if (log.getProductId() == null) {
                statement.setNull(1, java.sql.Types.INTEGER);
            } else {
                statement.setInt(1, log.getProductId());
            }
            statement.setString(2, log.getAction());
            statement.setInt(3, log.getQuantityChange());
            statement.executeUpdate();
        }
    }
}
