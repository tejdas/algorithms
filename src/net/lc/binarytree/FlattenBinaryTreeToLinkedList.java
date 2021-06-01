package net.lc.binarytree;

/**
 * 114
 * Binary Tree
 * Pre-order
 */
public class FlattenBinaryTreeToLinkedList {
    static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) { val = x; }
    }

    private TreeNode prev = null;
    public void flatten(TreeNode root) {
        if (root == null) return;
        flattenPreOrder(root, null, false);

        TreeNode cur = root;
        while (cur != null) {
            cur.right = cur.left;
            cur.left = null;
            cur = cur.right;
        }
    }

    private void flattenPreOrder(TreeNode cur, TreeNode parent, boolean isRight) {
        if (isRight) {
            if (parent != null) {
                parent.right = null;
            }
            if (prev != null) {
                prev.left = cur;
            }
        }

        prev = cur;
        if (cur.left != null) {
            flattenPreOrder(cur.left, cur, false);
        }

        if (cur.right != null) {
            flattenPreOrder(cur.right, cur, true);
        }
    }
}
