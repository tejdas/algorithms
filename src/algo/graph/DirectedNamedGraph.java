package algo.graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Stack;

public class DirectedNamedGraph {
    static final class DirectedEdge {
        public DirectedEdge(final int from, final int to) {
            super();
            this.from = from;
            this.to = to;

        }

        private final int from;

        private final int to;

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

    private final Map<Integer, Stack<Integer>> depth = new HashMap<>();

    private int edgeCount = 0;

    public DirectedNamedGraph(final int[] verticeArray) {
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
        final DirectedEdge e = new DirectedEdge(from, to);
        adjacentEdges.get(from).add(e);
        edgeCount++;
    }

    public List<DirectedEdge> getAdjacentEdges(final int vertex) {
        return adjacentEdges.get(vertex);
    }

    public void traverseDFS() {
        depth.clear();
        final Stack<Integer> topological = new Stack<>();
        final Map<Integer, Boolean> visited = new HashMap<>();
        for (final int v : verticesArray) {
            visited.put(v, false);
        }

        for (final int v : verticesArray) {
            if (!visited.get(v)) {
                dfs(v, visited, topological);
            }
        }
        System.out.println("Printing topological order");
        while (!topological.empty()) {
            final int val = topological.pop().intValue();
            System.out.print(val + " ");
            for (final Integer i : depth.get(val)) System.out.print(i + " ");
            System.out.println();
        }
        System.out.println("-----------------------");
    }

    private void dfs(final int currentNode, final Map<Integer, Boolean> visited, final Stack<Integer> topological) {
        visited.put(currentNode, true);
        System.out.println("Visiting " + currentNode);
        final Stack<Integer> maxDepth = new Stack<>();
        for (final DirectedEdge e : adjacentEdges.get(currentNode)) {
            final int adjacentNode = e.to();
            if (!visited.get(adjacentNode)) {
                dfs(adjacentNode, visited, topological);
            }
            if (depth.containsKey(adjacentNode)) {
                final Stack<Integer> nodeDepth = depth.get(adjacentNode);
                if (nodeDepth.size() > maxDepth.size()) {
                    maxDepth.clear();
                    maxDepth.addAll(nodeDepth);
                }
            }
        }
        maxDepth.add(currentNode);
        depth.put(currentNode, maxDepth);
        topological.push(currentNode);
    }

    static DirectedNamedGraph constructFromArray(final int[] array) {
        final DirectedNamedGraph dg = new DirectedNamedGraph(array);
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
            final int adjVertex = de.to;
            int distance;
            if (!longestHops.containsKey(adjVertex)) {
                final int maxHops = longestHop(adjVertex, longestHops, longestHopPaths);
                distance = maxHops+1;
            } else {
                distance = longestHops.get(adjVertex) + 1;
            }

            if (distance > max) {
                max = distance;
                maxpath.clear();
                maxpath.addAll(longestHopPaths.get(adjVertex));
                maxpath.add(vertex);
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
        final DirectedNamedGraph dg2 = constructFromArray(new int[] {5, 2, 4, 3, 10, 7, 8, 15, 12});
        final DirectedNamedGraph dg = constructFromArray(new int[] {10, 22, 9, 33, 21, 50, 41, 60, 80});
        dg.traverseDFS();
        dg.findLongestHop();

        dg2.traverseDFS();
        dg2.findLongestHop();
    }
}

