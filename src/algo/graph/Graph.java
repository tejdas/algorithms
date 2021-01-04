package algo.graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;
import java.util.Set;
import java.util.SortedMap;
import java.util.Stack;
import java.util.TreeMap;

public class Graph {
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

    private double maxWeight = 0.0;

    private int maxDistance = 0;

    public Graph(final int vertices) {
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
        if (weight > maxWeight) {
            maxWeight = weight;
        }
    }

    public void longestHop() {
        final List<Integer> largestHop = new ArrayList<>();

        for (int vertex = 0; vertex < vertices; vertex++) {
            final boolean[] visited = new boolean[vertices];
            Arrays.fill(visited,  false);
            maxDistance = 0;
            final List<Integer> maxHops = new ArrayList<>();

            final Stack<Integer> hops = new Stack<>();

            longestHopInternal(vertex, visited, 0, hops, maxHops);

            if (maxHops.size() > largestHop.size()) {
                largestHop.clear();
                largestHop.addAll(maxHops);
            }
        }


        for (final int hop : largestHop) System.out.print(hop + "  ");
        System.out.println();
    }

    private void longestHopInternal(final int curVertex, final boolean[] visited, final int distance, final Stack<Integer> hops, final List<Integer> maxHops) {
        visited[curVertex] = true;
        hops.push(curVertex);
        if (distance > maxDistance) {
            maxDistance = distance;
            maxHops.clear();
            maxHops.addAll(hops);
        }


        final List<Edge> edges = adjacentEdges.get(curVertex);
        for (final Edge e : edges) {
            final int adj = e.other(curVertex);
            if (!visited[adj]) {
                longestHopInternal(adj, visited, distance+1, hops, maxHops);
            }
        }
        hops.pop();
    }

    public void traverseDFSNonRecurse(final int startNode) {
        final boolean[] visited = new boolean[vertices];
        for (int v = 0; v < vertices; v++) {
            visited[v] = false;
        }
        final Stack<Integer> stack = new Stack<>();
        stack.push(startNode);
        while (!stack.isEmpty()) {
            final int node = stack.pop();
            if (visited[node]) {
                continue;
            }

            System.out.println(node);
            visited[node] = true;

            for (final Edge e : adjacentEdges.get(node)) {
                final int adjacentNode = e.other(node);
                if (!visited[adjacentNode]) {
                    stack.push(adjacentNode);
                }
            }
        }
    }

    public void traverseDFS(final int startNode) {
        final boolean[] visited = new boolean[vertices];
        for (int v = 0; v < vertices; v++) {
            visited[v] = false;
        }
        dfs(startNode, visited);
    }

    private void dfs(final int currentNode, final boolean[] visited) {
        visited[currentNode] = true;
        for (final Edge e : adjacentEdges.get(currentNode)) {
            final int adjacentNode = e.other(currentNode);
            if (!visited[adjacentNode]) {
                System.out.println("Visiting from: " + currentNode + " to: " + adjacentNode + " weight: " + e.getWeight());
                dfs(adjacentNode, visited);
            }
        }
    }

    static int globalCount = 0;
    public void traverseAllDFS(final int startNode) {
        globalCount = 0;
        final boolean[] visited = new boolean[vertices];
        for (int v = 0; v < vertices; v++) {
            visited[v] = false;
        }

        final Stack<Integer> path = new Stack<>();
        dfsAll(startNode, visited, path);
        System.out.println("globalCount: " + globalCount);
    }

    private void dfsAll(final int currentNode, final boolean[] visited, final Stack<Integer> path) {
        visited[currentNode] = true;
        path.push(currentNode);
        if (path.size() == vertices) {
            for (final int p : path) System.out.print(p + " ");
            globalCount++;
            System.out.println();
        } else {
            for (final Edge e : adjacentEdges.get(currentNode)) {
                final int adjacentNode = e.other(currentNode);
                if (!visited[adjacentNode]) {
                    dfsAll(adjacentNode, visited, path);
                }
            }
        }
        path.pop();
        visited[currentNode] = false;
    }

