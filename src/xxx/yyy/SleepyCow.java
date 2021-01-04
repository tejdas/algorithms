package xxx.yyy;

import java.util.Scanner;

public class SleepyCow {
    static class DListNode {
        int val;
        DListNode prev;
        DListNode next;

        public DListNode(int val) {
            this.val = val;
            prev = null;
            next = null;
        }
    }

    private DListNode head;
    private DListNode tail;
    private DListNode left;
    private DListNode right;

    public static void main(String[] args) {
        SleepyCow p = new SleepyCow();
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int[] arr = new int[n];

        for (int i = 0; i < n; i++) {
            arr[i] = in.nextInt();
        }
        System.out.println(p.cowSorting(arr));
        in.close();
    }

    private void buildDoublyLinkedList(int[] arr) {
        DListNode firstNode = new DListNode(arr[0]);
        head = firstNode;
        tail = firstNode;
        for (int i = 1; i < arr.length; i++) {
            DListNode dnode = new DListNode(arr[i]);
            tail.next = dnode;
            dnode.prev = tail;
            tail = dnode;
        }
    }

    public int cowSorting(int[] arr) {
        if (arr.length == 1) {
            return 0;
        }

        buildDoublyLinkedList(arr);
        left = tail;
        right = tail;

        while (left.prev != null && left.prev.val < left.val) {
            left = left.prev;
        }

        int timeSteps = 0;

        while (true) {
            DListNode obj = head;

            if (left == head) return timeSteps;

            head = obj.next;
            head.prev = null;

            if (obj.val > right.val) {
                /**
                 * put obj after right
                 */
                right.next = obj;
                obj.prev = right;
                right = obj;
            } else if (obj.val < left.val) {
                /**
                 * put obj before left
                 */
                DListNode prev = left.prev;
                obj.next = left;
                left.prev = obj;

                prev.next = obj;
                obj.prev = prev;
                left = obj;
            } else {
                DListNode cur = right;
                while (obj.val < cur.val) {
                    cur = cur.prev;
                }
                /**
                 * put obj after cur
                 */
                DListNode next = cur.next;
                cur.next = obj;
                obj.prev = cur;
                obj.next = next;
                next.prev = obj;
            }
            timeSteps++;
        }
    }
}