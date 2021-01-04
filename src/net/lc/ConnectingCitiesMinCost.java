package net.lc;

import java.util.*;

/**
 * https://leetcode.com/problems/connecting-cities-with-minimum-cost/submissions/
 * minimum spanning tree
 */
public class ConnectingCitiesMinCost {

    static class Edge implements Comparable<Edge> {
        int v1;
        int v2;
        int cost;

        public Edge(int v1, int v2, int cost) {
            this.v1 = v1;
            this.v2 = v2;
            this.cost = cost;
        }

        @Override
        public int compareTo(Edge o) {
            return Integer.compare(this.cost, o.cost);
        }

        public int other(int v) {
            if (v == v1) return v2; else return v1;
        }
    }

    private List[] edgeMap;

    private boolean[] visited;
    private int visitedNodes = 0;

    public int minimumCost(int N, int[][] connections) {

        visited = new boolean[N+1];

        edgeMap = new List[N+1];
        for (int i = 1; i <= N; i++) {
            edgeMap[i] = new ArrayList<Edge>();
        }

        Set<Integer> seenV = new HashSet<>();
        for (int[] conn : connections) {
            Edge e = new Edge(conn[0], conn[1], conn[2]);
            seenV.add(conn[0]);
            seenV.add(conn[1]);

            int v1 = conn[0], v2 = conn[1];

            edgeMap[v1].add(e);
            edgeMap[v2].add(e);
        }

        if (seenV.size() < N) return -1;

        int startNode = 1;
        /*
        dfs(startNode);

        if (visitedNodes < N) return -1;
*/
        int totalCost = 0;

        Arrays.fill(visited, false);

        PriorityQueue<Edge> pq = new PriorityQueue<>();
        visited[startNode] = true;
        List<Edge> edges = edgeMap[startNode];

        for (Edge e : edges) pq.add(e);

        int edgesAdded = 0;
        while (!pq.isEmpty()) {
            Edge curEdge = pq.remove();

            int v1 = curEdge.v1;
            int v2 = curEdge.v2;

            if (visited[v1] && visited[v2]) continue;

            totalCost += curEdge.cost;

            edgesAdded++;
            if (edgesAdded == N-1) break;

            int nextV = visited[v1] ? v2 : v1;
            visited[nextV] = true;

            List<Edge> adjEdges = edgeMap[nextV];
            for (Edge e : adjEdges) pq.add(e);
        }

        if (edgesAdded < N-1) return -1;
        return totalCost;
    }

    private void dfs(int v) {
        if (visited[v]) return;

        visited[v] = true;
        visitedNodes++;

        List<Edge> edges = edgeMap[v];

        for (Edge edge : edges) {

            int adj = edge.other(v);
            if (!visited[adj]) {
                dfs(adj);
            }
        }
    }

    public static void main(String[] args) {
        {
            int[][] connections = { { 1, 2, 5 }, { 1, 3, 6 }, { 2, 3, 1 } };
            System.out.println(new ConnectingCitiesMinCost().minimumCost(3, connections));
        }

        {
            int[][] connections = { {1,2,3}, {3,4,4}};
            System.out.println(new ConnectingCitiesMinCost().minimumCost(4, connections));
        }

        {
            int[][] connections = {{2,1,87129},{3,1,14707},{4,2,34505},{5,1,71766},{6,5,2615},{7,2,37352}};
            System.out.println(new ConnectingCitiesMinCost().minimumCost(7, connections));
        }
    }
}
