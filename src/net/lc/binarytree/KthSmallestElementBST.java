package net.lc.binarytree;

import java.util.Stack;

/**
 * 230
 * Stack
 */
public class KthSmallestElementBST {
    static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) { val = x; }
    }
    private TreeNode curNode;
    private final Stack<TreeNode> stack = new Stack<>();

    /**
     * @return whether we have a next number in inorder traversal
     */
    public boolean hasNext() {
        curNode = curNode.right;

        if (curNode != null) {
            /**
             * Go deep to leftmost
             */
            while (curNode.left != null) {
                stack.push(curNode);
                curNode = curNode.left;
            }
        } else {
            if (!stack.isEmpty()) {
                /**
                 * returning from left child
                 */
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

    /**
     * Start from a dummy-node and make the rootNode the right child of dummy node.
     * Helps in stack-based iteration.
     * @param root
     * @param k
     * @return
     */
    public int kthSmallest(TreeNode root, int k) {
        stack.clear();
        curNode = new TreeNode(-1);
        curNode.right = root;

        int retval = -1;
        for (int i = 0; i < k; i++) {
            if (hasNext()) {
                retval = next();
            }
        }
        return retval;
    }
}
