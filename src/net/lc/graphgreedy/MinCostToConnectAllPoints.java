package net.lc.graphgreedy;

import java.util.*;

/**
 * 1584
 * Minimum spanning Tree
 * Union-Find
 * Kruskal's algorithm
 */
public class MinCostToConnectAllPoints {
    private static int hash(int[] p) {
        return (p[0] << 16) ^ p[1];
    }

    static class Edge implements Comparable<Edge> {
        int pointa;
        int pointb;

        int dist;

        public Edge(int pointa, int pointb, int dist) {
            this.pointa = pointa;
            this.pointb = pointb;
            this.dist = dist;
        }

        @Override
        public int compareTo(Edge o) {
            return Integer.compare(this.dist, o.dist);
        }
    }

    private final Map<Integer, Integer> rootMap = new HashMap<>();

    int getRoot(int node) {
        int v = node;
        while (true) {
            int root = rootMap.get(v);
            if (root == v) {
                rootMap.put(node, root);
                return root;
            } else {
                v = root;
            }
        }
    }

    /**
     * MinSpanningTree of n vertices := n-1 edges need to be chosen.
     * Add all the Edge into PQ.
     * Process smaller edges first.
     * Form a Union-set.
     * Make sure that we do not get a cycle by adding edge.
     *
     * Lets say: edge has two vertices v1 and v2
     * Each vertex is its own root.
     *
     * Find v1's root and v2's root.
     * If they are not same, then make one's root same as other (union), and pick the edge.
     * If the roots are same, then adding the edge would create cycle; so avoid the edge.
     *
     * @param points
     * @return
     */
    public int minCostConnectPoints(int[][] points) {
        PriorityQueue<Edge> pq = new PriorityQueue<>();
        for (int i = 0; i < points.length; i++) {
            for (int j = i+1; j < points.length; j++) {
                int[] p1 = points[i];
                int[] p2 = points[j];
                int dist = Math.abs(p1[0] - p2[0]) + Math.abs(p1[1] - p2[1]);

                pq.add(new Edge(hash(p1), hash(p2), dist));
            }
        }

        int edgesChosen = 0;
        int mstCost = 0;
        while (edgesChosen < points.length-1) {
            Edge edge = pq.remove();
            int va = edge.pointa;
            int vb = edge.pointb;

            if (!rootMap.containsKey(va)) rootMap.put(va, va);
            if (!rootMap.containsKey(vb)) rootMap.put(vb, vb);

            int r1 = getRoot(va);
            int r2 = getRoot(vb);

            if (r1 != r2) {
                rootMap.put(r2, r1);
                mstCost += edge.dist;
                edgesChosen++;
            } else {
                /**
                 * since the root for v1 and v2 are the same, adding the edge could create a cycle.
                 * Therefore, skip the edge.
                 */
                continue;
            }
        }

        return mstCost;
    }

    public static void main(String[] args) {
        {
            int[][] points = { { 0, 0 }, { 2, 2 }, { 3, 10 }, { 5, 2 }, { 7, 0 } };
            //System.out.println(new MinCostToConnectAllPoints().minCostConnectPoints(points));
        }

        {
            int[][] points = {{-303,-579},{635,-412},{-865,884},{283,445},{17,323},{533,-450},{-129,-732},{-105,894},{451,749},{843,874},{-392,-943},{214,700},{194,220},{-230,-664},{530,49},{-802,564},{796,-731},{335,370},{-708,935},{-226,363},{-55,-980},{-776,-889},{-667,-480},{-676,400},{-640,884},{-602,743},{144,945},{530,-31},{6,462},{819,-50},{33,-749},{757,-653},{-571,229},{-1000,-662},{-231,-316},{-594,-143},{9,-5},{-835,-18},{695,920},{258,967},{-471,-990},{-203,833},{53,670},{594,-271},{238,-523},{-581,118},{459,395},{-816,-499},{35,348},{-160,-346},{836,-11},{278,290}};
            System.out.println(new MinCostToConnectAllPoints().minCostConnectPoints(points));
        }
    }
}
