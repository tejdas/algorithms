package net.lc;

/**
 * https://leetcode.com/problems/is-graph-bipartite/
 * DFS
 * Graph
 */
public class IsGraphBipartite {
    private boolean[] visited;
    private int color[];
    private int numNodes = 0;
    private boolean biPartiteFlag = true;
    private int[][] graph;

    public boolean isBipartite(int[][] graph) {

        if (graph == null || graph.length == 0) {
            return true;
        }

        numNodes = graph.length;
        visited = new boolean[numNodes];
        color = new int[numNodes];

        this.graph = graph;
        for (int i = 0; i < numNodes; i++) {
            visited[i] = false;
            color[i] = -1;
        }


        for (int numNode = 0; numNode < numNodes; numNode++) {
            if (!visited[numNode]) {
                color[numNode] = 0;
                biPartiteFlag = biPartiteFlag && isBipartiteRecurse(numNode);
            }
        }
        return biPartiteFlag;
    }

    private boolean isBipartiteRecurse(int curNode) {
        visited[curNode] = true;

        for (int neighbor : graph[curNode]) {
            if (color[neighbor] == -1) {
                color[neighbor] = 1 - color[curNode];
            } else if (color[neighbor] == color[curNode]) {
                return false;
            }

            if (!visited[neighbor]) {
                boolean flag = isBipartiteRecurse(neighbor);
                if (!flag) {
                    return false;
                }
            }
        }

        return true;
    }
}
