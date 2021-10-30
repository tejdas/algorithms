package net.lc.binarytree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 366
 */
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
     * Return true only when the current node is a leaf.
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
                /**
                 * cur.left was a leaf, so set to null
                 */
                cur.left = null;
            }
        }

        if (cur.right != null) {
            if (traverse(cur.right, list)) {
                /**
                 * cur.right was a leaf, so set to null
                 */
                cur.right = null;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        root.left.left = new TreeNode(4);
        root.left.right = new TreeNode(5);

        List<List<Integer>> result = new FindLeavesOfBinaryTree().findLeaves(root);
        for (List<Integer> l : result) {
            for (int val : l) System.out.print(val + "  ");
            System.out.println();
        }
    }
}
