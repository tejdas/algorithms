package net.lc.stack;

import java.util.Stack;

/**
 * https://leetcode.com/problems/recover-a-tree-from-preorder-traversal/submissions/
 * 1028
 * Stack
 * Preorder
 */
public class RecoverTreeFromPreorderTraversal {
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

    static class NodeWithDepth {
        TreeNode node;
        int depth;

        public NodeWithDepth(TreeNode node, int depth) {
            this.node = node;
            this.depth = depth;
        }
    }

    public TreeNode recoverFromPreorder(String S) {
        return constructBTree(S);
    }

    private TreeNode constructBTree(String s) {

        TreeNode root = null;
        Stack<NodeWithDepth> stack = new Stack<>();

        char[] array = s.toCharArray();

        int i = 0;
        while (i < array.length) {
            int depth = 0;
            int j = i;
            while (array[j] == '-') {
                j++;
                depth++;
            }

            int sum = 0;
            while (array[j] != '-') {
                int val = Character.digit(array[j], 10);
                sum = sum * 10 + val;
                j++;
                if (j == array.length)
                    break;
            }
            i = j;

            TreeNode curNode = new TreeNode(sum);

            if (stack.isEmpty()) {
                root = curNode;
                stack.push(new NodeWithDepth(curNode, depth));
            } else if (depth == stack.peek().depth + 1) {
                TreeNode peekNode = stack.peek().node;
                if (peekNode.left == null) {
                    peekNode.left = curNode;
                }
                stack.push(new NodeWithDepth(curNode, depth));
            } else {
                while (depth <= stack.peek().depth) {
                    stack.pop();
                }

                TreeNode par = stack.peek().node;
                par.right = curNode;
                stack.pop();
                stack.push(new NodeWithDepth(curNode, depth));
            }

        }
        return root;
    }
}
