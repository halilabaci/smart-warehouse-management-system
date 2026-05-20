package com.smartwarehouse.database;

import com.smartwarehouse.datastructure.CustomGraph;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class WarehousePathDAO {

    public CustomGraph<String> loadGraph() throws SQLException {
        CustomGraph<String> graph = new CustomGraph<>();
        String sql = "SELECT from_node, to_node, distance FROM warehouse_paths ORDER BY path_id";
        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                graph.addEdge(
                    resultSet.getString("from_node"),
                    resultSet.getString("to_node"),
                    resultSet.getInt("distance")
                );
            }
        }
        return graph;
    }
}
