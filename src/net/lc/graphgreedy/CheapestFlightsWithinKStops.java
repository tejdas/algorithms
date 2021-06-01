package net.lc.graphgreedy;

import java.util.*;

/**
 * https://leetcode.com/problems/cheapest-flights-within-k-stops/
 * BFS shortest path
 * Shortest path and minimum stops (two SP variables to consider)
 * Dijkstra
 * 787
 */
public class CheapestFlightsWithinKStops {
    static final class Edge {
        public Edge(final int to, final int cost) {
            this.to = to;
            this.cost = cost;
        }

        private int to;

        private final int cost;
    }

    static class FlightCost implements Comparable<FlightCost> {
        int srcNode;
        int cost;
        int stop;

        public FlightCost(int srcNode, int cost, int stop) {
            this.srcNode = srcNode;
            this.cost = cost;
            this.stop = stop;
        }

        @Override
        public int compareTo(FlightCost o) {
            if (o.cost == this.cost) return Integer.compare(this.stop, o.stop);
            return Integer.compare(this.cost, o.cost);
        }

        @Override
        public String toString() {
            return "FlightCost{" + "srcNode=" + srcNode + ", cost=" + cost + ", stop=" + stop + '}';
        }
    }

    private final Map<Integer, List<Edge>> adjMap = new HashMap<>();
    public int findCheapestPrice(int n, int[][] flights, int src, int dst, int K) {
        adjMap.clear();
        if (flights.length == 0) return -1;

        for (int i = 0; i < n; i++) adjMap.put(i, new ArrayList<>());

        for (int[] flight : flights) {
            Edge e = new Edge(flight[1], flight[2]);
            adjMap.get(flight[0]).add(e);
        }
        return shortestPath(n, src, dst, K);
    }

    private int shortestPath(int n, int src, int dest, int k) {
        int[][] greedyMap = new int[n+1][n+1];
        for (int i = 0; i < n; i++) {
            if (i == src) {
                Arrays.fill(greedyMap[i], 0);
            } else {
                Arrays.fill(greedyMap[i], Integer.MAX_VALUE);
            }
        }

        PriorityQueue<FlightCost> pq = new PriorityQueue<>();

        pq.add(new FlightCost(src, 0, 0));

        while (!pq.isEmpty()) {
            FlightCost cur = pq.poll();

            if (cur.srcNode == dest) continue;
            int curstop = cur.stop;

            if (cur.stop > k) {
                // do not consider
                continue;
            }

            List<Edge> adjEdges = adjMap.get(cur.srcNode);
            for (Edge e : adjEdges) {
                int adjNode = e.to;

                if (greedyMap[adjNode][curstop+1] > greedyMap[cur.srcNode][curstop] + e.cost) {
                    greedyMap[adjNode][curstop+1] = greedyMap[cur.srcNode][curstop] + e.cost;

                    pq.add(new FlightCost(adjNode, greedyMap[adjNode][curstop+1], curstop+1));
                }
            }
        }

        int res = Integer.MAX_VALUE;
        for (int i = 0; i <=k+1; i++) {
            res = Math.min(greedyMap[dest][i], res);
        }
        return res==Integer.MAX_VALUE? -1 : res;
    }

    public static void main(String[] args) {

        {
            int n = 11;
            int[][] flights = new int[][] { { 0, 3, 3 }, { 3, 4, 3 }, { 4, 1, 3 }, { 0, 5, 1 }, { 5, 1, 100 },
                { 0, 6, 2 }, { 6, 1, 100 }, { 0, 7, 1 }, { 7, 8, 1 }, { 8, 9, 1 }, { 9, 1, 1 }, { 1, 10, 1 },
                { 10, 2, 1 }, { 1, 2, 100 } };

            System.out.println(new CheapestFlightsWithinKStops().findCheapestPrice(n, flights, 0, 2, 4));
        }

        {
            int n = 3;
            int[][] flights = new int[][] { {0,1,100},{1,2,100},{0,2,500} };

            System.out.println(new CheapestFlightsWithinKStops().findCheapestPrice(n, flights, 0, 2, 1));
        }

        {
            int n = 3;
            int[][] flights = new int[][] { {0,1,100},{1,2,100},{0,2,500} };

            System.out.println(new CheapestFlightsWithinKStops().findCheapestPrice(n, flights, 0, 2, 0));
        }

        {
            int n = 5;
            int[][] flights = new int[][] { {1,2,10},{2,0,7}, {1,3,8},{4,0,10},{3,4,2},{4,2,10},{0,3,3},{3,1,6},{2,4,5}};

            System.out.println(new CheapestFlightsWithinKStops().findCheapestPrice(n, flights, 0, 4,1));

            /*
            5
[[1,2,10],[2,0,7],[1,3,8],[4,0,10],[3,4,2],[4,2,10],[0,3,3],[3,1,6],[2,4,5]]
0
4
1


             */
        }
    }
}
