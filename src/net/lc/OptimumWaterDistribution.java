package net.lc;

import java.util.*;

/**
 * https://leetcode.com/problems/optimize-water-distribution-in-a-village/submissions/
 * MST (minimum spanning tree)
 * Prim's algorithm
 */
public class OptimumWaterDistribution {

    static class Edge implements Comparable<Edge> {
        int node1;
        int node2;
        int cost;

        public Edge(int node1, int node2, int cost) {
            this.node1 = node1;
            this.node2 = node2;
            this.cost = cost;
        }

        public int other(int n) {
            if (n == node1) return node2; else return node1;
        }

        @Override
        public int compareTo(Edge o) {
            return Integer.compare(this.cost, o.cost);
        }
    }

    private List[] adjMap;
    public int minCostToSupplyWater(int n, int[] wells, int[][] pipes) {
        adjMap = new List[n+1];
        buildGraph(wells, pipes);

        PriorityQueue<Edge> pq = new PriorityQueue<>();
        List<Edge> l = (List<Edge>) adjMap[0];
        for (Edge e : l) {
            pq.add(e);
        }

        int cost = 0;
        int edgesAdded = 0;
        boolean[] visited = new boolean[n+1];
        Arrays.fill(visited, false);

        visited[0] = true;

        while (edgesAdded < n) {
            Edge e = pq.remove();
            int unvisitedNode;

            if (visited[e.node1] && visited[e.node2]) {
                continue;
            } else {
                unvisitedNode = visited[e.node1] ? e.node2 : e.node1;
            }

            visited[unvisitedNode] = true;
            cost += e.cost;
            edgesAdded++;

            List<Edge> ll = (List<Edge>) adjMap[unvisitedNode];
            for (Edge ee : ll) {
                int other = ee.other(unvisitedNode);
                if (!visited[other]) {
                    pq.add(ee);
                }
            }
        }
        return cost;
    }

    private void buildGraph(int[] wells, int[][] pipes) {
        adjMap[0] = new ArrayList<>();
        List<Edge> list = adjMap[0];
        for (int i = 0; i < wells.length; i++) {
            list.add(new Edge(0, i+1, wells[i]));
            adjMap[i+1] = new ArrayList<>();
        }

        for (int[] pipe : pipes) {
            Edge e = new Edge(pipe[0], pipe[1], pipe[2]);
            adjMap[pipe[0]].add(e);
            adjMap[pipe[1]].add(e);
        }
    }

    public static void main(String[] args) {
        {
            int n = 3;
            int[] wells = { 1, 2, 2 };
            int[][] pipes = { { 1, 2, 1 }, { 2, 3, 1 } };

            //System.out.println(new OptimumWaterDistribution().minCostToSupplyWater(n, wells, pipes));
        }

        {
            int n = 6;
            int[] wells = {4625,65696,86292,68291,37147,7880};


            int[][] pipes = { {2,1,79394},{3,1,45649},{4,1,75810},{5,3,22340},{6,1,6222}};

            System.out.println(new OptimumWaterDistribution().minCostToSupplyWater(n, wells, pipes));
        }
    }
}
