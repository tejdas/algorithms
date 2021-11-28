package net.lc.dfsbfs;

import java.util.*;

/**
 * This is NOT from LeetCode. Saw this in InterviewBit.
 * Given an Undirected Graph and a Source and Dest.
 * Find the shortest edge along the shortest path from S to D.
 *
 * BFS and DFS
 * Dijkstra Shortest Path
 */
public class ShortestEdgeInShortestPath {
    static class Edge {
        int to;
        int weight;

        public Edge(int to, int weight) {
            this.to = to;
            this.weight = weight;
        }
    }

    static class PathWeightToV implements Comparable<PathWeightToV> {
        int vertex;
        int pathWeight;

        public PathWeightToV(int vertex, int pathWeight) {
            this.vertex = vertex;
            this.pathWeight = pathWeight;
        }

        @Override
        public int compareTo(PathWeightToV o) {
            return Integer.compare(this.pathWeight, o.pathWeight);
        }
    }

    private List<Edge>[] graph = null;
    private int[] minDistance = null;
    private int minEdge = Integer.MAX_VALUE;
    private int dest = 0;
    private boolean[] visited;

    public int solve(final int A, final int[][] B, final int source, final int dest) {
        this.dest = dest;
        visited = new boolean[A+1];
        graph = new List[A + 1];
        for (int i = 1; i <= A; i++) {
            graph[i] = new ArrayList<>();
        }

        for (int[] e : B) {
            int v1 = e[0];
            int v2 = e[1];

            graph[v1].add(new Edge(v2, e[2]));
            graph[v2].add(new Edge(v1, e[2]));
        }

        minDistance = new int[A + 1];
        Arrays.fill(minDistance, Integer.MAX_VALUE);
        minDistance[source] = 0;

        /**
         * First run Dijkstra BFS algorithm to find shortest path.
         * Then run all-part DFS from S to D and keep track of shortest edge along ALL shortest path(s).
         * The reason for all-part DFS is that there could be multiple shortest paths.
         */
        PriorityQueue<PathWeightToV> pq = new PriorityQueue<>();
        pq.add(new PathWeightToV(source, 0));

        while (!pq.isEmpty()) {
            PathWeightToV curV = pq.remove();

            List<Edge> adjEdges = graph[curV.vertex];
            for (Edge e : adjEdges) {
                if (minDistance[e.to] > minDistance[curV.vertex] + e.weight) {
                    /**
                     * adjust shortest path to neighbor. Add it to PQ, if it is not destination.
                     */
                    minDistance[e.to] = minDistance[curV.vertex] + e.weight;

                    if (e.to != dest) {
                        pq.add(new PathWeightToV(e.to, minDistance[e.to]));
                    }
                }
            }
        }

        int curDistance = 0;
        int minSoFar = Integer.MAX_VALUE;
        dfs(source, curDistance, minSoFar);

        return minEdge;
    }

    /**
     * All-path DFS
     * Keep track of minSoFar in the call-stack.
     * @param cur
     * @param curDistance
     * @param minSoFar
     */
    private void dfs(int cur, int curDistance, int minSoFar) {
        if (cur == dest) {
            if (curDistance == minDistance[dest]) {
                minEdge = Math.min(minEdge, minSoFar);
            }
            return;
        }

        visited[cur] = true;
        List<Edge> adjEdges = graph[cur];

        for (Edge e : adjEdges) {
            /**
             * Only consider unvisited vertices that lead to shortest paths.
             */
            int to = e.to;
            if (!visited[to] && (curDistance + e.weight == minDistance[to])) {
                dfs(to, minDistance[to], Math.min(minSoFar, e.weight));
            }
        }
        visited[cur] = false; // enable all-path traversal.
    }

    public static void main(String[] args) {
        {
            int A = 7, C = 1, D = 3;

            int[][] B = new int[][] { { 1, 2, 2 }, { 2, 3, 8 }, { 1, 7, 3 }, { 7, 4, 2 }, { 4, 3, 5 }, { 1, 5, 3 },
                { 5, 3, 7 }, { 1, 6, 1 } };

            System.out.println(new ShortestEdgeInShortestPath().solve(A, B, C, D));
        }

        {
            int A = 4, C = 2, D = 4;

            int[][] B = new int[][] { { 1, 2, 1 }, { 2, 3, 1 }, { 1, 3, 2 }, { 3, 4, 1 } };

            System.out.println(new ShortestEdgeInShortestPath().solve(A, B, C, D));
        }

        {
            int A = 4, C = 2, D = 1;

            int[][] B = new int[][] { { 2, 3, 1 }, { 3, 1, 9 }, { 2, 4, 3 }, { 4, 1, 6 } };

            System.out.println(new ShortestEdgeInShortestPath().solve(A, B, C, D));
        }
    }
}
