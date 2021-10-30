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

    /**
     *
     * @param curNode
     * @param mayIncludeCurNode; if true, then may or may not include curNode. If false, definitely do not include curNode.
     * @return
     */
    public int traverse(TreeNode curNode, boolean mayIncludeCurNode) {
        if (curNode == null) return 0;

        if (mayIncludeCurNode) {
            /**
             * Two options:
             * Include curNode and exclude children
             * OR
             * Exclude curNode and include children
             * This makes sure that not all alternate nodes in the hierarchy are automatically selected.
             */
            int val1 = curNode.val + traverse(curNode.left, false) + traverse(curNode.right, false);
            int val2 = traverse(curNode.left, true) + traverse(curNode.right, true);
            return Math.max(val1, val2);
        } else {
            return traverse(curNode.left, true) + traverse(curNode.right, true);
        }
    }
}