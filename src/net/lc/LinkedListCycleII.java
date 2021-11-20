package net.lc;

/**
 * 142
 * Fast slow pointers
 */
public class LinkedListCycleII {
    static class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
            next = null;
        }
    }

        public ListNode detectCycle(ListNode head) {
        if (head == null) return null;
        int loopLength = getLoopLength(head);
        if (loopLength == 0) return null;

        return findLoopStart(head, loopLength);
    }

    private static int loopSize(final ListNode loopStart) {
        int count = 0;
        ListNode cur = loopStart;
        do {
            count++;
            cur = cur.next;
        } while (cur != loopStart);
        return count;
    }

    private static int getLoopLength(final ListNode head) {
        ListNode first = head;
        ListNode second = head;
        while (first != null && second != null) {
            first = first.next;
            if (second.next == null) {
                break;
            }
            second = second.next.next;
            if (first!=null && first==second) {
                return loopSize(first);
            }
        }
        return 0;
    }

    private static ListNode findLoopStart(final ListNode head, final int loopLen) {
        ListNode first = head;
        ListNode second = head;
        for (int i = 0; i < loopLen; i++) {
            second = second.next;
        }
        while (first != second) {
            first = first.next;
            second = second.next;
        }
        return first;
    }
}
