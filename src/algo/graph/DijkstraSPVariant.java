package algo.graph;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public class DijkstraSPVariant {

    static final class Edge {
        public Edge(final int from, final int to, final long weight) {
            this.from = from;
            this.to = to;
            this.weight = weight;
        }

        private final int from;

        private final int to;

        private final long weight;

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

        public long getWeight() {
            return weight;
        }
    }

    static final class PathToVertex implements Comparable<PathToVertex> {
        public PathToVertex(final int vertex, final long pathWeight) {
            this.vertex = vertex;
            this.pathWeight = pathWeight;
        }

        int vertex;
        long pathWeight;

        @Override
        public int compareTo(final PathToVertex that) {
            return Long.compare(this.pathWeight, that.pathWeight);
        }
    }

    private final int vertices;

    private final Map<Integer, List<Edge>> adjacentEdges = new HashMap<>();

    private int edgeCount = 0;

    public DijkstraSPVariant(final int vertices) {
        this.vertices = vertices;
        for (int i = 1; i <= vertices; i++) {
            adjacentEdges.put(i, new ArrayList<>());
        }
    }

    public int getEdgeCount() {
        return edgeCount;
    }

    public void addEdge(final int from, final int to, final long weight) {
        if (from > vertices || to > vertices) {
            return;
        }
        final Edge e = new Edge(from, to, weight);
        // System.out.println(e.toString());
        adjacentEdges.get(from).add(e);
        adjacentEdges.get(to).add(e);
        edgeCount++;
    }

    public Map<Integer, Long> findShortestPath(final int source) {
        final boolean[] visited = new boolean[vertices + 1];
        Arrays.fill(visited, false);

        final Map<Integer, Long> shortestPathMap = new HashMap<>();
        for (int i = 1; i <= vertices; i++) {
            if (i == source) {
                shortestPathMap.put(i, 0L);
            } else {
                shortestPathMap.put(i, Long.MAX_VALUE);
            }
        }

        final PriorityQueue<PathToVertex> pq = new PriorityQueue<>();
        pq.add(new PathToVertex(source, 0));
        while (!pq.isEmpty()) {
            final PathToVertex pathTo = pq.poll();
            final int curNode = pathTo.vertex;
            if (visited[curNode])
                continue;

            visited[curNode] = true;

            final long pathLenToCurNode = shortestPathMap.get(curNode);

            for (final Edge de : adjacentEdges.get(curNode)) {
                final int to = de.other(curNode);

                long pathLengthToAdjacentNode = shortestPathMap.get(to);

                long delta = de.weight - pathLenToCurNode;
                if (delta < 0) {
                    delta = 0;
                }

                long expectedPathLengthToAdjNode = pathLenToCurNode + delta;

                /**
                 * If path to adjacent node is shorter via current node, update
                 * the shortest path to adjacent node.
                 */
                if (pathLengthToAdjacentNode > expectedPathLengthToAdjNode) {
                    pathLengthToAdjacentNode = expectedPathLengthToAdjNode;
                    shortestPathMap.put(to, pathLengthToAdjacentNode);
                }

                /*
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

    public static void main(String[] args) throws IOException {
        BufferedReader bfr = new BufferedReader(new InputStreamReader(System.in));
        String[] temp = bfr.readLine().split(" ");
        int N = Integer.parseInt(temp[0]);
        int E = Integer.parseInt(temp[1]);

        if (N < 1 || N > 50000 || E < 1 || E > 500000) {
            return;
        }

        DijkstraSPVariant solution = new DijkstraSPVariant(N);

        for (int i = 0; i < E; i++) {

            temp = bfr.readLine().split(" ");
            int v1 = Integer.parseInt(temp[0]);
            int v2 = Integer.parseInt(temp[1]);
            long weight = Long.parseLong(temp[2]);

            if (weight < 0 || v1 < 1 || v2 < 1 || v1 > N || v2 > N) {
                System.out.println(0);
                return;
            }

            solution.addEdge(v1, v2, weight);
        }

        Map<Integer, Long> map = solution.findShortestPath(1);

        Long val = map.get(N);
        if (val == Long.MAX_VALUE) {
            System.out.println("NO PATH EXISTS");
        } else {
            System.out.println(map.get(N));
        }

        bfr.close();
    }
}