package net.lc.dfs;

import java.util.*;

/**
 * 797
 * DFS
 * All-paths
 */
public class AllPathsSourceToTarget {
    private final Map<Integer, Set<Integer>> map = new HashMap<>();
    private final Set<Integer> visited = new HashSet<>();

    List<List<Integer>> result = new ArrayList<>();

    public List<List<Integer>> allPathsSourceTarget(int[][] graph) {
        result.clear();
        if (graph == null || graph.length == 0)
            return result;

        map.clear();

        for (int i = 0; i < graph.length; i++) {
            Set<Integer> set = new HashSet<>();
            map.put(i, set);

            for (int j : graph[i]) {
                set.add(j);
            }

        }
        Stack<Integer> stack = new Stack<>();
        traverseDFS(0, graph.length-1, stack);
        return result;
    }

    private void traverseDFS(int curNode, int destNode, Stack<Integer> path) {
        if (visited.contains(curNode)) return;

        if (curNode == destNode) {
            List<Integer> pathList = new ArrayList<>();
            for (int val : path) pathList.add(val);
            pathList.add(curNode);
            result.add(pathList);
            return;
        }

        visited.add(curNode);
        path.push(curNode);

        for (int adjNode : map.get(curNode)) {
            if (!visited.contains(adjNode)) {
                traverseDFS(adjNode, destNode, path);
            }
        }

        path.pop();
        visited.remove(curNode); //enable all-path traversal
    }
}
