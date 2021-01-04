package net.lc;

import java.util.HashMap;
import java.util.Map;


public class PopulateNextRightPointer {
    /**
     * Definition for binary tree with next pointer.
     */
    static class Node {
        int val;
        Node left, right, next;
        Node(int x) { val = x; }
    }

    private final Map<Integer, Node> map = new HashMap<>();

    public Node connect(Node root) {
        if (root != null) {
            connect(root, 0);
        }
        return root;
    }

    private void connect(Node node, int depth) {
        if (node.left != null) {
            connect(node.left, depth+1);
        }
        if (map.containsKey(depth)) {
            map.get(depth).next = node;
        }
        map.put(depth, node);

        if (node.right != null) {
            connect(node.right, depth+1);
        }
    }
}