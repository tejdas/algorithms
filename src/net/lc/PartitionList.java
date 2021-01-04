package net.lc;

public class PartitionList {
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

    public ListNode partition(ListNode head, int x) {

        ListNode cur = head;
        ListNode prev = null;
        while (cur != null && cur.val != x) {
            prev = cur;
            cur = cur.next;
        }

        prev.next = null;

        ListNode list1 = head;
        ListNode list2 = cur.next;




        return null;
    }
}
