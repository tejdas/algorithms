package net.lc;

import java.util.LinkedList;
import java.util.Queue;

public class BinaryTreeBuilder {
    static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode() {
        }

        TreeNode(int val) {
            this.val = val;
        }

        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    static TreeNode NilNode = new TreeNode(-1);

    public static TreeNode buildTree(String input) {
        String[] array = input.split(",");
        if (array.length == 0) return null;
        Queue<TreeNode> queue = new LinkedList<>();

        int index = 0;
        int root = Integer.parseInt(array[index++]);
        TreeNode rootNode = new TreeNode(root);
        queue.add(rootNode);

        while (!queue.isEmpty()) {
            TreeNode curNode = queue.remove();
            if (curNode.val == NilNode.val) continue;

            if (index < array.length) {
                String s = array[index++];
                if (s.equalsIgnoreCase("null")) {
                    queue.add(NilNode);
                } else {
                    curNode.left = new TreeNode(Integer.parseInt(s));
                    queue.add(curNode.left);
                }
            }

            if (index < array.length) {
                String s = array[index++];
                if (s.equalsIgnoreCase("null")) {
                    queue.add(NilNode);
                } else {
                    curNode.right = new TreeNode(Integer.parseInt(s));
                    queue.add(curNode.right);
                }
            }
        }
        return rootNode;
    }

    public static void preorder(TreeNode tNode) {
        preorderRecurse(tNode);
        System.out.println();
    }

    private static void preorderRecurse(TreeNode tNode) {
        if (tNode == null) {
            System.out.print(" null ");
            return;
        }
        System.out.print(tNode.val + " ");
        preorderRecurse(tNode.left);
        preorderRecurse(tNode.right);
    }

}
