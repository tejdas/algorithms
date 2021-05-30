package net.lc.dfs;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 261
 * DFS
 * Undirected Graph Cycle detection
 */
public class GraphValidTree {
    private Set<Integer> visited = new HashSet<>();
    private Map<Integer, Set<Integer>> adjMap = new HashMap<>();
    public boolean validTree(int n, int[][] edges) {
        visited.clear();
        adjMap.clear();

        if (n == 1 && edges.length == 0) return true;

        for (int[] edge : edges) {
            int n1 = edge[0];
            int n2 = edge[1];

            adjMap.putIfAbsent(n1, new HashSet<>());
            adjMap.get(n1).add(n2);

            adjMap.putIfAbsent(n2, new HashSet<>());
            adjMap.get(n2).add(n1);
        }

        /**
         * Check for cycle
         */
        if (detectCycle(0, -1)) return false;

        /**
         * Check if it is Connected Graph
         */
        if (visited.size() < n) return false;

        return true;
    }

    private boolean detectCycle(int cur, int par) {
        visited.add(cur);

        if (adjMap.containsKey(cur)) {
            for (int adj : adjMap.get(cur)) {
                if (!visited.contains(adj)) {
                    if (detectCycle(adj, cur)) {
                        return true;
                    }
                } else {
                    /**
                     * Since adj is visited, adj must be cur's parent (cur must have come from adj)
                     * If adj is not parent, that means we have detected a cycle.
                     */
                    if (adj != par) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
