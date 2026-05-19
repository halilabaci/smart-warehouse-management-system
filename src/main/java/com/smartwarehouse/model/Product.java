package com.smartwarehouse.model;

public class Product {
    private int id;
    private String sku;
    private String name;
    private int quantity;
    private Integer shelfId;

    public Product() {
    }

    public Product(int id, String sku, String name, int quantity, Integer shelfId) {
        this.id = id;
        this.sku = sku;
        this.name = name;
        this.quantity = quantity;
        this.shelfId = shelfId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Integer getShelfId() {
        return shelfId;
    }

    public void setShelfId(Integer shelfId) {
        this.shelfId = shelfId;
    }
}