    /**
     * Color a Graph so that no two adjacent vertices have the same color.
     * DFS
     * A visited node is a colored node.
     * Visit an unvisited node. Mark as visited.
     * Pick a color by checking all neighboring colored vertices and excluding those colors.
     * Pick a new color if needed, or one of the unused colors.
     * For each of the vertices, if they are not visited, then recursively visit.
     *
     * @param startNode
     */
    public void colorGraph(final int startNode) {
        final boolean[] visited = new boolean[vertices];
        final Random r = new Random();
        final Set<Integer> colors = new HashSet<>();
        final Map<Integer, Integer> colorMap = new HashMap<>();
        for (int v = 0; v < vertices; v++) {
            visited[v] = false;
        }
        colorGraphDFS(startNode, visited, r, colors, colorMap);
        for (final int color : colors) {
            System.out.print(color + "  ");
        }
        System.out.println();
        for (final Entry<Integer, Integer> entry : colorMap.entrySet()) {
            System.out.println("Node: " + entry.getKey() + "  color: " + entry.getValue());
        }
    }

    private void colorGraphDFS(final int currentNode, final boolean[] visited, final Random r, final Set<Integer> chosenColors, final Map<Integer, Integer> vertexColorMap) {
        final Set<Integer> colorsAvailable = new HashSet<>(chosenColors);

        /**
         * Cannot choose a color that matches with one of the adjacent node's color.
         * Therefore, first remove all the adjacent nodes' colors from available colors set.
         */
        for (final Edge e : adjacentEdges.get(currentNode)) {
            final int adjacentNode = e.other(currentNode);
            if (vertexColorMap.containsKey(adjacentNode)) {
                final int colorOfAdjacentNode = vertexColorMap.get(adjacentNode);
                colorsAvailable.remove(colorOfAdjacentNode);
            }
        }

        /**
         * If we've run out of colors, then chose a new color,
         * else randomly choose one among the available colors.
         */
        if (colorsAvailable.isEmpty()) {
            final int color = r.nextInt(100);
            chosenColors.add(color);
            vertexColorMap.put(currentNode,  color);
        } else {
            final Integer[] array = colorsAvailable.toArray(new Integer[colorsAvailable.size()]);
            vertexColorMap.put(currentNode, array[r.nextInt(array.length)]);
        }

        /**
         * Mark current node as visited. Recursively DFS any of the adjacent unvisited node(s)
         */
        visited[currentNode] = true;
        for (final Edge e : adjacentEdges.get(currentNode)) {
            final int adjacentNode = e.other(currentNode);
            if (!visited[adjacentNode]) {
                colorGraphDFS(adjacentNode, visited, r, chosenColors, vertexColorMap);
            }
        }
    }

    public Edge[] traverseBFS(final int startNode) {
        final boolean[] visited = new boolean[vertices];
        final Edge[] edgeTo = new Edge[vertices];
        for (int v = 0; v < vertices; v++) {
            visited[v] = false;
        }

        final Queue<Integer> nodeQueue = new LinkedList<>();
        edgeTo[startNode] = null;
        nodeQueue.add(startNode);
        while (!nodeQueue.isEmpty()) {
            final int curNode = nodeQueue.poll();
            if (visited[curNode]) continue;
            visited[curNode] = true;
            for (final Edge e : adjacentEdges.get(curNode)) {
                final int adjacentVertex = e.other(curNode);
                if (!visited[adjacentVertex]) {
                    nodeQueue.add(adjacentVertex);
                    edgeTo[adjacentVertex] = e;
                }
            }
        }
        return edgeTo;
    }

    public void tracePath(final int source, final int dest) {
        final Stack<Edge> path = new Stack<>();
        final boolean[] visited = new boolean[vertices];
        Arrays.fill(visited, false);
        final boolean found = trace(source, dest, path, visited);
        if (found) {
            System.out.println("found");
            while (!path.isEmpty()) {
                final Edge e = path.pop();
                System.out.println(e);
            }
        }
    }

    private boolean trace(final int source, final int dest, final Stack<Edge> path,
            final boolean[] visited) {
        visited[source] = true;
        for (final Edge e : adjacentEdges.get(source)) {
            final int adj = e.other(source);
            if (visited[adj]) {
                continue;
            }
            path.push(e);
            if (adj == dest) {
                return true;
            }

            if (trace(adj, dest, path, visited)) {
                return true;
            }
            path.pop();
        }
        return false;
    }

    public void findAllPaths(final int source, final int dest) {
        final Stack<Edge> path = new Stack<>();
        final boolean[] visited = new boolean[vertices];
        Arrays.fill(visited, false);
        findAllPaths(source, dest, path, visited);
    }

