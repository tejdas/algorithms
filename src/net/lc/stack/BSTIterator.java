package net.lc.stack;

import java.util.Stack;

/**
 * Stack
 * 173
 */
public class BSTIterator {
    static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    TreeNode curNode;
    final Stack<TreeNode> stack = new Stack<>();

    public BSTIterator(TreeNode root) {
        curNode = new TreeNode(-1);
        curNode.right = root;
    }

    /**
     * @return whether we have a next smallest number
     */
    public boolean hasNext() {
        curNode = curNode.right;

        if (curNode != null) {
            while (curNode.left != null) {
                stack.push(curNode);
                curNode = curNode.left;
            }
        } else {
            if (!stack.isEmpty()) {
                curNode = stack.pop();
            }
        }

        return (curNode != null);
    }

    /**
     * @return the next smallest number
     */
    public int next() {
        return curNode.val;
    }
}
