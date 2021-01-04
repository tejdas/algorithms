package net.lc;
import net.lc.BinaryTreeBuilder.TreeNode;

/**
 * https://leetcode.com/problems/maximum-difference-between-node-and-ancestor/submissions/
 * Binary Tree
 * DFS
 */
public class MaxDifferenceNodeAndAncestor {

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
        TreeNode rootNode = BinaryTreeBuilder.buildTree("8,3,10,1,6,null,14,null,null,4,7,13");
        System.out.println(new MaxDifferenceNodeAndAncestor().maxAncestorDiff(rootNode));
    }
}