    private void findAllPaths(final int curNode, final int dest, final Stack<Edge> path, final boolean[] visited) {
        visited[curNode] = true;
        for (final Edge e : adjacentEdges.get(curNode)) {
            final int adj = e.other(curNode);
            if (visited[adj]) {
                continue;
            }
            path.push(e);
            if (adj == dest) {
                for (final Edge ed : path) {
                    System.out.println(ed);
                }
                System.out.println("---------------------");
            } else {
                findAllPaths(adj, dest, path, visited);
            }
            path.pop();
        }
        visited[curNode] = false;
    }

    static int numPaths = 0;
    public void findAllPathsWithVertices(final int source, final int dest) {
        numPaths = 0;
        final Stack<Integer> path = new Stack<>();
        final boolean[] visited = new boolean[vertices];
        Arrays.fill(visited, false);
        path.push(source);
        findAllPathsWithVertices(source, dest, path, visited);
    }

    private void findAllPathsWithVertices2(final int curNode, final int dest, final Stack<Integer> path, final boolean[] visited) {

        for (final Edge e : adjacentEdges.get(curNode)) {
            final int adj = e.other(curNode);

            if (path.contains(adj)) {
                continue;
            }

            path.push(adj);
            if (adj == dest) {
                for (final int v : path) {
                    System.out.print(v + "  ");
                }
                System.out.println();
                numPaths++;
            } else {
                findAllPathsWithVertices2(adj, dest, path, visited);
            }
            path.pop();
        }
    }

    private void findAllPathsWithVertices(final int curNode, final int dest, final Stack<Integer> path, final boolean[] visited) {
        visited[curNode] = true;
        for (final Edge e : adjacentEdges.get(curNode)) {
            final int adj = e.other(curNode);
            if (visited[adj]) {
                continue;
            }
            path.push(adj);
            if (adj == dest) {
                for (final int v : path) {
                    System.out.print(v + "  ");
                }
                System.out.println();
                numPaths++;
            } else {
                findAllPathsWithVertices(adj, dest, path, visited);
            }
            path.pop();
        }
        visited[curNode] = false;
    }

    /**
     * MST is a tree containing all vertices of the Graph, with minimal total weight.
     * Breadth-first search
     * Greedy algorithm.
     * Use a PriorityQueue to pick up the next edge with minimum weight.
     */
    public void buildMinimumSpanningTree(final int startNode) {
        final boolean[] visited = new boolean[vertices];
        Arrays.fill(visited, false);

        final List<Edge> mstEdges = new ArrayList<>();
        final PriorityQueue<Edge> pq = new PriorityQueue<>();

        {
            final int curNode = startNode;
            visited[curNode] = true;
            final List<Edge> edges = adjacentEdges.get(curNode);
            for (final Edge e : edges) {
                pq.offer(e);
            }
        }

        while (!pq.isEmpty()) {
            final Edge minimumEdge = pq.poll();

            final int v = minimumEdge.either();
            final int u = minimumEdge.other(v);
            if (visited[v] && visited[u]) {
                continue;
            }

            int curNode;
            if (!visited[u]) {
                curNode = u;
            } else {
                curNode = v;
            }
            mstEdges.add(minimumEdge);

            visited[curNode] = true;
            final List<Edge> edges = adjacentEdges.get(curNode);
            for (final Edge e : edges) {
                final int adj = e.other(curNode);
                if (!visited[adj]) {
                    pq.offer(e);
                }
            }
        }

        System.out.println("Minimum spanning tree");
        for (final Edge e : mstEdges) {
            System.out.println(e);
        }
    }

    private static boolean visitedAll(final boolean[] visited) {
        for (final boolean v : visited) {
            if (!v) {
                return false;
            }
        }
        return true;
    }

