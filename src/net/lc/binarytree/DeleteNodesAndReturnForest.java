package net.lc.binarytree;

import java.util.*;

/**
 * https://leetcode.com/problems/delete-nodes-and-return-forest/submissions/
 * Binary Tree
 * 1110
 * BFS
 */
public class DeleteNodesAndReturnForest {
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

    static class TreeInfo {
        TreeNode cur;
        TreeNode par;
        boolean isLeft;

        public TreeInfo(TreeNode cur, TreeNode par, boolean isLeft) {
            this.cur = cur;
            this.par = par;
            this.isLeft = isLeft;
        }
    }

    private final Stack<TreeInfo> stack = new Stack<>();

    public List<TreeNode> delNodes(TreeNode root, int[] td) {
        Set<Integer> toDelete = new HashSet<>();
        for (int val : td)
            toDelete.add(val);

        TreeInfo rootInfo = new TreeInfo(root, null, true);

        Queue<TreeInfo> queue = new LinkedList<>();
        queue.add(rootInfo);

        /**
         * Traverse the tree level-order, and collect parent, and isLeft/Right info.
         * Push the to-be-deleted nodes in stack, so that we will consider bottom-up fashion.
         */
        while (!queue.isEmpty()) {
            TreeInfo curNode = queue.remove();
            if (toDelete.contains(curNode.cur.val)) {
                stack.push(curNode);
            }

            if (curNode.cur.left != null) {
                queue.add(new TreeInfo(curNode.cur.left, curNode.cur, true));
            }

            if (curNode.cur.right != null) {
                queue.add(new TreeInfo(curNode.cur.right, curNode.cur, false));
            }
        }

        List<TreeNode> result = new ArrayList<>();
        boolean rootDeleted = false;

        while (!stack.isEmpty()) {
            TreeInfo treeInfo = stack.pop();
            /**
             * TreeInfo is being deleted. So, it's left-child  and right child become root of a forest.
             * So add those to result.
             * Also, if the TreeInfo is left-child of its parent, set the TreeInfo's parent's left-child to null.
             * OR
             * if the TreeInfo is right-child of its parent, set the TreeInfo's parent's right-child to null.
             */
            if (treeInfo.cur.left != null) {
                result.add(treeInfo.cur.left);
            }

            if (treeInfo.cur.right != null) {
                result.add(treeInfo.cur.right);
            }

            if (treeInfo.par == null) {
                rootDeleted = true;
            } else {
                if (treeInfo.isLeft) {
                    treeInfo.par.left = null;
                } else {
                    treeInfo.par.right = null;
                }
            }
        }

        if (!rootDeleted)
            result.add(root);
        return result;
    }

    public static void main(String[] args) {
        {
            /*
            TreeNode rootNode = BinaryTreeBuilder.buildTree("1,2,3,4,5,6,7");
            List<TreeNode> result = new DeleteNodesAndReturnForest().delNodes(rootNode, new int[]{3,5});

            for (TreeNode r : result) {
                BinaryTreeBuilder.preorder(r);
            }
            System.out.println("------------------------------");
            */
        }

        {
            /*
            TreeNode rootNode = BinaryTreeBuilder.buildTree("1,2,null,4,3");
            List<TreeNode> result = new DeleteNodesAndReturnForest().delNodes(rootNode, new int[]{2,3});

            for (TreeNode r : result) {
                BinaryTreeBuilder.preorder(r);
            }
            System.out.println("------------------------------");
            */
        }
/*
        {
            TreeNode rootNode = BinaryTreeBuilder.buildTree("1,2,3,null,null,null,4");
            List<TreeNode> result = new DeleteNodesAndReturnForest().delNodes(rootNode, new int[] { 2, 1 });

            for (TreeNode r : result) {
                BinaryTreeBuilder.preorder(r);
            }
            System.out.println("------------------------------");
        }
        */
    }
}
