package com.smartwarehouse.service;

import com.smartwarehouse.database.ShipmentDAO;
import com.smartwarehouse.model.Shipment;

import java.sql.SQLException;
import java.util.List;

public class ShipmentService {

    private final ShipmentDAO shipmentDAO = new ShipmentDAO();

    public Shipment scheduleShipment(Shipment shipment) throws SQLException {
        return shipmentDAO.addShipment(shipment);
    }

    public List<Shipment> getWaitingShipments() throws SQLException {
        return shipmentDAO.getWaitingShipments();
    }

    public boolean markProcessed(int shipmentId) throws SQLException {
        return shipmentDAO.updateStatus(shipmentId, "PROCESSED");
    }
}
