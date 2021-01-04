package algo.linkedlist;

public class SortedDuplicateRemover {

    static class ListNode {
        public int val;
        public ListNode next;
        ListNode(int x) {
            this.val = x;
        }
    }

    public ListNode deleteDuplicates(ListNode head) {
        if (head == null) {
            return null;
        }

        ListNode cur = head;
        ListNode duplicateTracker = cur;
        while (cur != null) {
            while (duplicateTracker != null && duplicateTracker.val == cur.val) {
                duplicateTracker = duplicateTracker.next;
            }

            cur.next = duplicateTracker;
            cur = duplicateTracker;
        }

        return head;
    }

    public static void main(String[] args) {
        ListNode n1 = new ListNode(1);
        ListNode n2 = new ListNode(1);
        ListNode n3 = new ListNode(2);
        ListNode n4 = new ListNode(3);
        ListNode n5 = new ListNode(3);

        n1.next = n2;
        n2.next = n3;
        n3.next = n4;
        n4.next = n5;

        ListNode head = new SortedDuplicateRemover().deleteDuplicates(n1);

        while (head != null) {
            System.out.println(head.val);
            head = head.next;
        }

    }
}
