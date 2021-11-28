package net.lc.binarytree;

import java.util.HashMap;
import java.util.Map;

/**
 * 105
 * Binary Tree
 * Preorder Recursion
 */
public class ConstructBinaryTreeFromPreAndInorder {
    static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) { val = x; }
    }

    private int[] preorder = null;
    private int preIndex = 0;
    private final Map<Integer, Integer> inorderMap = new HashMap<>();

    public TreeNode buildTree(int[] preorder, int[] inorder) {
        this.preorder = preorder;
        for (int i = 0; i < inorder.length; i++) {
            inorderMap.put(inorder[i], i);
        }
        return buildTreeRecurse(inorder, 0, inorder.length-1);
    }

    private TreeNode buildTreeRecurse(int[] inorder, int left, int right) {
        if (left > right) return null;

        int nodeval = preorder[preIndex++];
        TreeNode node = new TreeNode(nodeval);
        if (left == right) return node;

        int index = inorderMap.get(nodeval);
        node.left = buildTreeRecurse(inorder, left, index-1);
        node.right = buildTreeRecurse(inorder, index+1, right);
        return node;
    }
}
