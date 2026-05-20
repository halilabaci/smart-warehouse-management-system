package com.smartwarehouse.model;

import java.time.LocalDateTime;

public class Shipment implements Comparable<Shipment> {
    private int shipmentId;
    private String customerName;
    private int priority;
    private String status;
    private LocalDateTime createdAt;

    public Shipment() {
    }

    public Shipment(int shipmentId, String customerName, int priority, String status, LocalDateTime createdAt) {
        this.shipmentId = shipmentId;
        this.customerName = customerName;
        this.priority = priority;
        this.status = status;
        this.createdAt = createdAt;
    }

    public int getShipmentId() {
        return shipmentId;
    }

    public void setShipmentId(int shipmentId) {
        this.shipmentId = shipmentId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
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

    @Override
    public int compareTo(Shipment other) {
        int priorityComparison = Integer.compare(priority, other.priority);
        if (priorityComparison != 0) {
            return priorityComparison;
        }
        if (createdAt == null && other.createdAt == null) {
            return 0;
        }
        if (createdAt == null) {
            return -1;
        }
        if (other.createdAt == null) {
            return 1;
        }
        return other.createdAt.compareTo(createdAt);
    }

    @Override
    public String toString() {
        return "ID=" + shipmentId
            + ", customer='" + customerName + '\''
            + ", priority=" + priority
            + ", status='" + status + '\''
            + ", createdAt=" + createdAt;
    }
}
