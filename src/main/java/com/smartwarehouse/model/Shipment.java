package com.smartwarehouse.model;

import java.time.LocalDateTime;

public class Shipment {
    private int id;
    private String shipmentCode;
    private String destination;
    private int priority;
    private String status;
    private LocalDateTime createdAt;

    public Shipment() {
    }

    public Shipment(int id, String shipmentCode, String destination, int priority, String status, LocalDateTime createdAt) {
        this.id = id;
        this.shipmentCode = shipmentCode;
        this.destination = destination;
        this.priority = priority;
        this.status = status;
        this.createdAt = createdAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getShipmentCode() {
        return shipmentCode;
    }

    public void setShipmentCode(String shipmentCode) {
        this.shipmentCode = shipmentCode;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
