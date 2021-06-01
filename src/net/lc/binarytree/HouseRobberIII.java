package net.lc.binarytree;

/**
 * 337
 */
class HouseRobberIII {

    static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    public int rob(TreeNode root) {
        if (root == null) return 0;

        int val1 = traverse(root, true);
        int val2 = traverse(root, false);
        return Math.max(val1, val2);
    }

    public int traverse(TreeNode curNode, boolean includeCurNode) {
        if (curNode == null) return 0;

        if (includeCurNode) {
            int val1 = curNode.val + traverse(curNode.left, false) + traverse(curNode.right, false);
            int val2 = traverse(curNode.left, true) + traverse(curNode.right, true);
            return Math.max(val1, val2);
        } else {
            return traverse(curNode.left, true) + traverse(curNode.right, true);
        }
    }
}