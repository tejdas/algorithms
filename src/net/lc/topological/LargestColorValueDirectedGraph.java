package net.lc.topological;

import java.util.*;

/**
 * 1857
 * Topological sort
 * BFS
 * Greedy
 */
public class LargestColorValueDirectedGraph {

    private int[] nodeColors;
    private int[][] dp;
    private int[] inDegree;
    private int result = 0;

    private final Map<Integer, Set<Integer>> adjMap = new HashMap<>();
    public int largestPathValue(String colors, int[][] edges) {
        char[] colorArray = colors.toCharArray();

        int nodes = colorArray.length;
        nodeColors = new int[nodes];
        inDegree = new int[nodes];

        Map<Integer, Integer> colorMap = new HashMap<>();
        int cindex = 0;
        for (int i = 0; i < colorArray.length; i++) {
            int key = colorArray[i] - 'a';
            if (!colorMap.containsKey(key)) {
                colorMap.put(key, cindex++);
            }
        }
        int colorCount = colorMap.size();

        for (int i = 0; i < colorArray.length; i++) {
            nodeColors[i] = colorMap.get(colorArray[i] - 'a');
        }

        /**
         * 2D array Num-Nodes * Total available colors
         * Memoized info
         */
        dp = new int[nodes][colorCount];

        for (int[] edge : edges) {
            int from = edge[0];
            int to = edge[1];

            if (from == to) return -1; // cycle

            Set<Integer> adjset = adjMap.computeIfAbsent(from, k -> new HashSet<>());
            adjset.add(to);

            inDegree[to]++;
        }

        Queue<Integer> nodeQueue = new LinkedList<>();

        // add root nodes (inDegree == 0)
        for (int i = 0; i < nodes; i++) {
            if (inDegree[i] == 0) {
                nodeQueue.add(i);
                int colorId = nodeColors[i];
                dp[i][colorId] = 1;
            }
        }

        if (nodeQueue.isEmpty()) return -1; // cycle

        int processed = 0;
        while (!nodeQueue.isEmpty()) {
            int nodeId = nodeQueue.remove();
            processed++;

            Set<Integer> neighbors = adjMap.get(nodeId);
            if (neighbors == null) {
                // terminal Nodes; update result by going thru color counts
                for (int color = 0; color < colorCount; color++) {
                    result = Math.max(result, dp[nodeId][color]);
                }
            } else {
                for (int neighbor : neighbors) {
                    int neighborColorId = nodeColors[neighbor];
                    /**
                     * Update neighbor's color-count in Greedy manner.
                     * If the color count from currentNode is bigger, update it.
                     */
                    for (int color = 0; color < colorCount; color++) {
                        if (color == neighborColorId) {
                            /**
                             * For neighbor's color
                             */
                            dp[neighbor][color] = Math.max(dp[neighbor][color], 1 + dp[nodeId][color]);
                        } else {
                            dp[neighbor][color] = Math.max(dp[neighbor][color], dp[nodeId][color]);
                        }
                    }

                    inDegree[neighbor]--;
                    if (inDegree[neighbor] == 0) {
                        // Topological. All the predecessors of neighbors have been processed.
                        // So, add in the queue for processing.
                        nodeQueue.add(neighbor);
                    }
                }
            }
        }

        if (processed == nodes) return result;
        return -1; // the graph is not connected. So, return -1
    }

    public static void main(String[] args) {

        {
            String colors = "abaca";
            int[][] edges = new int[][] { { 0, 1 }, { 0, 2 }, { 2, 3 }, { 3, 4 } };

            System.out.println(new LargestColorValueDirectedGraph().largestPathValue(colors, edges));
        }

        {
            String colors = "a";
            int[][] edges = new int[][] { { 0, 0} };

            System.out.println(new LargestColorValueDirectedGraph().largestPathValue(colors, edges));
        }

        {
            String colors = "iiiiii";
            int[][] edges = new int[][] { { 0, 1}, {1,2}, {2, 3}, {3,4}, {4,5}};

            System.out.println(new LargestColorValueDirectedGraph().largestPathValue(colors, edges));
        }

        {
            String colors = "aaa";
            int[][] edges = new int[][] {{1,2},{2,1}};

            System.out.println(new LargestColorValueDirectedGraph().largestPathValue(colors, edges));
        }
    }
}
