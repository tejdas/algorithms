package net.lc;

import java.util.*;

/**
 * 210
 * Directed Graph
 * DAG
 * Topological Sort
 * Cycles
 */
public class CourseScheduleII {
    private final Map<Integer, List<Integer>> adjMap = new HashMap<>();
    public int[] findOrder(int numCourses, int[][] prerequisites) {

        Set<Integer> roots = new HashSet<>();

        for (int[] edge : prerequisites) {
            int from = edge[1];
            int to = edge[0];

            roots.remove(to);
            roots.add(from);

            List<Integer> adjList = adjMap.get(from);
            if (adjList == null) {
                adjList = new ArrayList<>();
                adjMap.put(from, adjList);
            }
            adjList.add(to);
        }

        if (hasCycle(roots)) {
            return new int[] {};
        }

        Stack<Integer> stack = traverseDFS(numCourses);

        if (stack.isEmpty()) {
            return new int[] {};
        }

        int[] ret = new int[stack.size()];
        int idx = 0;
        while (!stack.isEmpty()) {
            ret[idx++] = stack.pop();
        }
        return ret;
    }

    public Stack<Integer> traverseDFS(int vertices) {
        final Stack<Integer> topological = new Stack<>();
        Set<Integer> visited = new HashSet<>();


        for (int v = 0; v < vertices; v++) {
            if (!visited.contains(v)) {
                dfs(v, visited, topological);
            }
        }
        return topological;
    }

    private void dfs(final int currentNode, Set<Integer> visited, final Stack<Integer> topological) {
        visited.add(currentNode);

        List<Integer> adjList = adjMap.get(currentNode);
        if (adjList != null) {

            for (int adjacentNode : adjList) {
                if (!visited.contains(adjacentNode)) {
                    dfs(adjacentNode, visited, topological);
                }
            }
        }
        topological.push(currentNode);
    }

    public boolean hasCycle(Set<Integer> roots) {

        for (int sourceNode : roots) {
            final Set<Integer> visited = new HashSet<>();
            final Stack<Integer> path = new Stack<>();

            if (hasCycle(sourceNode, visited, path)) {
                return true;
            }
        }
        return false;
    }

    private boolean hasCycle(final int curNode, final Set<Integer> visited,
        final Stack<Integer> path) {

        visited.add(curNode);
        path.push(curNode);
        if (adjMap.get(curNode) != null) {
            for (final int adj : adjMap.get(curNode)) {

                if (path.contains(adj))
                    return true;

                if (!visited.contains(adj)) {
                    if (hasCycle(adj, visited, path)) {
                        return true;
                    }
                }
            }
        }
        path.pop();
        return false;
    }
}
