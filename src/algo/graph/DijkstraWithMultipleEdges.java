package algo.graph;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.PriorityQueue;

public class DijkstraWithMultipleEdges {

    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(in.readLine());
        for (int a0 = 0; a0 < t; a0++) {

            edgeMap.clear();

            String str = in.readLine();
            String[] arr = str.split(" ");

            int vertexCount = Integer.parseInt(arr[0]);
            int numEdges = Integer.parseInt(arr[1]);

            DijkstraWithMultipleEdges.vertices = vertexCount;
            initialize();

            for (int a1 = 0; a1 < numEdges; a1++) {

                str = in.readLine();
                arr = str.split(" ");

                int x = Integer.parseInt(arr[0]);
                int y = Integer.parseInt(arr[1]);
                int r = Integer.parseInt(arr[2]);

                addEdge(x, y, r);
            }
            int s = Integer.parseInt(in.readLine());

            for (Entry<EPair, Integer> entry : edgeMap.entrySet()) {
                addDEdge(entry.getKey().v1, entry.getKey().v2, entry.getValue());
            }

            Map<Integer, Integer> output = findShortestPath(s);
            printOutput(vertexCount, s, output);
        }

        in.close();
    }

    private static void printOutput(int n, int s, Map<Integer, Integer> output) {
        for (int i = 1; i <= n; i++) {
            if (i != s) {
                if (output.get(i) == Integer.MAX_VALUE) {
                    System.out.print(-1 + " ");
                } else {
                    System.out.print(output.get(i) + " ");
                }
            }
        }
        System.out.println();
    }

    static class EPair {
        public EPair(int v1, int v2) {
            super();
            this.v1 = v1;
            this.v2 = v2;
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + v1;
            result = prime * result + v2;
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            EPair other = (EPair) obj;

            return (this.v1 == other.v1 && this.v2 == other.v2);
        }

        int v1;
        int v2;
    }

    static final Map<EPair, Integer> edgeMap = new HashMap<>();

    static void addEdge(int v1, int v2, int weight) {
        EPair pa = new EPair(v1, v2);
        EPair pb = new EPair(v2, v1);

        if (edgeMap.containsKey(pa)) {
            int curweight = edgeMap.get(pa);
            if (weight < curweight) {
                edgeMap.put(pa, weight);
            }
        } else if (edgeMap.containsKey(pb)) {
            int curweight = edgeMap.get(pb);
            if (weight < curweight) {
                edgeMap.put(pb, weight);
            }
        } else {
            edgeMap.put(pa, weight);
        }
    }

    static final class Edge {
        public Edge(final int from, final int to, final int weight) {
            this.vertex1 = from;
            this.vertex2 = to;
            this.weight = weight;
        }

        private final int vertex1;

        private final int vertex2;

        private final int weight;

        public int other(final int v) {
            if (v == vertex1)
                return vertex2;
            else
                return vertex1;
        }

        public int getWeight() {
            return weight;
        }

        @Override
        public String toString() {
            return String.format("%d -->  %d  %d", vertex1, vertex2, weight);
        }
    }

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

    private static int vertices;

    private static final Map<Integer, List<Edge>> adjacentEdges = new HashMap<>();

    public static void initialize() {
        adjacentEdges.clear();

        for (int i = 1; i <= vertices; i++) {
            adjacentEdges.put(i, new ArrayList<Edge>());
        }
    }

    public static void addDEdge(final int from, final int to, final int weight) {
        if (from > vertices || to > vertices) {
            return;
        }
        final Edge e = new Edge(from, to, weight);
        adjacentEdges.get(from).add(e);
        adjacentEdges.get(to).add(e);
    }

    public static Map<Integer, Integer> findShortestPath(final int source) {

        final PriorityQueue<PathToVertex> pq = new PriorityQueue<>();
        pq.add(new PathToVertex(source, 0));

        final boolean[] visited = new boolean[vertices + 1];
        Arrays.fill(visited, false);

        final Map<Integer, Integer> shortestPathMap = new HashMap<>();
        for (int i = 1; i <= vertices; i++) {
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

            for (final Edge de : adjacentEdges.get(curNode)) {
                final int to = de.other(curNode);

                int pathLengthToAdjacentNode = shortestPathMap.get(to);

                /*
                 * If path to adjacent node is shorter via current node, update
                 * the shortest path to adjacent node.
                 */
                if (pathLengthToAdjacentNode > (pathLenToCurNode + de.weight)) {
                    pathLengthToAdjacentNode = pathLenToCurNode + de.weight;
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
}
