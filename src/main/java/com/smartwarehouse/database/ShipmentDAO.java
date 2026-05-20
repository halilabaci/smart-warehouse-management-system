package com.smartwarehouse.database;

import com.smartwarehouse.model.Shipment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ShipmentDAO {

    public Shipment addShipment(Shipment shipment) throws SQLException {
        String sql = """
            INSERT INTO shipments (customer_name, priority, status)
            VALUES (?, ?, ?)
            RETURNING shipment_id, customer_name, priority, status, created_at
            """;
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, shipment.getCustomerName());
            statement.setInt(2, shipment.getPriority());
            statement.setString(3, shipment.getStatus());
            try (ResultSet resultSet = statement.executeQuery()) {
                resultSet.next();
                return mapShipment(resultSet);
            }
        }
    }

    public Optional<Shipment> getShipmentById(int shipmentId) throws SQLException {
        String sql = "SELECT shipment_id, customer_name, priority, status, created_at FROM shipments WHERE shipment_id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, shipmentId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(mapShipment(resultSet));
                }
            }
        }
        return Optional.empty();
    }

    public List<Shipment> getWaitingShipments() throws SQLException {
        String sql = """
            SELECT shipment_id, customer_name, priority, status, created_at
            FROM shipments
            WHERE status = 'WAITING'
            ORDER BY created_at, shipment_id
            """;
        List<Shipment> shipments = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                shipments.add(mapShipment(resultSet));
            }
        }
        return shipments;
    }

    public boolean updateStatus(int shipmentId, String status) throws SQLException {
        String sql = "UPDATE shipments SET status = ? WHERE shipment_id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, status);
            statement.setInt(2, shipmentId);
            return statement.executeUpdate() > 0;
        }
    }

    public boolean deleteShipment(int shipmentId) throws SQLException {
        String sql = "DELETE FROM shipments WHERE shipment_id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, shipmentId);
            return statement.executeUpdate() > 0;
        }
    }

    private Shipment mapShipment(ResultSet resultSet) throws SQLException {
        Timestamp createdAt = resultSet.getTimestamp("created_at");
        return new Shipment(
            resultSet.getInt("shipment_id"),
            resultSet.getString("customer_name"),
            resultSet.getInt("priority"),
            resultSet.getString("status"),
            createdAt == null ? null : createdAt.toLocalDateTime()
        );
    }
}
