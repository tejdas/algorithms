package net.lc.binarytree;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * 199
 * Binary Tree
 * BFS
 */
public class BinaryTreeRightSideView {
    static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        public TreeNode(int val) {
            this.val = val;
        }
    }

    /**
     * Level-order (BFS) traversal
     * Keep track of rightMostNode
     * When the curNode becomes rightMostNode, print it, and set it to null
     * When rightMostNode is set to null, mark the next child node (in the below-level) as rightMostNode.
     * @param root
     * @return
     */
    public List<Integer> rightSideView(TreeNode root) {

        List<Integer> rightmostNodes = new ArrayList<>();
        if (root == null) {
            return rightmostNodes;
        }

        Queue<TreeNode> queue = new LinkedList<>();

        queue.add(root);

        TreeNode rightMostNode = root;
        while (!queue.isEmpty()) {
            TreeNode curNode = queue.poll();

            if (curNode == rightMostNode) {
                rightmostNodes.add(curNode.val);
                rightMostNode = null;
            }

            if (curNode.right != null) {
                if (rightMostNode == null) {
                    rightMostNode = curNode.right;
                }
                queue.add(curNode.right);
            }

            if (curNode.left != null) {
                if (rightMostNode == null) {
                    rightMostNode = curNode.left;
                }
                queue.add(curNode.left);
            }
        }
        return rightmostNodes;
    }
}
