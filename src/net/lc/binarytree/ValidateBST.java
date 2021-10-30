package net.lc.binarytree;

/**
 * 98
 * BST
 * In-order traversal.
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

    /**
     * Use to validate that the inorder traversal is sorted.
     */
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
        if (node == null) return true;

        if (!isBinarySearchTree(node.left))
            return false;

        if (node.val <= lastSeenValue)
            return false;
        lastSeenValue = node.val;

        return isBinarySearchTree(node.right);
    }
}
