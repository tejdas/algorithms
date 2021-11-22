package net.lc.binarytree;

import java.util.Stack;

/**
 * 1008
 * Stack
 * Binary Tree
 */
public class ConstructBSTFromPreorderTraversal {
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

    public TreeNode bstFromPreorder(int[] preorder) {
        if (preorder.length == 0) return null;

        Stack<TreeNode> stack = new Stack<>();
        TreeNode root = new TreeNode(preorder[0]);
        stack.push(root);

        for (int i = 1; i < preorder.length; i++) {
            int curval = preorder[i];
            TreeNode node = new TreeNode(curval);
            if (stack.isEmpty()) {
                stack.push(node);
                continue;
            }

            if (curval < stack.peek().val) {
                stack.peek().left = node;
                stack.push(node);
                continue;
            }

            TreeNode lastPopped = null;
            while (!stack.isEmpty() && curval > stack.peek().val) {
                lastPopped = stack.pop();
            }

            lastPopped.right = node;
            stack.push(node);
        }
        return root;
    }

    static void printInorder(TreeNode node) {
        if (node == null) return;
        printInorder(node.left);
        System.out.println(node.val);
        printInorder(node.right);
    }

    public static void main(String[] args) {
        int[] input = new int[] {8,5,1,7,10,12};
        TreeNode root = new ConstructBSTFromPreorderTraversal().bstFromPreorder(input);
        printInorder(root);
    }
}
