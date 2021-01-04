package net.lc;

import java.util.*;

/**
 * 1192
 * https://leetcode.com/problems/critical-connections-in-a-network/
 * DFS
 * Post-processing
 * Bridges in Graph
 * Tarjan algorithm
 */
public class CriticalConnectionsInNetwork {
    private final Map<Integer, List<Integer>> adjMap = new HashMap<>();
    private boolean[] visited;
    private int[] dfsAge;
    private int[] lowVal;
    private int age = 0;
    private final List<List<Integer>> output = new ArrayList<>();

    public List<List<Integer>> criticalConnections(int n, List<List<Integer>> connections) {

        visited = new boolean[n];
        dfsAge = new int[n];
        lowVal = new int[n];

        adjMap.clear();
        for (int i = 0; i < n; i++) {
            adjMap.put(i, new ArrayList<>());
        }
        buildAdjMap(connections);
        age = 0;
        for (int i = 0; i < n; i++) {
            dfs(-1, i);
        }

        return output;
    }

    private void buildAdjMap(List<List<Integer>> connections) {
        for (List<Integer> edge : connections) {
            int v1 = edge.get(0);
            int v2 = edge.get(1);

            adjMap.get(v1).add(v2);
            adjMap.get(v2).add(v1);
        }
    }

    /**
     * dfsAge[i] is the sequential number in which a node was first visited. (pre-processing)
     * lowVal[i] is the min of dfsAge of current Node and lowVal[j] for all adjNodes
     * (excluding the parent from which the current node is visited)
     *
     * if, for an adjNode, it's lowVal is less than or equal to current node's dfsAge, this means the adjNode was visited earlier,
     * and that the edge to adjNode is not the only edge leading to adjNode.
     *
     * if, for an adjNode, it's lowVal is greater to current node's dfsAge, this means the adjNode could only be visited from the current
     * node, and there was no other way to visit the adjNode. So, the edge from currentNode to adjNode could be considered as a BRIDGE.
     * @param parent
     * @param cur
     */
    private void dfs(int parent, int cur) {
        if (visited[cur]) return;
        visited[cur] = true;
        dfsAge[cur] = ++age;
        lowVal[cur] = dfsAge[cur];

        int lowValue = Integer.MAX_VALUE;
        for (int adj : adjMap.get(cur)) {
            if (!visited[adj]) {
                dfs(cur, adj);

                if (lowVal[adj] > dfsAge[cur]) {
                    output.add(Arrays.asList(cur, adj));
                }
            }

            if (parent != -1 && adj != parent) {
                lowValue = Math.min(lowValue, lowVal[adj]);
            }
        }

        lowVal[cur] = (lowValue == Integer.MAX_VALUE) ? dfsAge[cur] : lowValue;
    }
}
