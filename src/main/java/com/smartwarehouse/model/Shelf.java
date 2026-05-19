package com.smartwarehouse.model;

public class Shelf {
    private int id;
    private String code;
    private int capacity;
    private int currentLoad;

    public Shelf() {
    }

    public Shelf(int id, String code, int capacity, int currentLoad) {
        this.id = id;
        this.code = code;
        this.capacity = capacity;
        this.currentLoad = currentLoad;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getCurrentLoad() {
        return currentLoad;
    }

    public void setCurrentLoad(int currentLoad) {
        this.currentLoad = currentLoad;
    }
}
