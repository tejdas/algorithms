package net.lc;

/**
 * 143
 */
public class ReorderList {
    static class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }
    }
    public void reorderList(ListNode head) {
        if (head == null || head.next == null) {
            return;
        }

        int count = countNodes(head);
        int nodesToSkip = (count+1)/2;

        ListNode cur = head;
        ListNode lastNode = null;
        for (int i = 0; i < nodesToSkip; i++) {
            lastNode = cur;
            cur = cur.next;
        }

        ListNode head2 = reverseList(cur);
        lastNode.next = null;

        ListNode cur1 = head;
        ListNode cur2 = head2;
        for (int i = 0; i < count/2; i++) {
            ListNode cur1Next = cur1.next;
            ListNode cur2Next = cur2.next;
            cur1.next = cur2;
            cur2.next = cur1Next;
            cur1 = cur1Next;
            cur2 = cur2Next;
        }
    }

    public int countNodes(ListNode head) {
        int count = 0;
        ListNode cur = head;
        while (cur != null) {
            count++;
            cur = cur.next;
        }
        return count;
    }
    public ListNode reverseList(ListNode head) {
        ListNode prev = null;
        ListNode cur = head;
        while (cur.next != null) {
            ListNode next = cur.next;
            cur.next = prev;
            prev = cur;
            cur = next;
        }

        cur.next = prev;
        head = cur;
        return head;
    }
}
