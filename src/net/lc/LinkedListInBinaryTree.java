package net.lc;

import java.util.ArrayList;
import java.util.List;

/**
 * https://leetcode.com/problems/linked-list-in-binary-tree/submissions/
 */
public class LinkedListInBinaryTree {

    static class ListNode {
        int val;
        ListNode next;

        ListNode() {
        }

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }

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

    public boolean isSubPath(ListNode head, TreeNode root) {

        if (head == null) return true;
        int rootVal = head.val;
        List<TreeNode> startNodes = new ArrayList<>();
        collectRoot(root, startNodes, rootVal);

        for (TreeNode startNode : startNodes) {
            if (match(startNode, head)) return true;
        }
        return false;
    }

    private void collectRoot(TreeNode cur, List<TreeNode> roots, int rootval) {
        if (cur == null) return;
        if (cur.val == rootval) roots.add(cur);
        collectRoot(cur.left, roots, rootval);
        collectRoot(cur.right, roots, rootval);
    }

    private boolean match(TreeNode cur, ListNode listNode) {
        if (cur == null) return false;

        if (cur.val != listNode.val) return false;

        if (listNode.next == null) return true;

        if (match(cur.left, listNode.next)) return true;

        if (match(cur.right, listNode.next)) return true;

        return false;
    }
}
