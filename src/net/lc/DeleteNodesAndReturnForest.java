package net.lc;

import net.lc.BinaryTreeBuilder.TreeNode;

import java.util.*;

/**
 * https://leetcode.com/problems/delete-nodes-and-return-forest/submissions/
 * Binary Tree
 * BFS
 */
public class DeleteNodesAndReturnForest {
    static class TreeInfo {
        TreeNode cur;
        TreeNode par;
        boolean isLeft;
        int label;

        public TreeInfo(TreeNode cur, TreeNode par, boolean isLeft, int label) {
            this.cur = cur;
            this.par = par;
            this.isLeft = isLeft;
            this.label = label;
        }
    }

    private final Stack<TreeInfo> stack = new Stack<>();
    public List<TreeNode> delNodes(TreeNode root, int[] td) {
        Set<Integer> toDelete = new HashSet<>();
        for (int val : td) toDelete.add(val);

        TreeInfo rootInfo = new TreeInfo(root, null, true, 0);

        Queue<TreeInfo> queue = new LinkedList<>();
        queue.add(rootInfo);

        while (!queue.isEmpty()) {
            TreeInfo curNode = queue.remove();
            if (toDelete.contains(curNode.cur.val)) {
                stack.push(curNode);
            }

            if (curNode.cur.left != null) {
                queue.add(new TreeInfo(curNode.cur.left, curNode.cur, true, curNode.label+1));
            }

            if (curNode.cur.right != null) {
                queue.add(new TreeInfo(curNode.cur.right, curNode.cur, false, curNode.label+1));
            }
        }

        //Collections.sort(list);

        List<TreeNode> result = new ArrayList<>();
        boolean rootDeleted = false;

        while (!stack.isEmpty()) {
            TreeInfo treeInfo = stack.pop();
            //System.out.println("processing: " + treeInfo.cur.val + "    val: " + treeInfo.val);
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

        {
            TreeNode rootNode = BinaryTreeBuilder.buildTree("1,2,3,null,null,null,4");
            List<TreeNode> result = new DeleteNodesAndReturnForest().delNodes(rootNode, new int[]{2,1});

            for (TreeNode r : result) {
                BinaryTreeBuilder.preorder(r);
            }
            System.out.println("------------------------------");
        }
    }
}
