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
     * Thing to note here is that, here, the recursive function does not
     * directly return the result, but it is used to compute a partial result,
     * and the (max) result is stored in a class-member attribute.
     * @param curNode
     * @return
     */
    private int maxPathEndingAtCurNode(TreeNode curNode) {
        if (curNode == null) return 0;

        int maxLeft = maxPathEndingAtCurNode(curNode.left);
        int maxRight = maxPathEndingAtCurNode(curNode.right);

        /**
         * maxSum stores the maxSum so far starting from a lchild and going thru the node and ending at a rchild.
         */
        maxSum = Math.max(maxSum, curNode.val + Math.max(0, maxLeft) + Math.max(0, maxRight));

        /**
         * maxPathEndingAtCurNode is calculated from two values:
         * max of (LChildTraversal + RChildTraversal) + curNode
         */
        int maxChildVal = Math.max(maxLeft, maxRight);
        return curNode.val + Math.max(0, maxChildVal);
    }
}
