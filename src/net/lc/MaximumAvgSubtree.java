package net.lc;

/**
 * https://leetcode.com/problems/maximum-average-subtree/submissions/
 * Binary Tree Post-order
 */
public class MaximumAvgSubtree {
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

    static class Pair {
        int sum;
        int numNodes;

        public Pair(int sum, int numNodes) {
            this.sum = sum;
            this.numNodes = numNodes;
        }
    }

    private double maxAvg = Double.MIN_VALUE;

    public double maximumAverageSubtree(TreeNode root) {
        if (root == null) return 0;
        countNodes(root);
        return maxAvg;
    }

    private Pair countNodes(TreeNode cur) {
        if (cur == null) return new Pair(0, 0);

        Pair leftInfo = countNodes(cur.left);
        Pair rightInfo = countNodes(cur.right);

        int sum = cur.val + leftInfo.sum + rightInfo.sum;

        int numNodes = 1 + leftInfo.numNodes + rightInfo.numNodes;


        maxAvg = Math.max(maxAvg, (double) sum / numNodes);
        return new Pair(sum, numNodes);
    }

    public static void main(String[] args) {
        TreeNode root = new TreeNode(2);
        root.right = new TreeNode(1);

        System.out.println(new MaximumAvgSubtree().maximumAverageSubtree(root));
    }
}
