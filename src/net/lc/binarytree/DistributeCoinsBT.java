package net.lc.binarytree;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 979
 * https://leetcode.com/problems/distribute-coins-in-binary-tree/submissions/
 * Binary Tree
 * DFS
 */
public class DistributeCoinsBT {
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

    static class TreeNodeInfo {
        TreeNode treeNode;
        int numNodes;
        int numCoins;

        TreeNodeInfo left;
        TreeNodeInfo right;

        public TreeNodeInfo(TreeNode treeNode) {
            this.treeNode = treeNode;
        }
    }

    private int result = 0;

    public int distributeCoins(TreeNode root) {
        TreeNodeInfo rootPair = populateTreeNodeInfo(root);
        distribute(rootPair, 0);
        return result;
    }

    private TreeNodeInfo populateTreeNodeInfo(TreeNode treeNode) {
        if (treeNode == null) return null;

        TreeNodeInfo treeNodeInfo = new TreeNodeInfo(treeNode);

        int numNodes = 1;
        int numCoins = treeNodeInfo.treeNode.val;

        if (treeNode.left != null) {
            treeNodeInfo.left = populateTreeNodeInfo(treeNode.left);
            numNodes += treeNodeInfo.left.numNodes;
            numCoins += treeNodeInfo.left.numCoins;
        }

        if (treeNode.right != null) {
            treeNodeInfo.right = populateTreeNodeInfo(treeNode.right);
            numNodes += treeNodeInfo.right.numNodes;
            numCoins += treeNodeInfo.right.numCoins;
        }

        treeNodeInfo.numCoins = numCoins;
        treeNodeInfo.numNodes = numNodes;

        return treeNodeInfo;
    }

    /**
     * Post-order
     * @param treeNodeInfo
     * @param extraCoins: got extra coins from parent
     * @return
     */
    private int distribute(TreeNodeInfo treeNodeInfo, int extraCoins) {
        if (extraCoins > 0) {
            result += extraCoins;
        }

        int gotFromChild = 0;
        int gaveChild = 0;

        if (treeNodeInfo.left != null) {
            if (treeNodeInfo.left.numCoins == treeNodeInfo.left.numNodes) {
                distribute(treeNodeInfo.left, 0);
            } else if (treeNodeInfo.left.numCoins > treeNodeInfo.left.numNodes) {
                int got = distribute(treeNodeInfo.left, 0);

                result += got;
                gotFromChild += got;
            } else {
                gaveChild += (treeNodeInfo.left.numNodes - treeNodeInfo.left.numCoins);
                distribute(treeNodeInfo.left, (treeNodeInfo.left.numNodes - treeNodeInfo.left.numCoins));
            }
        }

        if (treeNodeInfo.right != null) {
            if (treeNodeInfo.right.numCoins == treeNodeInfo.right.numNodes) {
                distribute(treeNodeInfo.right, 0);
            } else if (treeNodeInfo.right.numCoins > treeNodeInfo.right.numNodes) {
                int got = distribute(treeNodeInfo.right, 0);

                result += got;
                gotFromChild += got;
            } else {
                gaveChild += (treeNodeInfo.right.numNodes - treeNodeInfo.right.numCoins);
                distribute(treeNodeInfo.right, (treeNodeInfo.right.numNodes - treeNodeInfo.right.numCoins));
            }
        }

        int surplus = gotFromChild - gaveChild + extraCoins + treeNodeInfo.treeNode.val -1;
        treeNodeInfo.treeNode.val = 1;
        return surplus;
    }

    static TreeNode NilNode = new TreeNode(-1);

    private static TreeNode buildTree(String input) {
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

    public static void main(String[] args) {
        {
            TreeNode rootNode = buildTree("0,1,null,3,0");
            System.out.println(new DistributeCoinsBT().distributeCoins(rootNode));
        }

        {
            TreeNode rootNode = buildTree("3,null,0,0,null,0,null,2");
            System.out.println(new DistributeCoinsBT().distributeCoins(rootNode));
        }
    }
}
