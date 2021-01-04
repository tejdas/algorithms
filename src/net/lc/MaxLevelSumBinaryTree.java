package net.lc;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 1161
 * Binary Tree
 * BFS (Level-Order)
 */
public class MaxLevelSumBinaryTree {
    static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode() {
        }

        TreeNode(int val) {
            this.val = val;
        }

        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    public int maxLevelSum(TreeNode root) {
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        Queue<TreeNode> nextQueue = new LinkedList<>();

        int maxLevel = -1;
        int maxSum = Integer.MIN_VALUE;
        int level = 1;
        while (true) {
            int sum = 0;

            while (!queue.isEmpty()) {
                TreeNode node = queue.remove();
                sum += node.val;
                if (node.left != null) nextQueue.add(node.left);
                if (node.right != null) nextQueue.add(node.right);
            }

            if (sum > maxSum) {
                maxSum = sum;
                maxLevel = level;
            }

            if (nextQueue.isEmpty()) break;

            Queue<TreeNode> temp = queue;
            queue = nextQueue;
            nextQueue = temp;
            level++;
        }

        return maxLevel;
    }
}
