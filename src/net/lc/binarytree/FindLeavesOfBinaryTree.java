package net.lc.binarytree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FindLeavesOfBinaryTree {
    static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    private final List<List<Integer>> result = new ArrayList<>();

    public List<List<Integer>> findLeaves(TreeNode root) {
        result.clear();
        if (root == null)
            return result;

        while (root.left != null || root.right != null) {
            List<Integer> list = new ArrayList<>();
            traverse(root, list);
            result.add(list);
        }
        result.add(Collections.singletonList(root.val));
        return result;
    }

    /**
     * Return true if deleted both left and right subtree, and thus current node becomes leaf.
     * @param cur
     * @param list
     * @return
     */
    private boolean traverse(TreeNode cur, List<Integer> list) {
        if (cur.left == null && cur.right == null) {
            list.add(cur.val);
            return true;
        }

        if (cur.left != null) {
            if (traverse(cur.left, list)) {
                cur.left = null;
            }
        }

        if (cur.right != null) {
            if (traverse(cur.right, list)) {
                cur.right = null;
            }
        }
        return false;
    }
}
