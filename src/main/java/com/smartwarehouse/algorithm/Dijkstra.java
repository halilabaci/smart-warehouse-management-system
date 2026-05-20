package com.smartwarehouse.algorithm;

import com.smartwarehouse.datastructure.CustomGraph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class Dijkstra {

    private Dijkstra() {
    }

    public static int[] shortestPaths(int[][] graph, int source) {
        int n = graph.length;
        int[] distance = new int[n];
        boolean[] visited = new boolean[n];

        Arrays.fill(distance, Integer.MAX_VALUE);
        distance[source] = 0;

        for (int count = 0; count < n - 1; count++) {
            int u = minDistance(distance, visited);
            if (u == -1) {
                break;
            }
            visited[u] = true;

            for (int v = 0; v < n; v++) {
                if (!visited[v] && graph[u][v] > 0 && distance[u] != Integer.MAX_VALUE
                    && distance[u] + graph[u][v] < distance[v]) {
                    distance[v] = distance[u] + graph[u][v];
                }
            }
        }

        return distance;
    }

    public static PathResult shortestPath(CustomGraph<String> graph, String start, String end) {
        List<String> vertices = graph.getVertices();
        Map<String, Integer> distance = new HashMap<>();
        Map<String, String> previous = new HashMap<>();
        Map<String, Boolean> visited = new HashMap<>();

        for (String vertex : vertices) {
            distance.put(vertex, Integer.MAX_VALUE);
            visited.put(vertex, false);
        }
        distance.put(start, 0);

        for (int i = 0; i < vertices.size(); i++) {
            String current = minDistance(distance, visited);
            if (current == null) {
                break;
            }
            visited.put(current, true);
            if (current.equals(end)) {
                break;
            }

            for (CustomGraph.Edge<String> edge : graph.getEdges(current)) {
                String neighbor = edge.getTo();
                if (Boolean.TRUE.equals(visited.get(neighbor))) {
                    continue;
                }
                int currentDistance = distance.get(current);
                if (currentDistance != Integer.MAX_VALUE) {
                    int candidate = currentDistance + edge.getWeight();
                    if (candidate < distance.getOrDefault(neighbor, Integer.MAX_VALUE)) {
                        distance.put(neighbor, candidate);
                        previous.put(neighbor, current);
                    }
                }
            }
        }

        int totalDistance = distance.getOrDefault(end, Integer.MAX_VALUE);
        if (totalDistance == Integer.MAX_VALUE) {
            return new PathResult(List.of(), totalDistance);
        }

        List<String> path = new ArrayList<>();
        String current = end;
        while (current != null) {
            path.add(current);
            current = previous.get(current);
        }
        Collections.reverse(path);
        return new PathResult(path, totalDistance);
    }

    private static int minDistance(int[] distance, boolean[] visited) {
        int minValue = Integer.MAX_VALUE;
        int minIndex = -1;

        for (int index = 0; index < distance.length; index++) {
            if (!visited[index] && distance[index] < minValue) {
                minValue = distance[index];
                minIndex = index;
            }
        }

        return minIndex;
    }

    private static String minDistance(Map<String, Integer> distance, Map<String, Boolean> visited) {
        int minValue = Integer.MAX_VALUE;
        String minVertex = null;
        for (Map.Entry<String, Integer> entry : distance.entrySet()) {
            if (!Boolean.TRUE.equals(visited.get(entry.getKey())) && entry.getValue() < minValue) {
                minValue = entry.getValue();
                minVertex = entry.getKey();
            }
        }
        return minVertex;
    }

    public record PathResult(List<String> path, int distance) {
    }
}
