package net.lc.binarytree;

/**
 * 226
 * BinaryTree
 * Post-Order traversal and swap
 */
public class InvertBinaryTree {
    static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) { val = x; }
    }

    public TreeNode invertTree(TreeNode root) {
        return invertTreeRecurse(root);
    }

    private TreeNode invertTreeRecurse(TreeNode node) {
        if (node == null) return null;
        invertTreeRecurse(node.left);
        invertTreeRecurse(node.right);
        TreeNode temp = node.left;
        node.left = node.right;
        node.right = temp;
        return node;
    }
}
