package net.lc;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

/**
 * https://leetcode.com/problems/construct-binary-tree-from-preorder-and-postorder-traversal/submissions/
 * Binary Tree
 */
public class ConstructBinaryTreeFromPrePost {
    static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int val) {
            this.val = val;
        }

        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    private int[] pre;
    private int[] post;
    private final Map<Integer, Integer> preMap = new HashMap<>();
    private final Map<Integer, Integer> postMap = new HashMap<>();

    public TreeNode constructFromPrePost(int[] pre, int[] post) {
        if (pre == null || pre.length == 0) return null;
        if (pre.length == 1) return new TreeNode(pre[0]);

        this.pre = pre;
        this.post = post;

        for (int i = 0; i < pre.length; i++) {
            preMap.put(pre[i], i);
            postMap.put(post[i], i);
        }
        return construct(0, pre.length-1, 0, post.length-1);
    }

    private TreeNode construct(int leftPre, int rightPre, int leftPost, int rightPost) {
        if (leftPre == rightPre) return new TreeNode(pre[leftPre]);

        TreeNode curNode = new TreeNode(pre[leftPre]);

        int lc = pre[leftPre+1];

        int lcPostPos = postMap.get(lc);

        if (lcPostPos == rightPost-1) {
            // no right tree. Only left tree

            curNode.left = construct(leftPre+1, rightPre, leftPost, rightPost-1);

        } else {
            int rc = post[rightPost-1];
            int rcPrePos = preMap.get(rc);

            curNode.left = construct(leftPre+1, rcPrePos-1, leftPost, lcPostPos);

            curNode.right = construct(rcPrePos, rightPre, lcPostPos+1, rightPost-1);
        }

        return curNode;
    }

    private void printLevelOrder(TreeNode root) {
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);

        while (!queue.isEmpty()) {
            TreeNode cur = queue.remove();
            System.out.print(cur.val + "  ");
            if (cur.left != null) queue.add(cur.left);
            if (cur.right != null) queue.add(cur.right);
        }
        System.out.println();
    }

    public static void main(String[] args) {
        {
            int[] pre = { 1, 2, 4, 5, 3, 6, 7 };
            int[] post = { 4, 5, 2, 6, 7, 3, 1 };

            ConstructBinaryTreeFromPrePost driver = new ConstructBinaryTreeFromPrePost();
            TreeNode root = driver.constructFromPrePost(pre, post);
            driver.printLevelOrder(root);
        }

        {
            int[] pre = { 2,1};
            int[] post = { 1,2 };

            ConstructBinaryTreeFromPrePost driver = new ConstructBinaryTreeFromPrePost();
            TreeNode root = driver.constructFromPrePost(pre, post);
            driver.printLevelOrder(root);
        }
    }
}
