package net.lc.dfs;

import java.util.*;

/**
 * 133
 * https://leetcode.com/problems/clone-graph/
 * DFS
 */
class UndirectedGraphNode {
    int label;
    List<UndirectedGraphNode> neighbors;

    UndirectedGraphNode(int x) {
        label = x;
        neighbors = new ArrayList<>();
    }

    public static UndirectedGraphNode deserialize(String input) {

        UndirectedGraphNode root = null;
        Map<Integer, UndirectedGraphNode> map = new HashMap<>();

        input = input.substring(1, input.length() - 1);

        String[] array = input.split("#");
        for (String entry : array) {
            String[] nodes = entry.split(",");

            if (nodes == null || nodes.length == 0) {
                continue;
            }
            if (nodes[0].isEmpty()) {
                continue;
            }

            int nodeLabel = Integer.parseInt(nodes[0]);
            UndirectedGraphNode node = map.get(nodeLabel);
            if (node == null) {
                node = new UndirectedGraphNode(nodeLabel);
                map.put(nodeLabel, node);
            }

            if (root == null) {
                root = node;
            }

            for (int i = 1; i < nodes.length; i++) {
                int neighborNodeLabel = Integer.parseInt(nodes[i]);
                UndirectedGraphNode neighborNode = map.get(neighborNodeLabel);
                if (neighborNode == null) {
                    neighborNode = new UndirectedGraphNode(neighborNodeLabel);
                    map.put(neighborNodeLabel, neighborNode);
                }
                node.neighbors.add(neighborNode);
            }
        }
        return root;
    }

    public static String serialize(UndirectedGraphNode root) {
        StringBuilder sb = new StringBuilder("{");
        Set<Integer> visited = new HashSet<>();
        if (root != null) {
            serializeRecurse(root, sb, visited, true);
        }
        sb.append("}");
        return sb.toString();
    }

    private static void serializeRecurse(UndirectedGraphNode node, StringBuilder sb, Set<Integer> visited, boolean isRoot) {
        visited.add(node.label);

        if (!isRoot) {
            sb.append("#");
        }
        sb.append(node.label);

        for (UndirectedGraphNode nnode : node.neighbors) {
            sb.append(",");
            sb.append(nnode.label);
        }

        for (UndirectedGraphNode nnode : node.neighbors) {
            if (!visited.contains(nnode.label)) {
                serializeRecurse(nnode, sb, visited, false);
            }
        }
    }
}
