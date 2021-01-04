package algo.graph;

import java.util.*;

public class CourseSchedule {

    public static void main(String[] args) {
        {
            CourseSchedule cs = new CourseSchedule();
            int[] ret = cs.findOrder(4, new int[][]  {{1,0},{2,0},{3,1},{3,2}});
            System.out.println(Arrays.toString(ret));
        }
/*
        {
            CourseSchedule cs = new CourseSchedule();
            int[] ret = cs.findOrder(2, new int[][] {{1, 0}});
            System.out.println(Arrays.toString(ret));
        }

        {
            CourseSchedule cs = new CourseSchedule();
            int[] ret = cs.findOrder(2, new int[][] {{0, 1}, {1, 0}});
            System.out.println(Arrays.toString(ret));
        }
        */
    }

    private final Map<Integer, List<Integer>> adjMap = new HashMap<>();

    private final Map<Integer, Set<Integer>> predMap = new HashMap<>();

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

            Set<Integer> predList = predMap.get(to);
            if (predList == null) {
                predList = new HashSet<>();
                predMap.put(to, predList);
            }
            predList.add(from);
        }

        if (hasCycle(roots)) {
            return new int[] {};
        }

        List<Integer> top = getTopological(roots);
        if (top.isEmpty()) {
            return new int[] {};
        }

        int idx = 0;
        int[] ret = new int[top.size()];
        for (int val : top) {
            ret[idx++] = val;
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

    private List<Integer> getTopological(Set<Integer> roots) {
        List<Integer> result = new ArrayList<>();

        Queue<Integer> q = new LinkedList<>();
        q.addAll(roots);

        Set<Integer> visited = new HashSet<>();

        while (!q.isEmpty()) {
            Integer node = q.remove();
            if (visited.contains(node)) {
                continue;
            }
            result.add(node);
            visited.add(node);

            if (adjMap.get(node) != null) {
                for (int adjNode : adjMap.get(node)) {
                    if (!visited.contains(adjNode)) {
                        Set<Integer> predSet = predMap.get(adjNode);
                        predSet.remove(node);
                        if (predSet.isEmpty()) {
                            q.add(adjNode);
                        }
                    }
                }
            }
        }

        return result;
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
