package algo.graph;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

public class DirectedGraphWithParentInfo {
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

    private final Map<Integer, Set<Integer>> parents = new HashMap<>();

    private final Map<Integer, Integer> depth = new HashMap<>();

    private int edgeCount = 0;

    public DirectedGraphWithParentInfo(final int vertices) {
        this.vertices = vertices;
        for (int v = 0; v < vertices; v++) {
            adjacentEdges.put(v, new ArrayList<DirectedEdge>());
            parents.put(v, new LinkedHashSet<Integer>());
        }
        verticesArray = null;
    }

    public int getEdgeCount() {
        return edgeCount;
    }

    public Set<Integer> getParents(int vertex) {
        return parents.get(vertex);
    }

    public void addEdge(final int from, final int to) {
        final DirectedEdge e = new DirectedEdge(from, to);
        adjacentEdges.get(from).add(e);
        edgeCount++;
        parents.get(to).add(from);
    }

    public List<DirectedEdge> getAdjacentEdges(final int vertex) {
        return adjacentEdges.get(vertex);
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
        // System.out.println("Visiting " + currentNode);
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

    public static void main(final String[] args) {

        final int vertices = 10;
        final DirectedGraphWithParentInfo g = new DirectedGraphWithParentInfo(vertices);
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

        System.out.println("---------Topoligical sort-------");

        for (final int i : topological) {
            System.out.print(i + "  ");
        }
        System.out.println();

        final Set<Integer> visited = new HashSet<>();

        final Queue<Integer> q = new LinkedList<>();
        q.add(topological.pop());
        while (visited.size() < vertices) {
            final int visitingNode = q.remove();
            visited.add(visitingNode);
            System.out.println("Processing: " + visitingNode);

            final List<DirectedEdge> edges = g.getAdjacentEdges(visitingNode);
            final List<Integer> elig = new ArrayList<>();
            for (final DirectedEdge edge : edges) {
                final int adj = edge.to;
                final Set<Integer> parents = g.getParents(adj);

                if (visited.containsAll(parents)) {
                    q.add(adj);
                    elig.add(adj);
                }
            }
            if (!elig.isEmpty()) {
                System.out.println("Can run in parallel: " + Arrays.toString(elig.toArray(new Integer[elig.size()])));
            }
        }
    }

    public static void main2(final String[] args) throws IOException {

        final int vertices = 10;
        final DirectedGraphWithParentInfo g = new DirectedGraphWithParentInfo(vertices);
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

        System.out.println("---------Topoligical sort-------");

        for (final int i : topological) {
            System.out.print(i + "  ");
        }
        System.out.println();

        final Set<Integer> visited = new HashSet<>();

        final Queue<Integer> q = new LinkedList<>();

        final Set<Integer> elig = new LinkedHashSet<>();
        final int first = topological.pop();
        q.add(first);
        elig.add(first);
        final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        while (visited.size() < vertices) {
            System.out.println(
                    "Enter task to execute: options: " + Arrays.toString(elig.toArray(new Integer[elig.size()])));
            final String input = in.readLine();
            final String[] tasks = input.split(" ");
            for (final String task : tasks) {
                final int taskId = Integer.parseInt(task);
                if (visited.contains(taskId)) {
                    System.out.println("ALready executed: " + taskId);
                } else if (!elig.contains(taskId)) {
                    System.out.println("Cannot execute: " + taskId);
                } else {
                    System.out.println("Executing: " + taskId);
                    elig.remove(taskId);
                    visited.add(taskId);

                    final List<DirectedEdge> edges = g.getAdjacentEdges(taskId);
                    for (final DirectedEdge edge : edges) {
                        final int adj = edge.to;
                        final Set<Integer> parents = g.getParents(adj);

                        if (visited.containsAll(parents)) {
                            q.add(adj);
                            elig.add(adj);
                        }
                    }
                }
            }
        }
        System.out.println("All done");
        in.close();
    }
}
