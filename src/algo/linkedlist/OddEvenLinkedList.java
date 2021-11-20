package algo.linkedlist;

/**
 * 328
 */
public class OddEvenLinkedList {
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

    public ListNode oddEvenList(ListNode head) {
        if (head == null) return null;
        ListNode headOdd = head;
        ListNode headEven = head.next;

        ListNode prevO = headOdd;
        ListNode prevE = headEven;

        ListNode lastO = prevO;

        while (prevO != null) {

            if (prevE == null) {
                lastO = prevO;
                break;
            }
            ListNode curO = prevE.next;
            ListNode curE = curO != null? curO.next: null;

            prevO.next = curO;
            prevE.next = curE;

            lastO = prevO;

            prevO = curO;
            prevE = curE;
        }

        lastO.next = headEven;

        return headOdd;
    }

    private static void printList(ListNode cur) {
        while (cur != null) {
            System.out.println(cur.val);
            cur = cur.next;
        }
        System.out.println("------------");
    }
    public static void main(String[] args) {
        {
            ListNode n1 = new ListNode(1);
            ListNode n2 = new ListNode(2);
            ListNode n3 = new ListNode(3);
            ListNode n4 = new ListNode(4);
            ListNode n5 = new ListNode(5);

            n1.next = n2;
            n2.next = n3;
            n3.next = n4;
            n4.next = n5;

            ListNode h = new OddEvenLinkedList().oddEvenList(n1);
            printList(h);
        }
        {
            ListNode n1 = new ListNode(1);
            ListNode n2 = new ListNode(2);
            ListNode n3 = new ListNode(3);
            ListNode n4 = new ListNode(4);

            n1.next = n2;
            n2.next = n3;
            n3.next = n4;

            ListNode h = new OddEvenLinkedList().oddEvenList(n1);
            printList(h);
        }

        {
            ListNode n1 = new ListNode(1);
            ListNode n2 = new ListNode(2);

            n1.next = n2;

            ListNode h = new OddEvenLinkedList().oddEvenList(n1);
            printList(h);
        }

        {
            ListNode n1 = new ListNode(1);

            ListNode h = new OddEvenLinkedList().oddEvenList(n1);
            printList(h);
        }
    }
}
