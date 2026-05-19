package com.smartwarehouse.algorithm;

import java.util.Arrays;

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
}
