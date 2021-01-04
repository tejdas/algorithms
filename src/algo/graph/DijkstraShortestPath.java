package algo.graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

public class DijkstraShortestPath {

    static final class Edge {
        public Edge(final String from, final String to, final int weight) {
            this.from = from;
            this.to = to;
            this.weight = weight;
        }

        private final String from;

        private final String to;

        private final int weight;

        public String other(final String v) {
            if (v.equals(from)) {
                return to;
            } else {
                return from;
            }
        }

        public int getWeight() {
            return weight;
        }
    }

    static final class PathToVertex implements Comparable<PathToVertex> {
        public PathToVertex(final String vertex, final int pathWeight) {
            this.vertex = vertex;
            this.pathWeight = pathWeight;
        }

        String vertex;
        int pathWeight;

        @Override
        public int compareTo(final PathToVertex that) {
            return Integer.compare(this.pathWeight, that.pathWeight);
        }
    }

    /**
     * Adjacency-List representation: Key (Vertex) => Value (List of adjacent
     * edges)
     */
    private final Map<String, List<Edge>> adjacentEdges = new HashMap<>();

    private final Set<String> vertices = new HashSet<>();

    public DijkstraShortestPath(Set<String> vertices) {
        this.vertices.addAll(vertices);
        for (final String vertexName : vertices) {
            adjacentEdges.put(vertexName, new ArrayList<>());
        }
    }

    public void addEdge(final String from, final String to, final int weight) {
        final Edge e = new Edge(from, to, weight);
        /*
         * Put the edge on the adjacentEdgeList of both vertices
         */
        adjacentEdges.get(from).add(e);
        adjacentEdges.get(to).add(e);
    }

    public Map<String, Integer> findShortestPath(final String source) {

        /*
         * Initialize shortest path for all vertices to Integer.MAX_VALUE,
         * except for source, which is initialized to 0.
         */
        final Map<String, Integer> shortestPathMap = new HashMap<>();
        for (final String v : vertices) {
            if (v.equals(source)) {
                shortestPathMap.put(v, 0);
            } else {
                shortestPathMap.put(v, Integer.MAX_VALUE);
            }
        }

        /**
         * Use a PriorityQueue to sort vertices in the order of closeness to
         * source.
         */
        final PriorityQueue<PathToVertex> pq = new PriorityQueue<>();
        pq.add(new PathToVertex(source, 0));

        /**
         * Perform Breadth-first-search (BFS) and use the following
         * data-structure to keep track of visited nodes
         */
        final Set<String> visitedNodes = new HashSet<>();

        while (!pq.isEmpty()) {
            final PathToVertex pathTo = pq.poll();
            final String curNode = pathTo.vertex;
            if (visitedNodes.contains(curNode)) {
                continue;
            }

            visitedNodes.add(curNode);

            final int pathLenToCurNode = shortestPathMap.get(curNode);

            for (final Edge edge : adjacentEdges.get(curNode)) {
                final String adjNode = edge.other(curNode);

                int pathLengthToAdjacentNode = shortestPathMap.get(adjNode);

                /**
                 * Re-evaluate shortest path: If path to adjacent node is
                 * shorter via current node, update the shortest path to
                 * adjacent node.
                 */
                if (pathLengthToAdjacentNode > (pathLenToCurNode + edge.weight)) {
                    pathLengthToAdjacentNode = pathLenToCurNode + edge.weight;
                    shortestPathMap.put(adjNode, pathLengthToAdjacentNode);
                }

                /**
                 * Put the adjacent node into PriorityQueue, if it is not
                 * visited yet.
                 */
                if (!visitedNodes.contains(adjNode)) {
                    pq.offer(new PathToVertex(adjNode, pathLengthToAdjacentNode));
                }
            }
        }
        return shortestPathMap;
    }

    public static void main(final String[] args) {
        final Set<String> vertices = new LinkedHashSet<>(Arrays.asList("A", "B", "C", "D", "E"));
        final DijkstraShortestPath g = new DijkstraShortestPath(vertices);

        g.addEdge("A", "B", 2);
        g.addEdge("A", "D", 6);
        g.addEdge("A", "E", 3);
        g.addEdge("A", "B", 2);
        g.addEdge("B", "C", 5);
        g.addEdge("B", "D", 1);
        g.addEdge("C", "D", 7);
        g.addEdge("C", "E", 4);

        System.out.println("Shortest Paths");
        for (final String vertex : vertices) {
            final Map<String, Integer> shortestPaths = g.findShortestPath(vertex);
            for (final Map.Entry<String, Integer> entry : shortestPaths.entrySet()) {
                System.out.println(vertex + " ===> " + entry.getKey() + "  ShortestPath: " + entry.getValue());
            }
            System.out.println();
        }
    }
}
