package net.lc.dfs;

import java.util.*;

/**
 * 133
 * DFS
 */
public class CloneGraph {

    /**
     * Use a Map<Integer,UndirectedGraphNode> for the cloned-graph.
     */
    Map<Integer, UndirectedGraphNode> map = new HashMap<>();
    Set<Integer> visited = new HashSet<>();
    /**
     * Traverse DFS on the original graph, and cloned-graph simultaneously.
     * @param node
     * @return
     */
    public UndirectedGraphNode cloneGraph(UndirectedGraphNode node) {
        if (node == null) {
            return null;
        }
        UndirectedGraphNode cloned = new UndirectedGraphNode(node.label);
        map.put(node.label, cloned);
        cloneDFS(node, cloned);
        return cloned;
    }

    private void cloneDFS(UndirectedGraphNode orig, UndirectedGraphNode cloned) {
        visited.add(orig.label);

        for (UndirectedGraphNode neighbor : orig.neighbors) {

            UndirectedGraphNode clonedNeighbor = map.get(neighbor.label);
            if (clonedNeighbor == null) {
                clonedNeighbor = new UndirectedGraphNode(neighbor.label);
                map.put(neighbor.label, clonedNeighbor);
            }
            cloned.neighbors.add(clonedNeighbor);

            if (!visited.contains(neighbor.label)) {
                cloneDFS(neighbor, clonedNeighbor);
            }
        }
    }

    public static void main(String[] args) {
        {
            String input = "{0,1,2#1,2#2,2}";
            UndirectedGraphNode root = UndirectedGraphNode.deserialize(input);

            CloneGraph cg = new CloneGraph();
            UndirectedGraphNode clonedRoot = cg.cloneGraph(root);
            String output = UndirectedGraphNode.serialize(clonedRoot);
            System.out.println(output);
        }

        {
            String input = "{}";
            UndirectedGraphNode root = UndirectedGraphNode.deserialize(input);

            CloneGraph cg = new CloneGraph();
            UndirectedGraphNode clonedRoot = cg.cloneGraph(root);

            for (UndirectedGraphNode node : cg.map.values()) {
                System.out.println(node.label);
                for (UndirectedGraphNode nnode : node.neighbors) {
                    System.out.print(nnode.label + "   ");
                }
                System.out.println();
                System.out.println("-------------------");
            }
        }
    }
}
