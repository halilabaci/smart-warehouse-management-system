package com.smartwarehouse.model;

import java.time.LocalDateTime;

public class StockLog {
    private int id;
    private Integer productId;
    private String action;
    private int quantityChange;
    private LocalDateTime logTime;

    public StockLog() {
    }

    public StockLog(int id, Integer productId, String action, int quantityChange, LocalDateTime logTime) {
        this.id = id;
        this.productId = productId;
        this.action = action;
        this.quantityChange = quantityChange;
        this.logTime = logTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public int getQuantityChange() {
        return quantityChange;
    }

    public void setQuantityChange(int quantityChange) {
        this.quantityChange = quantityChange;
    }

    public LocalDateTime getLogTime() {
        return logTime;
    }

    public void setLogTime(LocalDateTime logTime) {
        this.logTime = logTime;
    }
}
