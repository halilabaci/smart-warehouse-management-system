package com.smartwarehouse.datastructure;

public class CustomPriorityQueue<T extends Comparable<T>> {

    private int size;

    public void add(T item) {
        size++;
    }

    public T poll() {
        throw new UnsupportedOperationException("Poll implementation will be added in later iterations.");
    }

    public boolean isEmpty() {
        return size == 0;
    }
}
