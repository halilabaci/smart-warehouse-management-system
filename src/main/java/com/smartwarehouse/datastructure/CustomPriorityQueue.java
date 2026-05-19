package com.smartwarehouse.datastructure;

public class CustomPriorityQueue<T extends Comparable<T>> {

    private Node<T> head;
    private int size;

    public void add(T item) {
        Node<T> newNode = new Node<>(item);
        if (head == null || item.compareTo(head.value) < 0) {
            newNode.next = head;
            head = newNode;
            size++;
            return;
        }

        Node<T> current = head;
        while (current.next != null && item.compareTo(current.next.value) >= 0) {
            current = current.next;
        }
        newNode.next = current.next;
        current.next = newNode;
        size++;
    }

    public T poll() {
        if (head == null) {
            return null;
        }
        T value = head.value;
        head = head.next;
        size--;
        return value;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    private static final class Node<T> {
        private final T value;
        private Node<T> next;

        private Node(T value) {
            this.value = value;
        }
    }
}
