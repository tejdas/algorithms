package net.lc.binarytree;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 297
 */
public class BinaryTreeSerde {
    static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        public TreeNode(int val) {
            this.val = val;
        }
    }
    private static final TreeNode NULL_TREE_NODE = new TreeNode(1001);

    public String serialize(TreeNode root) {
        if (root == null) {
            return "";
        }

        StringBuilder sb = new StringBuilder();

        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);

        while (!queue.isEmpty()) {
            TreeNode curNode = queue.remove();
            if (curNode.val == NULL_TREE_NODE.val) {

                if (!queue.isEmpty()) {
                    sb.append("N").append(',');
                } else {
                    sb.append("N");
                }
                continue;
            }

            sb.append(String.valueOf(curNode.val)).append(',');

            if (curNode.left != null) {
                queue.add(curNode.left);
            } else {
                queue.add(NULL_TREE_NODE);
            }

            if (curNode.right != null) {
                queue.add(curNode.right);
            } else {
                queue.add(NULL_TREE_NODE);
            }
        }

        return sb.toString();
    }

    public TreeNode deserialize(String data) {
        TreeNode root = null;
        try {
            if (data == null || data.isEmpty()) {
                return null;
            }

            String[] tokens = data.split(",");
            int index = 0;

            root = new TreeNode(getVal(tokens[index]));
            Queue<TreeNode> queue = new LinkedList<>();
            queue.add(root);

            while (!queue.isEmpty() && (index < tokens.length-1)) {
                TreeNode curNode = queue.remove();

                String token = tokens[++index];
                if (!token.equals("N")) {
                    curNode.left = new TreeNode(getVal(token));
                    queue.add(curNode.left);
                }

                if (index == tokens.length) {
                    break;
                }

                token = tokens[++index];
                if (!token.equals("N")) {
                    curNode.right = new TreeNode(getVal(token));
                    queue.add(curNode.right);
                }
            }
        } catch (NumberFormatException ex) {
            return null;
        }
        return root;
    }

    private int getVal(String str) {
        return Integer.valueOf(str);
    }
}
