package algo.linkedlist;

public class DoubleLinkedListSpiral {
    static class Node {
        public Node(final int val) {
            super();
            this.val = val;
            next = null;
            prev = null;
        }

        public int val;

        Node next;

        Node prev;
    }

    private Node head = null;

    private Node tail = null;

    public void addNode(final int val) {
        final Node node = new Node(val);
        if (tail == null) {
            head = node;
            tail = node;
        } else {
            tail.next = node;
            node.prev = tail;
            tail = node;
        }
    }

    public void traverse() {
        if (head != null) {
            Node cur = head;
            while (cur != null) {
                System.out.println(cur.val);
                cur = cur.next;
            }
        }
        System.out.println("---------------");
    }

    public void spiral() {
        Node ptr1 = head;
        Node ptr2 = tail;
        while (true) {
            if (ptr1.next == ptr2) {
                ptr2.next = null;
                break;
            }
            ptr2.next = ptr1.next;
            ptr1.next = ptr2;

            ptr1 = ptr2.next;
            ptr2 = ptr2.prev;

            if (ptr1 == ptr2) {
                ptr1.next = null;
                break;
            }
        }
    }

    public static void main(final String[] args) {
        final DoubleLinkedListSpiral dll = new DoubleLinkedListSpiral();
        dll.addNode(10);
        dll.addNode(20);
        dll.addNode(30);
        dll.addNode(40);
        dll.addNode(50);
        dll.addNode(60);
        dll.traverse();
        dll.spiral();
        dll.traverse();
    }
}
