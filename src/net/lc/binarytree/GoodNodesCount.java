package net.lc.binarytree;

/**
 * https://leetcode.com/problems/count-good-nodes-in-binary-tree/submissions/
 * Binary Tree
 * 1448
 */
public class GoodNodesCount {
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

    private int count = 0;
    public int goodNodes(TreeNode root) {
        countGoodNodes(root, Integer.MIN_VALUE);
        return count;
    }


    private void countGoodNodes(TreeNode cur, int maxSoFar) {
        if (cur == null) return;

        if (cur.val >= maxSoFar) {
            count++;
        }

        countGoodNodes(cur.left, Math.max(maxSoFar, cur.val));
        countGoodNodes(cur.right, Math.max(maxSoFar, cur.val));
    }
}