    public void findShortestPath(final int startNode) {
        final boolean[] visited = new boolean[vertices];
        final SortedMap<Double, Integer> distances = new TreeMap<>();
        final Map<Integer, Double> vertexMap = new HashMap<>();
        for (int v = 0; v < vertices; v++) {
            visited[v] = false;
            if (v != startNode) {
                distances.put(maxWeight * 2 + v, v);
                vertexMap.put(v, maxWeight * 2 + v);
            } else {
                distances.put(0.0, v);
                vertexMap.put(v, 0.0);
            }
        }

        final Map<Integer, List<Edge>> shortestPaths = new HashMap<>();
        final List<Edge> el = Collections.emptyList();
        shortestPaths.put(startNode, el);

        int curNode = startNode;
        while (true) {
            final double curNodeDistance = vertexMap.get(curNode);
            final List<Edge> edges = adjacentEdges.get(curNode);
            for (final Edge edge : edges) {
                final int v = edge.other(curNode);
                if (visited[v]) {
                    continue;
                }

                double distanceFromRoot = vertexMap.get(v);
                if (distanceFromRoot > (edge.weight + curNodeDistance)) {
                    distances.remove(distanceFromRoot);

                    distanceFromRoot = curNodeDistance + edge.weight;
                    vertexMap.put(v, distanceFromRoot);
                    distances.put(distanceFromRoot, v);

                    final List<Edge> pathTo = new ArrayList<>();
                    pathTo.addAll(shortestPaths.get(curNode));
                    pathTo.add(edge);
                    shortestPaths.put(v, pathTo);
                }
            }

            visited[curNode] = true;
            if (visitedAll(visited)) {
                break;
            }

            final Set<Double> keys = distances.keySet();
            for (final double key : keys) {
                final int v = distances.get(key);
                if (!visited[v]) {
                    curNode = v;
                    break;
                }
            }
        }

        for (final Entry<Integer, Double> entry : vertexMap.entrySet()) {
            System.out.println("----------------------------");
            System.out.println(entry.getKey() + "  " + entry.getValue());
            final List<Edge> sp = shortestPaths.get(entry.getKey());
            System.out.println("Shortest Path: ");
            for (final Edge e : sp) {
                System.out.println(e);
            }
        }
    }

    public boolean isBipartite() {
        final boolean[] visited = new boolean[vertices];
        Arrays.fill(visited, false);

        final int[] color = new int[vertices];
        Arrays.fill(color, -1);
        for (int i = 0; i < vertices; i++) {
            if (!visited[i]) {
                if (!isBipartiteDFS(i, color, visited)) {
                    return false;
                }
            }
        }

        for (int i = 0; i < vertices; i++) {
            System.out.println(i + " " + color[i]);
        }
        return true;
    }

    private boolean isBipartiteDFS(final int curNode, final int[] color, final boolean[] visited) {
        visited[curNode] = true;

        int adjColor = -1;
        for (final Edge e : adjacentEdges.get(curNode)) {
            final int adjV = e.other(curNode);
            if (color[adjV] != -1) {
                if (adjColor == -1) {
                    adjColor = color[adjV];
                } else {
                    if (adjColor != color[adjV]) {
                        return false;
                    }
                }
            }
        }
        if (color[curNode] == -1) {
            if (adjColor == -1)
                color[curNode] = 1;
            else
                color[curNode] = 1 - adjColor;
        } else if ((adjColor != -1) && (color[curNode] == adjColor)) {
            return false;
        }

        for (final Edge e : adjacentEdges.get(curNode)) {
            final int adjV = e.other(curNode);
            color[adjV] = 1 - color[curNode];
        }

        for (final Edge e : adjacentEdges.get(curNode)) {
            final int adjV = e.other(curNode);
            if (!visited[adjV]) {
                if (!isBipartiteDFS(adjV, color, visited))
                    return false;
            }
        }
        return true;
    }

    public boolean hasCycle() {
        final boolean[] visited = new boolean[vertices];
        Arrays.fill(visited, false);
        final Stack<Integer> path = new Stack<>();
        if (hasCycle(0, 0, visited, path)) {
            for (final int i : path) System.out.print(i + " ");
            return true;
        } else
            return false;
    }

    private boolean hasCycle(final int curNode, final int sourceNode, final boolean[] visited, final Stack<Integer> path) {
        visited[curNode] = true;
        path.push(curNode);
        for (final Edge e : adjacentEdges.get(curNode)) {
            final int adj = e.other(curNode);
            if (adj == sourceNode) continue;
            if (path.contains(adj))
                return true;

            if (!visited[adj]) {
                if (hasCycle(adj, curNode, visited, path)) {
                    return true;
                }
            }
        }
        path.pop();
        return false;
    }

    public static void main1(final String[] args) {
        final int vertices = 9;
        final Graph g = new Graph(vertices);
        g.addEdge(4, 5, .35);
        g.addEdge(4, 7, .37);
        g.addEdge(5, 7, .28);
        g.addEdge(0, 7, .16);
        g.addEdge(1, 5, .32);
        g.addEdge(0, 4, .38);
        g.addEdge(2, 3, .17);
        g.addEdge(1, 7, .19);
        g.addEdge(0, 2, .26);
        g.addEdge(1, 2, .36);
        g.addEdge(1, 3, .29);
        g.addEdge(2, 7, .34);
        g.addEdge(6, 2, .40);
        g.addEdge(3, 6, .52);
        g.addEdge(6, 0, .58);
        g.addEdge(6, 4, .93);

        g.addEdge(3, 8, .15);
        g.addEdge(2, 8, .18);
        g.addEdge(6, 8, .27);

        for (int start = 0; start < 8; start++) {
            // g.traverseDFS(start);
        }

        g.tracePath(3, 4);
        g.tracePath(1,  8);
        /*
         * List<Edge> mstEdges = g.buildMST(); for (Edge e : mstEdges) {
         * System.out.println(e); }
         */

        //g.findShortestPath(0);
    }

