package net.lc.binarytree;

/**
 * 98
 * BST
 */
public class ValidateBST {
    static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        public TreeNode(int val) {
            this.val = val;
        }
    }

    private long lastSeenValue = Long.MIN_VALUE;

    public boolean isValidBST(final TreeNode root) {
        if (root == null) {
            return true;
        }

        if (root.left == null && root.right == null) {
            return true;
        }

        return isBinarySearchTree(root);
    }

    boolean isBinarySearchTree(final TreeNode node) {
        if (node.left != null) {
            if (!isBinarySearchTree(node.left))
                return false;
        }

        if (node.val <= lastSeenValue)
            return false;
        lastSeenValue = node.val;
        if (node.right != null)
            return isBinarySearchTree(node.right);
        else
            return true;
    }
}
