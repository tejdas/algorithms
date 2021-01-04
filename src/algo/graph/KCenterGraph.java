package algo.graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

public class KCenterGraph {

    static final class Edge implements Comparable<Edge> {
        public Edge(final int v1, final int v2, final double weight) {
            super();
            this.v1 = v1;
            this.v2 = v2;
            this.weight = weight;
        }

        private final int v1;

        private final int v2;

        private final double weight;

        @Override
        public int compareTo(final Edge that) {
            return Double.compare(this.weight,  that.weight);
        }

        public int either() {
            return v1;
        }

        public int other(final int v) {
            if (v == v1)
                return v2;
            else
                return v1;
        }

        public double getWeight() {
            return weight;
        }

        @Override
        public String toString() {
            return String.format("%d  %d  %f", v1, v2, weight);
        }

        @Override
        public boolean equals(final Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof Edge)) return false;
            final Edge that = (Edge) obj;
            return ((this.weight == that.weight) &&
                    (this.v1==that.v1 && this.v2==that.v2 ||
                    this.v1==that.v2 && this.v2==that.v1));
        }
    }

    private final int vertices;

    private final Map<Integer, List<Edge>> adjacentEdges = new HashMap<>();

    private int edgeCount = 0;

    public KCenterGraph(final int vertices) {
        this.vertices = vertices;
        for (int v = 0; v < vertices; v++) {
            adjacentEdges.put(v, new ArrayList<Edge>());
        }
    }

    public int getEdgeCount() {
        return edgeCount;
    }

    public void addEdge(final int v1, final int v2) {
        addEdge(v1, v2, 0);
    }

    public void addEdge(final int v1, final int v2, final double weight) {
        if (v1 >= vertices || v2 >= vertices) {
            return;
        }
        final Edge e = new Edge(v1, v2, weight);
        adjacentEdges.get(v1).add(e);
        adjacentEdges.get(v2).add(e);
        edgeCount++;
    }

    public void kCenter(final int k) {
        final boolean[] visited = new boolean[vertices];
        Arrays.fill(visited, false);

        final PriorityQueue<Edge> pq = new PriorityQueue<>();

        /*
         * Put all the Edges in PriorityQueue
         */
        for (int curNode = 0; curNode < vertices; curNode++) {
            if (visited[curNode]) {
                continue;
            }

            visited[curNode] = true;
            final List<Edge> edges = adjacentEdges.get(curNode);
            for (final Edge e : edges) {
                final int v = e.other(curNode);
                if (!visited[v]) {
                    pq.offer(e);
                }
            }
        }

        final Set<Integer> chosenVertices = new HashSet<>();

        while (!pq.isEmpty() && chosenVertices.size() < k) {
            final Edge e = pq.poll();
            final int v1 = e.either();
            final int v2 = e.other(v1);
            if (!chosenVertices.contains(v1)) {
                chosenVertices.add(v1);

                if (chosenVertices.size() == k) {
                    break;
                }
            }

            if (!chosenVertices.contains(v2)) {
                chosenVertices.add(v2);
            }
        }

        for (final int v : chosenVertices) {
            System.out.println(v);
        }
    }


    public static void main(final String[] args) {
        final int vertices = 9;
        final KCenterGraph g = new KCenterGraph(vertices);

        g.addEdge(0, 1, 10);
        g.addEdge(0, 2, 7);
        g.addEdge(0, 3, 6);
        g.addEdge(1, 2, 8);
        g.addEdge(1, 3, 5);
        g.addEdge(2, 3, 12);

        g.kCenter(2);

    }
}