    public static void main(final String[] args) {
        final int vertices = 9;
        final Graph g = new Graph(vertices);
        g.addEdge(0, 1, .7);
        g.addEdge(0, 2, .4);
        g.addEdge(0, 3, .3);
        g.addEdge(1, 2, .2);
        g.addEdge(1, 4, 2.5);
        g.addEdge(2, 7, .9);
        g.addEdge(3, 5, .91);
        g.addEdge(4, 6, 1.0);
        g.addEdge(5, 7, 2.0);
        g.addEdge(6, 8, .21);
        g.addEdge(7, 8, .31);

        final int startNode = new Random().nextInt(vertices);
        System.out.println("------------------------------");
        g.traverseDFS(startNode);
        System.out.println("------------------------------");
        g.findShortestPath(startNode);
        System.out.println("------------------------------");
        g.buildMinimumSpanningTree(startNode);
    }

    public static void main3(final String[] args) {
        final int vertices = 9;
        final Graph g = new Graph(vertices);
        g.addEdge(0, 1, .7);
        g.addEdge(0, 2, .4);
        g.addEdge(0, 3, .3);
        g.addEdge(1, 2, .2);
        g.addEdge(1, 4, 2.5);
        g.addEdge(2, 7, .9);
        g.addEdge(3, 5, .91);
        g.addEdge(4, 6, 1.0);
        g.addEdge(5, 7, 2.0);
        g.addEdge(6, 8, .21);
        g.addEdge(7, 8, .31);

        final int startNode = 3; //new Random().nextInt(vertices);
        g.buildMinimumSpanningTree(startNode);
    }

    public static void main4(final String[] args) {
        final Graph g = new Graph(14);
        g.addEdge(0, 2);
        g.addEdge(1, 2);
        g.addEdge(1, 3);
        g.addEdge(1, 4);
        g.addEdge(1, 5);
        g.addEdge(1, 6);
        g.addEdge(1, 7);
        g.addEdge(1, 9);
        g.addEdge(1, 10);
        g.addEdge(1, 11);
        g.addEdge(1, 12);
        g.addEdge(2, 3);
        g.addEdge(3, 4);
        g.addEdge(4, 5);
        g.addEdge(5, 6);
        g.addEdge(6, 7);
        g.addEdge(6, 8);

        g.addEdge(7, 8);
        g.addEdge(7, 9);
        g.addEdge(7, 13);
        g.addEdge(9, 10);
        g.addEdge(9, 11);
        g.addEdge(9, 13);

        g.addEdge(10, 11);
        g.addEdge(11, 12);

        //final int startNode = new Random().nextInt(14);

        for (int startNode = 0; startNode < 14; startNode++) {
            System.out.println("------------------------------");
            System.out.println("startNode: " + startNode);
            g.colorGraph(startNode);
        }
    }

    public static void main5(final String[] args) {
        final Graph g = new Graph(14);
        g.addEdge(0, 2);
        g.addEdge(1, 2);
        g.addEdge(1, 3);
        g.addEdge(1, 4);
        g.addEdge(1, 5);
        g.addEdge(1, 6);
        g.addEdge(1, 7);
        g.addEdge(1, 9);
        g.addEdge(1, 10);
        g.addEdge(1, 11);
        g.addEdge(1, 12);
        g.addEdge(2, 3);
        g.addEdge(3, 4);
        g.addEdge(4, 5);
        g.addEdge(5, 6);
        g.addEdge(6, 7);
        g.addEdge(6, 8);

        g.addEdge(7, 8);
        g.addEdge(7, 9);
        g.addEdge(7, 13);
        g.addEdge(9, 10);
        g.addEdge(9, 11);
        g.addEdge(9, 13);

        g.addEdge(10, 11);
        g.addEdge(11, 12);

        //int startNode = new Random().nextInt(14);
        g.findAllPathsWithVertices(0, 7);
        System.out.println(numPaths);
    }

