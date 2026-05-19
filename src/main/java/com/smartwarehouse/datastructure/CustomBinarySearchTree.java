package com.smartwarehouse.datastructure;

public class CustomBinarySearchTree<T extends Comparable<T>> {

    private Node<T> root;

    public void insert(T value) {
        root = insertRecursive(root, value);
    }

    public boolean contains(T value) {
        return containsRecursive(root, value);
    }

    private Node<T> insertRecursive(Node<T> current, T value) {
        if (current == null) {
            return new Node<>(value);
        }
        if (value.compareTo(current.value) < 0) {
            current.left = insertRecursive(current.left, value);
        } else if (value.compareTo(current.value) > 0) {
            current.right = insertRecursive(current.right, value);
        }
        return current;
    }

    private boolean containsRecursive(Node<T> current, T value) {
        if (current == null) {
            return false;
        }
        int comparison = value.compareTo(current.value);
        if (comparison == 0) {
            return true;
        }
        return comparison < 0 ? containsRecursive(current.left, value) : containsRecursive(current.right, value);
    }

    private static final class Node<T> {
        private final T value;
        private Node<T> left;
        private Node<T> right;

        private Node(T value) {
            this.value = value;
        }
    }
}
