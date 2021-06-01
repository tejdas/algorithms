package net.lc.binarytree;

/**
 * https://leetcode.com/problems/maximum-difference-between-node-and-ancestor/submissions/
 * Binary Tree
 * DFS
 * 1026
 */
public class MaxDifferenceNodeAndAncestor {
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

    private int maxDiff = 0;
    public int maxAncestorDiff(TreeNode root) {
        if (root == null) return maxDiff;
        dfs(root, root.val, root.val);
        return maxDiff;
    }

    private void dfs(TreeNode cur, int min, int max) {
        if (cur == null) return;

        int curval = cur.val;
        int diff = Math.max(Math.abs(cur.val-min), Math.abs(cur.val-max));
        maxDiff = Math.max(maxDiff, diff);

        dfs(cur.left, Math.min(min, curval), Math.max(max, curval));
        dfs(cur.right, Math.min(min, curval), Math.max(max, curval));
    }

    public static void main(String[] args) {
        //TreeNode rootNode = BinaryTreeBuilder.buildTree("8,3,10,1,6,null,14,null,null,4,7,13");
        //System.out.println(new MaxDifferenceNodeAndAncestor().maxAncestorDiff(rootNode));
    }
}