    public static void main6(final String[] args) {
        final Graph g = new Graph(5);
        g.addEdge(0, 1);
        g.addEdge(0, 3);
        g.addEdge(0, 2);
        g.addEdge(1, 4);
        g.addEdge(2, 3);
        g.addEdge(2, 4);
        g.addEdge(3, 4);


        //int startNode = new Random().nextInt(14);
        g.findAllPathsWithVertices(0, 4);
    }

    public static void main7(final String[] args) {
        final int vertices = 9;
        final Graph g = new Graph(vertices);
        g.addEdge(0, 1);
        g.addEdge(0, 2);
        g.addEdge(0, 3);
        g.addEdge(1, 2);
        g.addEdge(1, 4);
        g.addEdge(2, 7);
        g.addEdge(3, 5);
        g.addEdge(4, 6);
        g.addEdge(5, 7);
        g.addEdge(6, 8);
        g.addEdge(7, 8);

        final int startNode = new Random().nextInt(vertices);
        final Edge[] edgeTo = g.traverseBFS(startNode);

        for (int v = 0; v < vertices; v++) {
            if (v == startNode)
                continue;
            int curNode = v;
            System.out.print(curNode + " ");
            while (curNode != startNode) {
                final Edge e = edgeTo[curNode];
                curNode = e.other(curNode);
                System.out.print(curNode + " ");
            }
            System.out.println();
        }
    }

    public static void main8(final String[] args) {
        final int vertices = 4;
        final Graph g = new Graph(vertices);
        g.addEdge(0, 1);
        g.addEdge(1, 2);
        g.addEdge(2, 3);
        g.addEdge(0, 3);

        System.out.println(g.isBipartite());
    }

    public static void main9(final String[] args) {
        final int vertices = 7;
        final Graph g = new Graph(vertices);
        g.addEdge(0, 1);
        g.addEdge(0, 3);
        g.addEdge(1, 2);
        //g.addEdge(1, 4);
        g.addEdge(2, 5);
        g.addEdge(3, 4);
        g.addEdge(4, 6);
        //g.addEdge(5, 6);

        System.out.println(g.hasCycle());
    }

    public static void main10(final String[] args) {
        final int vertices = 9;
        final Graph g = new Graph(vertices);
        g.addEdge(0, 1, .7);
        g.addEdge(0, 2, .4);
        g.addEdge(0, 3, .3);
        g.addEdge(1, 2, .2);
        g.addEdge(1, 4, 2.5);
        g.addEdge(2, 7, .9);
        g.addEdge(3, 5, .91);
        g.addEdge(4, 6, 1.0);
        g.addEdge(5, 7, 2.0);
        g.addEdge(6, 8, .21);
        g.addEdge(7, 8, .31);

        final int startNode = new Random().nextInt(vertices);
        g.traverseDFSNonRecurse(startNode);
    }

    public static void main11(final String[] args) {
        final Graph g = new Graph(14);
        g.addEdge(0, 2);
        g.addEdge(1, 2);
        g.addEdge(1, 3);
        g.addEdge(1, 4);
        g.addEdge(1, 5);
        g.addEdge(1, 6);
        g.addEdge(1, 7);
        g.addEdge(1, 9);
        g.addEdge(1, 10);
        g.addEdge(1, 11);
        g.addEdge(1, 12);
        g.addEdge(2, 3);
        g.addEdge(3, 4);
        g.addEdge(4, 5);
        g.addEdge(5, 6);
        g.addEdge(6, 7);
        g.addEdge(6, 8);

        g.addEdge(7, 8);
        g.addEdge(7, 9);
        g.addEdge(7, 13);
        g.addEdge(9, 10);
        g.addEdge(9, 11);
        g.addEdge(9, 13);

        g.addEdge(10, 11);
        g.addEdge(11, 12);

        //int startNode = new Random().nextInt(14);
        g.longestHop();
    }

    public static void main12(final String[] args) {
        final Graph g = new Graph(8);
        g.addEdge(0, 1);
        g.addEdge(0, 3);
        g.addEdge(0, 4);

        g.addEdge(1, 2);
        g.addEdge(1, 5);

        g.addEdge(2, 3);
        g.addEdge(2, 6);

        g.addEdge(3, 7);

        g.addEdge(4, 5);
        g.addEdge(4, 7);

        g.addEdge(5, 6);

        g.addEdge(6, 7);



        g.traverseAllDFS(0);
    }
}
