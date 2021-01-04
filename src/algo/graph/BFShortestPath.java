package algo.graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Bellman-Ford algorithm
 */
public class BFShortestPath {

    static final class DEdge {
        public DEdge(final int from, final int to, final int weight) {
            this.from = from;
            this.to = to;
            this.weight = weight;
        }

        private final int from;

        private final int to;

        private final int weight;

        public int from() {
            return from;
        }

        public int to() {
            return to;
        }

        public int other(final int v) {
            if (v == from)
                return to;
            else
                return from;
        }

        public int getWeight() {
            return weight;
        }

        @Override
        public String toString() {
            return String.format("%d -->  %d  %f", from, to, weight);
        }
    }

    private final int vertices;

    private final Map<Integer, List<DEdge>> adjacentEdges = new HashMap<>();

    private int edgeCount = 0;

    public BFShortestPath(final int vertices) {
        this.vertices = vertices;
        for (int i = 0; i < vertices; i++) {
            adjacentEdges.put(i, new ArrayList<>());
        }
    }

    public int getEdgeCount() {
        return edgeCount;
    }

    public void addEdge(final int from, final int to, final int weight) {
        if (from >= vertices || to >= vertices) {
            return;
        }
        final DEdge e = new DEdge(from, to, weight);
        adjacentEdges.get(from).add(e);
        edgeCount++;
    }

    public Map<Integer, Integer> findShortestPath(final int source) {

        final Map<Integer, Integer> shortestPathMap = new HashMap<>();
        for (int i = 0; i < vertices; i++) {
            if (i == source) {
                shortestPathMap.put(i,  0);
            } else {
                shortestPathMap.put(i,  Integer.MAX_VALUE);
            }
        }

        /**
         * For n edges in the DirectedGraph, iterate n times
         */
        for (int iter = 0; iter < adjacentEdges.size(); iter++) {

            /*
             * Visit each vertex, and update the shortest path to its adjacent vertices.
             */
            for (int v = 0; v < vertices; v++) {
                final int pathToCurVertex = shortestPathMap.get(v);

                final List<DEdge> adjEdges = adjacentEdges.get(v);
                for (final DEdge edge : adjEdges) {
                    final int adjVertex = edge.to;

                    /**
                     * Update the shortest path to adjVertex, if a shorter path is found
                     * via current vertex.
                     */
                    if (pathToCurVertex + edge.weight < shortestPathMap.get(adjVertex)) {
                        shortestPathMap.put(adjVertex, pathToCurVertex + edge.weight);
                    }
                }
            }
        }

        return shortestPathMap;
    }

    public static void main(final String[] args) {
        final BFShortestPath g = new BFShortestPath(8);
        g.addEdge(0, 1, 10);
        g.addEdge(0, 2, 5);
        g.addEdge(0, 3, 15);
        g.addEdge(1, 2, 4);
        g.addEdge(1, 3, -8);
        g.addEdge(1, 4, 9);
        g.addEdge(1, 5, 15);
        g.addEdge(2, 3, 4);
        g.addEdge(2, 5, 8);
        g.addEdge(3, 6, 16);

        g.addEdge(4, 5, 15);
        g.addEdge(4, 7, 10);
        g.addEdge(5, 6, 15);
        g.addEdge(5, 7, 10);
        g.addEdge(6, 2, 6);
        g.addEdge(6, 7, 10);
        final Map<Integer, Integer> shortestPaths = g.findShortestPath(0);
        for (final Map.Entry<Integer, Integer> entry : shortestPaths.entrySet()) {
            System.out.println(entry.getKey() + "  " + entry.getValue());
        }
    }
}
