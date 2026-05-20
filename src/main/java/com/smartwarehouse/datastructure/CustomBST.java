package com.smartwarehouse.datastructure;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class CustomBST<T> {

    private final Comparator<T> comparator;
    private Node<T> root;

    public CustomBST(Comparator<T> comparator) {
        this.comparator = comparator;
    }

    public void insert(T value) {
        root = insert(root, value);
    }

    public T search(T value) {
        Node<T> current = root;
        while (current != null) {
            int comparison = comparator.compare(value, current.value);
            if (comparison == 0) {
                return current.value;
            }
            current = comparison < 0 ? current.left : current.right;
        }
        return null;
    }

    public void delete(T value) {
        root = delete(root, value);
    }

    public List<T> inorderTraversal() {
        List<T> values = new ArrayList<>();
        inorder(root, values);
        return values;
    }

    private Node<T> insert(Node<T> current, T value) {
        if (current == null) {
            return new Node<>(value);
        }
        int comparison = comparator.compare(value, current.value);
        if (comparison < 0) {
            current.left = insert(current.left, value);
        } else if (comparison > 0) {
            current.right = insert(current.right, value);
        } else {
            current.value = value;
        }
        return current;
    }

    private Node<T> delete(Node<T> current, T value) {
        if (current == null) {
            return null;
        }
        int comparison = comparator.compare(value, current.value);
        if (comparison < 0) {
            current.left = delete(current.left, value);
        } else if (comparison > 0) {
            current.right = delete(current.right, value);
        } else {
            if (current.left == null) {
                return current.right;
            }
            if (current.right == null) {
                return current.left;
            }
            Node<T> successor = min(current.right);
            current.value = successor.value;
            current.right = delete(current.right, successor.value);
        }
        return current;
    }

    private Node<T> min(Node<T> current) {
        while (current.left != null) {
            current = current.left;
        }
        return current;
    }

    private void inorder(Node<T> current, List<T> values) {
        if (current == null) {
            return;
        }
        inorder(current.left, values);
        values.add(current.value);
        inorder(current.right, values);
    }

    private static final class Node<T> {
        private T value;
        private Node<T> left;
        private Node<T> right;

        private Node(T value) {
            this.value = value;
        }
    }
}
