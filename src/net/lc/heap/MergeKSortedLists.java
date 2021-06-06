package net.lc.heap;

import java.util.PriorityQueue;

/**
 * 23
 * PriorityQueue
 */
public class MergeKSortedLists {
    static class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }
    }
    static class EnListNode implements Comparable<EnListNode> {
        public EnListNode(ListNode node) {
            this.node = node;
        }

        @Override
        public int compareTo(EnListNode o) {
            return Integer.compare(this.node.val, o.node.val);
        }

        private final ListNode node;
    }

    public ListNode mergeKLists(ListNode[] lists) {
        if (lists == null || lists.length == 0) return null;
        ListNode head = null;
        ListNode prev = null;

        PriorityQueue<EnListNode> pq = new PriorityQueue<>();

        for (ListNode ln : lists) {
            if (ln != null) {
                pq.add(new EnListNode(ln));
            }
        }

        while (!pq.isEmpty()) {
            EnListNode enListNode = pq.remove();
            if (prev == null) {
                head = enListNode.node;
                prev = enListNode.node;
            } else {
                prev.next = enListNode.node;
                prev = enListNode.node;
            }

            if (enListNode.node.next != null) {
                pq.add(new EnListNode(enListNode.node.next));
            }
        }
        if (prev != null) {
            prev.next = null;
        }

        return head;
    }
}
