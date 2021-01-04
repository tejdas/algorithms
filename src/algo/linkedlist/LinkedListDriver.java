package algo.linkedlist;

public class LinkedListDriver {
    static class Node {
        public Node(int val) {
            this.val = val;
        }
        int val;
        Node next;
    }

    static Node buildLinkedList(int[] array) {
        Node cur = null;
        Node head = null;

        for (int val : array) {
            Node node = new Node(val);
            if (head == null) head = node;
            if (cur != null) cur.next = node;
            cur = node;
        }
        return head;
    }

    static void iterate(Node head) {
        while (head != null) {
            System.out.print(head.val + " ");
            head = head.next;
        }
        System.out.println();
    }

    static Node removeNode(Node head, int val) {
        Node cur = head;
        Node prev = head;
        while (cur != null) {
            if (cur.val == val) {
                if (cur == head) {
                    head = cur.next;
                    prev = cur.next;
                } else {
                    prev.next = cur.next;
                }
            } else {
                prev = cur;
            }
            cur = cur.next;
        }
        return head;
    }

    static Node swapPairs(Node head) {
        boolean headChanged = false;
        Node prev = null;
        Node first = head;
        Node second = first.next;
        while (second != null) {
            first.next = second.next;
            second.next = first;
            if (!headChanged) {
                head = second;
                headChanged = true;
            }
            if (prev != null) prev.next = second;
            prev = first;
            first = first.next;
            if (first == null) break;
            second = first.next;
        }
        return head;
    }

    static Node mergeTwoLinkedLists(Node head1, Node head2) {
        Node cur1 = head1;
        Node cur2 = head2;
        Node next1 = null;
        Node next2 = null;
        while (cur1!= null && cur2!=null) {
            next1 = cur1.next;
            next2 = cur2.next;

            cur1.next = cur2;
            if (next1 != null)
                cur2.next = next1;
            cur1 = next1;
            cur2 = next2;
        }
        return head1;
    }

    static int getSize(Node head) {
        int size = 0;
        Node temp = head;
        while (temp != null) {
            temp = temp.next;
            size++;
        }
        return size;
    }

    static Node getPrevOfKthNodeFromEnd(Node head, int k) {
        int size = getSize(head);
        if (k >= size-1) return null;
        if (k == size-2) return head;

        Node n1 = head;
        Node n2 = head;
        for (int i = 0; i < k+1; i++) {
            n2 = n2.next;
        }

        while (n2.next != null) {
            n1 = n1.next;
            n2 = n2.next;
        }
        return n1;
    }

    static Node getPrevOfKthNodeFromBegin(Node head, int k) {
        int size = getSize(head);
        if (k == 0) return null;
        if (k >= size) return null;
        if (k == 1) return head;

        Node n2 = head;
        for (int i = 0; i < k-1; i++) {
            n2 = n2.next;
        }
        return n2;
    }
    static Node swapNodes(Node head, int index) {
        if (index == 0) {
            Node prevFromEnd = getPrevOfKthNodeFromEnd(head, 0);
            Node curFromEnd = prevFromEnd.next;
            prevFromEnd.next = head;
            curFromEnd.next = head.next;
            head.next = null;
            return curFromEnd;
        }
        Node prevFromBegin = getPrevOfKthNodeFromBegin(head, index);
        Node prevFromEnd = getPrevOfKthNodeFromEnd(head, index);

        Node curFromBegin = prevFromBegin.next;
        Node curFromEnd = prevFromEnd.next;

        prevFromBegin.next = curFromEnd;
        prevFromEnd.next = curFromBegin;

        Node temp = curFromBegin.next;
        curFromBegin.next = curFromEnd.next;
        curFromEnd.next = temp;
        return head;
    }

    public static void main1(String[] args) {
        Node head2 = buildLinkedList(new int[] {1, 2, 4, 2, 3, 5, 5, 7});
        Node head1 = buildLinkedList(new int[] {1, 1, 4, 2, 3, 5, 5, 7});
        Node head = buildLinkedList(new int[] {2, 2, 2});
        iterate(head);
        head = removeNode(head, 2);
        iterate(head);
    }
    public static void main2(String[] args) {
        Node head = buildLinkedList(new int[] {1, 2, 3, 4, 5, 6, 7, 8, 9});
        iterate(head);
        head = swapPairs(head);
        iterate(head);

        Node head1 = buildLinkedList(new int[] {1, 2, 3, 4, 10, 11, 12});
        Node head2 = buildLinkedList(new int[] {5, 6, 7, 8});
        head1 = mergeTwoLinkedLists(head1, head2);
        iterate(head1);
    }

    public static void main(String[] args) {
        Node head = buildLinkedList(new int[] {1, 2, 3, 4, 5, 6, 7, 8, 9});
        Node n = getPrevOfKthNodeFromEnd(head, 7);
        System.out.println(n.val);

        Node m = getPrevOfKthNodeFromBegin(head, 4);
        System.out.println(m.val);

        head = swapNodes(head, 6);
        iterate(head);
    }
}
