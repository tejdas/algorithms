package net.lc;

import java.util.LinkedList;
import java.util.Queue;

/**
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
        int nl = 0;
        int nr = 0;
        int coinl = 0;
        int coinr = 0;

        TreeNodeInfo left;
        TreeNodeInfo right;

        public TreeNodeInfo(TreeNode treeNode) {
            this.treeNode = treeNode;
        }
    }

    private int result = 0;

    public int distributeCoins(TreeNode root) {
        Pair rootPair = populateTreeNodeInfo(root);
        distribute(rootPair.treeNodeInfo, 0);
        return result;
    }

    static class Pair {
        TreeNodeInfo treeNodeInfo;
        int numNodes;
        int numCoins;

        public Pair(TreeNodeInfo treeNodeInfo, int numNodes, int numCoins) {
            this.treeNodeInfo = treeNodeInfo;
            this.numNodes = numNodes;
            this.numCoins = numCoins;
        }
    }

    private Pair populateTreeNodeInfo(TreeNode treeNode) {
        if (treeNode == null) return null;

        TreeNodeInfo treeNodeInfo = new TreeNodeInfo(treeNode);

        int numNodes = 1;
        int numCoins = treeNodeInfo.treeNode.val;

        if (treeNode.left != null) {
            Pair lp = populateTreeNodeInfo(treeNode.left);
            treeNodeInfo.nl = lp.numNodes;
            treeNodeInfo.coinl = lp.numCoins;
            treeNodeInfo.left = lp.treeNodeInfo;
        }

        if (treeNode.right != null) {
            Pair lp = populateTreeNodeInfo(treeNode.right);
            treeNodeInfo.nr = lp.numNodes;
            treeNodeInfo.coinr = lp.numCoins;
            treeNodeInfo.right = lp.treeNodeInfo;
        }

        numNodes += treeNodeInfo.nl + treeNodeInfo.nr;
        numCoins += treeNodeInfo.coinl + treeNodeInfo.coinr;

        return new Pair(treeNodeInfo, numNodes, numCoins);
    }

    private int distribute(TreeNodeInfo treeNodeInfo, int extraCoins) {
        if (extraCoins > 0) {
            //System.out.println("From parent, TreeNode: " + treeNodeInfo.treeNode.val + " got extra coins: " + extraCoins);
            result += extraCoins;
        }

        if (treeNodeInfo.left != null && treeNodeInfo.coinl == treeNodeInfo.nl) {
            distribute(treeNodeInfo.left, 0);
        }

        if (treeNodeInfo.right != null && treeNodeInfo.coinr == treeNodeInfo.nr) {
            distribute(treeNodeInfo.right, 0);
        }

        int gotFromChild = 0;
        int gaveChild = 0;
        if (treeNodeInfo.coinl > treeNodeInfo.nl) {
            int got = distribute(treeNodeInfo.left, 0);
            if (got > 0) {
                //System.out.println("From left-child, TreeNode: " + treeNodeInfo.treeNode.val + " got surplus coins: " + got);
            }
            result += got;
            gotFromChild += got;
        }

        if (treeNodeInfo.coinr > treeNodeInfo.nr) {
            int got = distribute(treeNodeInfo.right, 0);
            if (got > 0) {
               //System.out.println("From right-child, TreeNode: " + treeNodeInfo.treeNode.val + " got surplus coins: " + got);
            }
            result += got;
            gotFromChild += got;
        }

        if (treeNodeInfo.coinl < treeNodeInfo.nl) {
            gaveChild += (treeNodeInfo.nl - treeNodeInfo.coinl);
            distribute(treeNodeInfo.left, (treeNodeInfo.nl - treeNodeInfo.coinl));
        }

        if (treeNodeInfo.coinr < treeNodeInfo.nr) {
            gaveChild += (treeNodeInfo.nr - treeNodeInfo.coinr);
            distribute(treeNodeInfo.right, (treeNodeInfo.nr - treeNodeInfo.coinr));
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
