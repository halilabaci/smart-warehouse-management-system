package com.smartwarehouse.datastructure;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class CustomGraph<T> {

    private final Map<T, CustomLinkedList<Edge<T>>> adjacencyList = new LinkedHashMap<>();

    public void addVertex(T vertex) {
        adjacencyList.putIfAbsent(vertex, new CustomLinkedList<>());
    }

    public void addEdge(T from, T to, int weight) {
        addVertex(from);
        addVertex(to);
        adjacencyList.get(from).add(new Edge<>(to, weight));
        adjacencyList.get(to).add(new Edge<>(from, weight));
    }

    public List<T> getVertices() {
        return new ArrayList<>(adjacencyList.keySet());
    }

    public List<Edge<T>> getEdges(T vertex) {
        CustomLinkedList<Edge<T>> edges = adjacencyList.get(vertex);
        if (edges == null) {
            return List.of();
        }
        return edges.toList();
    }

    public boolean containsVertex(T vertex) {
        return adjacencyList.containsKey(vertex);
    }

    public static final class Edge<T> {
        private final T to;
        private final int weight;

        public Edge(T to, int weight) {
            this.to = to;
            this.weight = weight;
        }

        public T getTo() {
            return to;
        }

        public int getWeight() {
            return weight;
        }
    }
}
