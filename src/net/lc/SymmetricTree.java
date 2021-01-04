package net.lc;

/**
 * 101
 * Binary Tree
 */
public class SymmetricTree {
    static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }
    public boolean isSymmetric(TreeNode root) {
        if (root == null) return true;
        return checkSymmetry(root.left, root.right);
    }

    private boolean checkSymmetry(TreeNode node1, TreeNode node2) {
        if (node1 == null && node2 == null) {
            return true;
        }

        if (node1 != null && node2 != null) {
            return (node1.val == node2.val && checkSymmetry(node1.left, node2.right) && checkSymmetry(node1.right, node2.left));
        }

        return false;
    }
}
