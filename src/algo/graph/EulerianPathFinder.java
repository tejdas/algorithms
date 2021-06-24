package algo.graph;

import java.util.*;

/**
 * Eulerian Path for Directed Graphs
 */
public class EulerianPathFinder {

    private int[] outDegree;
    private int[] inDegree;
    private LinkedList[] adjNodes;
    private int numNodes;
    private final LinkedList<Integer> path = new LinkedList<>();
    private int source;
    public List<Integer> findEulerianPath(int numNodes, int[][] graph) {
        if (numNodes == 0) return null;
        this.numNodes = numNodes;

        outDegree = new int[numNodes];
        inDegree = new int[numNodes];

        adjNodes = new LinkedList[numNodes];

        for (int[] edge : graph) {
            int from = edge[0];
            int to = edge[1];

            if (from >= numNodes || to >= numNodes) throw new IllegalArgumentException("Malformed Graph");

            outDegree[from]++;
            inDegree[to]++;

            if (adjNodes[from] == null) {
                adjNodes[from] = new LinkedList<Integer>();
            }
            adjNodes[from].addLast(to);
        }

        if (!checkEulerianPathExistence()) return null;

        dfs(source);

        System.out.println(Arrays.toString(path.toArray()));
        return null;
    }

    private boolean checkEulerianPathExistence() {

        int src = -1;
        int target = -1;
        for (int i = 0; i < numNodes; i++) {
            if (outDegree[i] != inDegree[i]) {
                if (Math.abs(outDegree[i]-inDegree[i]) > 1) return false;

                if (outDegree[i] > inDegree[i]) {
                    if (src == -1) src = i;
                    else return false;
                } else {
                    if (target == -1) target = i;
                    else return false;
                }
            }
        }
        this.source = src == -1? 0: src;
        return true;
    }

    private void dfs(int curNode) {
        if (outDegree[curNode] == 0) {
            path.addFirst(curNode);
            return;
        }

        LinkedList<Integer> adjacentNodes = adjNodes[curNode];

        while (!adjacentNodes.isEmpty()) {
            int adj = adjacentNodes.removeFirst();
            outDegree[curNode]--;
            dfs(adj);
        }

        /**
         * Post-order. Add current Node to the beginning.
         */
        if (outDegree[curNode] == 0) {
            path.addFirst(curNode);
        }
    }

    public static void main(String[] args) {
        {
            int numNodes = 5;
            int[][] graph = { { 0, 1 }, { 1, 2 }, { 2, 1 }, { 1, 3 }, { 3, 4 } };
            new EulerianPathFinder().findEulerianPath(numNodes, graph);
        }

        {
            int numNodes = 4;
            int[][] graph = { {0,1},{0,1},{1,3},{3,0},{1,2},{2,0}};
            new EulerianPathFinder().findEulerianPath(numNodes, graph);
        }

        {
            int numNodes = 5;
            int[][] graph = {{0,0}, {0,2},{2,3},{3,2},{2,4},{4,3},{3,0},{0,1},{1,3}};
            new EulerianPathFinder().findEulerianPath(numNodes, graph);
        }
    }
}
