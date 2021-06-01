package net.lc.binarytree;

/**
 * 104
 */
public class MaxDepthBinaryTree {

    static class TreeNode {
        int val;
        TreeNode left, right;

        public TreeNode(int val) {
            this.val = val;
        }
    }

    public int maxDepth(TreeNode root) {
        return findDepth(root);
    }

    public int findDepth(TreeNode curNode) {
        if (curNode == null)
            return 0;

        int leftDepth = findDepth(curNode.left);
        int rightDepth = findDepth(curNode.right);

        return 1 + Math.max(leftDepth, rightDepth);
    }
}
