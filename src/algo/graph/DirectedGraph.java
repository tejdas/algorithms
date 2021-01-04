package algo.graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Stack;

public class DirectedGraph {
    static final class DirectedEdge {
        public DirectedEdge(final int from, final int to) {
            super();
            this.from = from;
            this.to = to;
            this.weight = 0;
        }

        public DirectedEdge(final int from, final int to, final int weight) {
            super();
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
    }

    private final int vertices;

    private final int[] verticesArray;

    private final Map<Integer, List<DirectedEdge>> adjacentEdges = new HashMap<>();

    private final Map<Integer, Integer> depth = new HashMap<>();

    private int edgeCount = 0;

    public DirectedGraph(final int vertices) {
        this.vertices = vertices;
        for (int v = 0; v < vertices; v++) {
            adjacentEdges.put(v, new ArrayList<DirectedEdge>());
        }
        verticesArray = null;
    }

    public DirectedGraph(final int[] verticeArray) {
        verticesArray = verticeArray;
        this.vertices = verticeArray.length;
        for (final int v : verticesArray) {
            adjacentEdges.put(v, new ArrayList<DirectedEdge>());
        }
    }

    public int getEdgeCount() {
        return edgeCount;
    }

    public void addEdge(final int from, final int to) {
        addEdge(from, to, 0);
    }

    public void addEdge(final int from, final int to, final int weight) {
        final DirectedEdge e = new DirectedEdge(from, to, weight);
        adjacentEdges.get(from).add(e);
        edgeCount++;
    }

    public List<DirectedEdge> getAdjacentEdges(final int vertex) {
        return adjacentEdges.get(vertex);
    }

    public DirectedGraph reverse() {
        final DirectedGraph dg = new DirectedGraph(vertices);
        for (int v = 0; v < vertices; v++) {
            final List<DirectedEdge> adjEdges = adjacentEdges.get(v);
            if (adjEdges != null) {
                for (final DirectedEdge adjEdge : adjEdges) {
                    dg.addEdge(adjEdge.to, adjEdge.from);
                }
            }
        }
        return dg;
    }

    public void shortestPath(final int source) {
        final Stack<Integer> topological = traverseDFS();
        final List<Integer> topologicalList = new ArrayList<>();
        while (!topological.isEmpty())
            topologicalList.add(topological.pop());

        final Map<Integer, List<Integer>> paths = new HashMap<>();
        for (int i = 0; i < vertices; i++) {
            paths.put(i,  new ArrayList<>());
        }
        for (final int i : topological) System.out.print(i + " ");
        System.out.println("End topological list");
        final int dist[] = new int[vertices];
        Arrays.fill(dist,  Integer.MAX_VALUE);
        dist[source] = 0;
        paths.get(source).add(source);

        for (final int curNode : topologicalList) {
            for (final DirectedEdge e : adjacentEdges.get(curNode)) {
                final int adj = e.to();
                if (dist[adj] > dist[curNode] + e.weight) {
                    dist[adj] = dist[curNode] + e.weight;
                    final List<Integer> path = new ArrayList<>();
                    path.addAll(paths.get(curNode));
                    path.add(adj);
                    paths.put(adj, path);
                }
            }
        }

        for (int i = 0; i < vertices; i++) {
            System.out.println("Shortest path for: " + i + "  " + dist[i]);
            for (final int j : paths.get(i)) System.out.print(j + " ");
            System.out.println();
        }
    }

    public Stack<Integer> traverseDFS() {
        depth.clear();
        final Stack<Integer> topological = new Stack<>();
        final boolean[] visited = new boolean[vertices];
        Arrays.fill(visited, false);
        for (int v = 0; v < vertices; v++) {
            if (!visited[v]) {
                dfs(v, visited, topological);
            }
        }
        return topological;
    }

    private void dfs(final int currentNode, final boolean[] visited, final Stack<Integer> topological) {
        visited[currentNode] = true;
        System.out.println("Visiting " + currentNode);
        int maxDepth = 0;
        for (final DirectedEdge e : adjacentEdges.get(currentNode)) {
            final int adjacentNode = e.to();
            if (!visited[adjacentNode]) {
                dfs(adjacentNode, visited, topological);
            }
            if (depth.containsKey(adjacentNode)) {
                final int nodeDepth = depth.get(adjacentNode);
                if (nodeDepth > maxDepth) {
                    maxDepth = nodeDepth;
                }
            }
        }
        depth.put(currentNode, maxDepth + 1);
        topological.push(currentNode);
    }

    static DirectedGraph constructFromArray(final int[] array) {
        final DirectedGraph dg = new DirectedGraph(array);
        for (int src = 0; src < array.length-1; src++) {
            for (int dest = src+1; dest < array.length; dest++) {
                if (array[src] < array[dest]) {
                    dg.addEdge(array[src],  array[dest]);
                }
            }
        }
        for (final int v : array) {
            final List<DirectedEdge> adjacentEdges = dg.getAdjacentEdges(v);
            System.out.println("The adjacent vertices for : " + v);
            for (final DirectedEdge adjacentEdge : adjacentEdges) {
                System.out.print(adjacentEdge.to + " ");
            }
            System.out.println();
        }
        return dg;
    }

