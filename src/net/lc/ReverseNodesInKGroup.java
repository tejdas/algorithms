package net.lc;

/**
 * https://leetcode.com/problems/reverse-nodes-in-k-group/submissions/
 * Linked-List
 */
public class ReverseNodesInKGroup {
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

    public ListNode reverseKGroup(ListNode head, int k) {
        ListNode newHead = null;

        ListNode globalCur = head;
        ListNode tail = null;

        while (true) {

            ListNode newTail = globalCur;

            ListNode prev = null;
            ListNode cur = globalCur;
            int i;
            for (i = 0; i < k; i++) {

                if (cur == null) break;
                ListNode next = cur.next;
                cur.next = prev;
                prev = cur;
                cur = next;
            }

            if (i < k) {
                // fix

                //System.out.println("fixing");
                cur = prev;
                prev = null;
                for (int j = 0; j < i; j++) {
                    ListNode next = cur.next;
                    cur.next = prev;
                    prev = cur;
                    cur = next;
                }

                if (newHead == null) return head;

                tail.next = prev;
                return newHead;
            }

            if (tail != null) {
                tail.next = prev;
            } else {
                newHead = prev;
            }

            globalCur = cur;
            if (globalCur == null) break;

            tail = newTail;
        }

        return newHead;
    }

    private static ListNode buildList(int[] array) {
        ListNode head = null;
        ListNode prev = null;
        for (int val : array) {
            ListNode cur = new ListNode(val);

            if (head == null) {
                head = cur;
                prev = cur;
            } else {
                prev.next = cur;
                prev = cur;
            }
        }

        return head;
    }

    private static void print(ListNode l) {
        while (l != null) {
            System.out.print(l.val + " ");
            l = l.next;
        }
        System.out.println();
    }

    public static void main(String[] args) {
        {
            ListNode head = buildList(new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12 });
            print(head);

            ListNode res = new ReverseNodesInKGroup().reverseKGroup(head, 4);
            print(res);
        }

        {
            ListNode head = buildList(new int[] { 1, 2, 3, 4, 5, 6, 7 });
            print(head);

            ListNode res = new ReverseNodesInKGroup().reverseKGroup(head, 2);
            print(res);
        }
    }
}
