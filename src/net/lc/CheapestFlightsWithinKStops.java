package net.lc;

import java.util.*;

/**
 * https://leetcode.com/problems/cheapest-flights-within-k-stops/
 * BFS shortest path
 * Shortest path and minimum stops (two SP variables to consider)
 * Dijkstra
 */
public class CheapestFlightsWithinKStops {
    static final class Edge {
        public Edge(final int from, final int to, final int cost) {
            this.from = from;
            this.to = to;
            this.cost = cost;
        }

        private int from;

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
        for (int i = 0; i < n; i++) adjMap.put(i, new ArrayList<>());

        if (flights.length == 0) return -1;

        for (int[] flight : flights) {
            Edge e = new Edge(flight[0], flight[1], flight[2]);

            adjMap.putIfAbsent(flight[0], new ArrayList<>());
            adjMap.get(flight[0]).add(e);
        }
        return shortestPath(n, src, dst, K);
    }

    private int shortestPath(int n, int src, int dest, int k) {
        Map<Integer, Integer> costMap = new HashMap<>();
        Map<Integer, Integer> hopMap = new HashMap<>();

        for (int i = 0; i < n; i++) {
            if (i == src) {
                costMap.put(i, 0);
                hopMap.put(i, 0);
            } else {
                costMap.put(i, Integer.MAX_VALUE);
                hopMap.put(i, Integer.MAX_VALUE);
            }

        }

        PriorityQueue<FlightCost> pq = new PriorityQueue<>();

        pq.add(new FlightCost(src, 0, 0));

        while (!pq.isEmpty()) {
            FlightCost cur = pq.poll();
            //System.out.println(cur.toString());

            if (cur.srcNode == dest) continue;

            if (cur.stop == k+1) continue;

            for (Edge adj : adjMap.get(cur.srcNode)) {

                int adjNode = adj.to;
                int newcost = cur.cost + adj.cost;
                int newstop = cur.stop + 1;

                if (costMap.get(adjNode) > newcost) {
                    costMap.put(adjNode, newcost);
                    pq.offer(new FlightCost(adjNode, newcost, newstop));
                }

                if (hopMap.get(adjNode) > newstop) {
                    hopMap.put(adjNode, newstop);
                    pq.offer(new FlightCost(adjNode, newcost, newstop));
                }
            }
        }

        return costMap.get(dest) == Integer.MAX_VALUE? -1 : costMap.get(dest);
    }
}
