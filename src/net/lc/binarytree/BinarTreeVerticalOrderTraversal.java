package net.lc.binarytree;

import java.util.*;

/**
 * 314
 * BinaryTree
 * BFS
 */
public class BinarTreeVerticalOrderTraversal {
    static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) { val = x; }
    }

    static class NodeWithLevel {
        TreeNode node;
        int level;

        public NodeWithLevel(TreeNode node, int level) {
            this.node = node;
            this.level = level;
        }
    }

    public List<List<Integer>> verticalOrder(TreeNode root) {
        if (root == null) {
            return new ArrayList<>();
        }
        Map<Integer, List<Integer>> map = new HashMap<>();

        Queue<NodeWithLevel> queue = new LinkedList<>();
        queue.add(new NodeWithLevel(root, 0));

        int minCol = 0;
        int maxCol = 0;

        while (!queue.isEmpty()) {
            NodeWithLevel n = queue.remove();
            List<Integer> list = map.computeIfAbsent(n.level, l -> new ArrayList<>());
            list.add(n.node.val);
            if (n.node.left != null) {
                if (n.level-1 < minCol) {
                    minCol = n.level-1;
                }
                queue.add(new NodeWithLevel(n.node.left, n.level-1));
            }

            if (n.node.right != null) {
                if (n.level+1 >maxCol) {
                    maxCol = n.level+1;
                }
                queue.add(new NodeWithLevel(n.node.right, n.level+1));
            }
        }

        List<List<Integer>> result = new ArrayList<>();
        for (int i = minCol; i <= maxCol; i++) {
            result.add(map.get(i));
        }

        return result;
    }
}
