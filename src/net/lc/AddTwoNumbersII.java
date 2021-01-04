package net.lc;

public class AddTwoNumbersII {
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

    static class ListInfo {
        ListNode node;
        int carryOver;

        public ListInfo(ListNode node, int carryOver) {
            this.node = node;
            this.carryOver = carryOver;
        }
    }

    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        int s1 = getSize(l1);
        int s2 = getSize(l2);

        ListNode big = (s1 >= s2)? l1 : l2;
        ListNode small = (big == l1)? l2 : l1;

        int sizeDiff = Math.abs(s1-s2);

        ListInfo partialSum = addR(big, small, 0, sizeDiff);
        if (partialSum.carryOver == 0) return partialSum.node;

        ListNode head = new ListNode(partialSum.carryOver);
        head.next = partialSum.node;
        return head;
    }

    private ListInfo addR(ListNode big, ListNode small, int curdepth, int size) {
        if (curdepth < size) {

            ListInfo res = addR(big.next, small, curdepth+1, size);

            int sum = (res != null)? res.carryOver : 0;
            sum +=  big.val;
            int rem = sum % 10;
            int carryOver = sum / 10;

            ListNode resNode = new ListNode(rem);
            resNode.next = (res != null)? res.node : null;
            return new ListInfo(resNode, carryOver);
        } else {
            return addRecurse(big, small);
        }
    }

    private ListInfo addRecurse(ListNode big, ListNode small) {
        if (big == null) return null;

        ListInfo res = addRecurse(big.next, small.next);
        int sum = (res != null)? res.carryOver : 0;
        sum +=  big.val + small.val;
        int rem = sum % 10;
        int carryOver = sum / 10;

        ListNode resNode = new ListNode(rem);
        resNode.next = (res != null)? res.node : null;
        return new ListInfo(resNode, carryOver);
    }

    private int getSize(ListNode l) {
        int size = 0;
        while (l != null) {
            size++;
            l = l.next;
        }
        return size;
    }

    private static void print(ListNode l) {
        while (l != null) {
            System.out.print(l.val + " ");
            l = l.next;
        }
        System.out.println();
    }

    private static ListNode createList(int val) {
        ListNode cur = null;

        while (val > 0) {
            int rem = val % 10;
            ListNode newNode = new ListNode(rem);
            newNode.next = cur;
            cur = newNode;
            val = val / 10;
        }
        return cur;
    }

    public static void main(String[] args) {
        {
            ListNode n1 = createList(39987);
            ListNode n2 = createList(796);

            ListNode res = new AddTwoNumbersII().addTwoNumbers(n1, n2);
            print(res);
        }

        {
            ListNode n1 = createList(796);
            ListNode n2 = createList(9);

            ListNode res = new AddTwoNumbersII().addTwoNumbers(n1, n2);
            print(res);
        }
    }
}
