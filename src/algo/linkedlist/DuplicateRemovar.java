package algo.linkedlist;

import java.util.Random;

public class DuplicateRemovar {
    private static class Node {
        public Node(int data) {
            super();
            this.data = data;
            this.next = null;
        }
        int data;
        public Node next = null;
    }

    private static Node createList(int count) {
        Random r = new Random();
        Node head = null;
        Node curNode = null;
        for (int i = 0; i < count; i++) {
            Node newNode = new Node(r.nextInt(100));
            if (curNode == null) {
                head = newNode;
                curNode = newNode;
            } else {
                curNode.next = newNode;
                curNode = newNode;
            }
        }
        return head;
    }

    private static void printList(Node head) {
        int count = 0;
        while (head != null) {
            System.out.print(" " + head.data);
            count++;
            head = head.next;
        }
        System.out.println();
        System.out.println("Item count: " + count);
    }

    /**
     * Iterate the list. For each item, scan the remainder
     * and remove duplicates.
     * @param head
     */
    private static void removeDuplicates(Node head) {
        while (head.next != null) {
            removeDuplicate(head);
            head = head.next;
        }
    }

    private static void removeDuplicate(Node current) {
        Node scanCur = null;
        Node scanPrev = current;
        while (scanPrev != null && scanPrev.next != null) {
            scanCur = scanPrev.next;
            if (scanCur.data == current.data) {
                System.out.println("Removed : " + scanCur.data);
                scanPrev.next = scanCur.next;
            }
            scanPrev = scanPrev.next;
        }
    }

    public static void main(String[] args) {
        Node head = createList(100);
        printList(head);
        System.out.println("----------------");
        removeDuplicates(head);
        printList(head);
    }
}
