package com.smartwarehouse.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Product implements Comparable<Product> {
    private int productId;
    private String name;
    private String category;
    private BigDecimal price;
    private int stockQuantity;
    private LocalDate expirationDate;
    private String shelfId;

    public Product() {
    }

    public Product(int productId, String name, String category, BigDecimal price, int stockQuantity,
                   LocalDate expirationDate, String shelfId) {
        this.productId = productId;
        this.name = name;
        this.category = category;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.expirationDate = expirationDate;
        this.shelfId = shelfId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getShelfId() {
        return shelfId;
    }

    public void setShelfId(String shelfId) {
        this.shelfId = shelfId;
    }

    @Override
    public int compareTo(Product other) {
        return Integer.compare(productId, other.productId);
    }

    @Override
    public String toString() {
        return "ID=" + productId
            + ", name='" + name + '\''
            + ", category='" + category + '\''
            + ", price=" + price
            + ", stock=" + stockQuantity
            + ", expiration=" + (expirationDate == null ? "-" : expirationDate)
            + ", shelf='" + shelfId + '\'';
    }
}
