package net.lc.topological;

import java.util.*;

/**
 * 207
 * DFS
 * Topological
 */
public class CourseSchedule {
    private final Map<Integer, List<Integer>> adjMap = new HashMap<>();

    private final Map<Integer, Set<Integer>> predMap = new HashMap<>();

    public boolean canFinish(int numCourses, int[][] prerequisites) {

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

            Set<Integer> predList = predMap.get(to);
            if (predList == null) {
                predList = new HashSet<>();
                predMap.put(to, predList);
            }
            predList.add(from);
        }

        return (!hasCycle(roots));
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
