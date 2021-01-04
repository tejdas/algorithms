package algo.graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public class DijkstraDirectedGraphShortestPath {
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

    /**
     * Shortest path to Vertex from Source
     */
    static final class PathToVertex implements Comparable<PathToVertex> {
        public PathToVertex(final int vertex, final int pathWeight) {
            this.vertex = vertex;
            this.pathWeight = pathWeight;
        }

        int vertex;
        int pathWeight;

        @Override
        public int compareTo(final PathToVertex that) {
            return Integer.compare(this.pathWeight, that.pathWeight);
        }
    }

    private final int vertices;

    private final Map<Integer, List<DEdge>> adjacentEdges = new HashMap<>();

    private int edgeCount = 0;

    public DijkstraDirectedGraphShortestPath(final int vertices) {
        this.vertices = vertices;
        for (int i = 0; i < vertices; i++) {
            adjacentEdges.put(i, new ArrayList<DEdge>());
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

        final PriorityQueue<PathToVertex> pq = new PriorityQueue<>();
        pq.add(new PathToVertex(source, 0));

        final boolean[] visited = new boolean[vertices];
        Arrays.fill(visited, false);

        final Map<Integer, Integer> shortestPathMap = new HashMap<>();
        for (int i = 0; i < vertices; i++) {
            if (i == source) {
                shortestPathMap.put(i, 0);
            } else {
                shortestPathMap.put(i, Integer.MAX_VALUE);
            }
        }

        while (!pq.isEmpty()) {
            final PathToVertex pathTo = pq.poll();
            final int curNode = pathTo.vertex;
            if (visited[curNode])
                continue;

            visited[curNode] = true;
            final int pathLenToCurNode = shortestPathMap.get(curNode);

            for (final DEdge de : adjacentEdges.get(curNode)) {
                final int to = de.to;

                int pathLengthToAdjacentNode = shortestPathMap.get(to);

                /**
                 * If path to adjacent node is shorter via current node, update
                 * the shortest path to adjacent node.
                 */
                if (pathLengthToAdjacentNode > (pathLenToCurNode + de.weight)) {
                    pathLengthToAdjacentNode = pathLenToCurNode + de.weight;
                    shortestPathMap.put(to, pathLengthToAdjacentNode);
                }

                /**
                 * Put the adjacent node into PriorityQueue, if it is not
                 * visited yet.
                 */
                if (!visited[to]) {
                    pq.offer(new PathToVertex(to, pathLengthToAdjacentNode));
                }
            }
        }
        return shortestPathMap;
    }

    public static void main(final String[] args) {
        final DijkstraDirectedGraphShortestPath g = new DijkstraDirectedGraphShortestPath(8);
        g.addEdge(0, 1, 10);
        g.addEdge(0, 2, 5);
        g.addEdge(0, 3, 15);
        g.addEdge(1, 2, 4);
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