    public void findLongestHop() {
        final Map<Integer, Integer> vertexHopMap = new HashMap<>();
        final Map<Integer, Stack<Integer>> longestHopPaths = new HashMap<>();
        for (final int v : verticesArray) {
            longestHopPaths.put(v,  new Stack<Integer>());
        }
        for (final int v : verticesArray) {
            longestHop(v, vertexHopMap, longestHopPaths);
        }

        for (final Entry<Integer, Integer> entry : vertexHopMap.entrySet()) {
            System.out.println(entry.getKey() + "  " + entry.getValue());
        }

        for (final Entry<Integer, Stack<Integer>> entry : longestHopPaths.entrySet()) {
            System.out.println("Longest path for: " + entry.getKey());
            for (final int i : entry.getValue()) {
                System.out.print(i + " ");
            }
            System.out.println("--------------");
        }
    }

    private int longestHop(final int vertex, final Map<Integer, Integer> longestHops, final Map<Integer, Stack<Integer>> longestHopPaths) {
        int max = 0;
        final Stack<Integer> maxpath = new Stack<>();
        for (final DirectedEdge de : adjacentEdges.get(vertex)) {
            final Stack<Integer> path = new Stack<>();
            final int adjVertex = de.to;
            int distance;
            if (!longestHops.containsKey(adjVertex)) {
                final int maxHops = longestHop(adjVertex, longestHops, longestHopPaths);
                distance = maxHops+1;
            } else {
                distance = longestHops.get(adjVertex) + 1;
            }
            path.addAll(longestHopPaths.get(adjVertex));
            path.add(vertex);
            if (distance > max) {
                max = distance;
                maxpath.clear();
                maxpath.addAll(path);
            }
        }
        if (maxpath.isEmpty()) {
            maxpath.add(vertex);
        }
        longestHops.put(vertex,  max);
        longestHopPaths.put(vertex,  maxpath);
        return max;
    }

    public static void main(final String[] args) {

        final int vertices = 10;
        final DirectedGraph g = new DirectedGraph(vertices);
        g.addEdge(0, 1);
        g.addEdge(0, 2);
        g.addEdge(0, 3);
        g.addEdge(3, 2);
        g.addEdge(1, 4);
        g.addEdge(2, 4);
        g.addEdge(3, 5);
        g.addEdge(4, 6);
        g.addEdge(5, 7);
        g.addEdge(5, 8);
        g.addEdge(6, 9);
        g.addEdge(7, 9);
        g.addEdge(8, 9);

        final Stack<Integer> topological = g.traverseDFS();

        System.out.println("-----------------");

        while (!topological.isEmpty()) {
            System.out.print(topological.pop() + "  ");
        }
        System.out.println();
    }

    public static void main0(final String[] args) {

        final int vertices = 13;
        final DirectedGraph g = new DirectedGraph(vertices);
        g.addEdge(0, 1);
        g.addEdge(0, 5);
        g.addEdge(0, 6);
        g.addEdge(2, 0);
        g.addEdge(2, 3);
        g.addEdge(3, 5);
        g.addEdge(5, 4);
        g.addEdge(6, 4);
        g.addEdge(6, 9);
        g.addEdge(7, 6);
        g.addEdge(8, 7);
        g.addEdge(9, 10);
        g.addEdge(9, 11);
        g.addEdge(9, 12);
        g.addEdge(11, 12);

        g.traverseDFS();

        System.out.println("-----------------");
        final DirectedGraph reversed = g.reverse();
        reversed.traverseDFS();
    }

    public static void main1(final String[] args) {
        final DirectedGraph dg = constructFromArray(new int[] {5, 2, 4, 3, 10, 7, 8, 15, 12, 6, 19, 18, 28});
        dg.findLongestHop();
    }

    public static void main2(final String[] args) {
        final int vertices = 13;
        final DirectedGraph g = new DirectedGraph(vertices);
        g.addEdge(0, 1);
        g.addEdge(0, 5);
        g.addEdge(0, 6);
        g.addEdge(2, 0);
        g.addEdge(2, 3);
        g.addEdge(3, 5);
        g.addEdge(5, 4);
        g.addEdge(6, 4);
        g.addEdge(6, 9);
        g.addEdge(7, 6);
        g.addEdge(8, 7);
        g.addEdge(9, 10);
        g.addEdge(9, 11);
        g.addEdge(9, 12);
        g.addEdge(11, 12);

        g.traverseDFS();
    }

    public static void main3(final String[] args) {
        final int vertices = 8;
        final DirectedGraph g = new DirectedGraph(vertices);
        g.addEdge(5, 4, 35);
        g.addEdge(4, 7, 37);
        g.addEdge(5, 7, 28);
        g.addEdge(5, 1, 32);
        g.addEdge(4, 0, 38);
        g.addEdge(0, 2, 26);
        g.addEdge(3, 7, 39);
        g.addEdge(1, 3, 29);
        g.addEdge(7, 2, 34);
        g.addEdge(6, 2, 40);
        g.addEdge(3, 6, 52);
        g.addEdge(6, 0, 58);
        g.addEdge(6, 4, 93);

        g.shortestPath(5);
    }
}
