package net.lc;

/**
 * 19
 */
public class RemoveNthNodeFromEndOfList {
    static class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }
    }

    public ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode prev = null;
        ListNode cur = head;
        ListNode next = cur;
        for (int i = 0; i < n && next != null; i++) {
            next = next.next;
        }

        if (next == null) return head.next;

        while (next != null) {
            prev = cur;
            cur = cur.next;
            next = next.next;
        }

        prev.next = cur.next;
        return head;
    }
}
