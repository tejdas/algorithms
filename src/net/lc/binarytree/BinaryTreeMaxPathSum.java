package net.lc.binarytree;

/**
 * 124
 * Binary Tree
 */
public class BinaryTreeMaxPathSum {
    static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        int key = 0;
        TreeNode(int x) { val = x; }
    }
    private int maxSum = Integer.MIN_VALUE;

    public int maxPathSum(TreeNode root) {
        maxSum = Integer.MIN_VALUE;
        maxPathEndingAtCurNode(root);
        return maxSum;
    }


    /**
     * Max sum from one of the child nodes ending at curNode.
     * @param curNode
     * @return
     */
    private int maxPathEndingAtCurNode(TreeNode curNode) {
        if (curNode == null) return 0;

        int maxLeft = maxPathEndingAtCurNode(curNode.left);
        int maxRight = maxPathEndingAtCurNode(curNode.right);

        maxSum = Math.max(maxSum, curNode.val + Math.max(0, maxLeft) + Math.max(0, maxRight));

        return Math.max(curNode.val, curNode.val + Math.max(maxLeft, maxRight));
    }
}
