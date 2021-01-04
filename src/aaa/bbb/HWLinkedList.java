package aaa.bbb;

public class HWLinkedList {

    static class Node {
        int val;
        Node next;

        public Node(int val) {
            this.val = val;
        }
    }

    private Node head;
    private Node tail;

    public void printList() {
    }

    public void addNodeAfter(int sourceVal, int newVal) {

    }

    public void addNodeAtTail(int newVal) {
        Node newNode = new Node(newVal);

        if (tail == null) {
            head = newNode;
            tail = newNode;
        } else {
            tail.next = newNode;
            tail = newNode;
        }
    }

    public void addNodeAtHead(int newVal) {
    }

    public static void main(String[] args) {
        HWLinkedList hll = new HWLinkedList();
        hll.addNodeAtTail(45);
        hll.addNodeAtTail(23);
        hll.addNodeAtTail(52);
        hll.printList();
    }
}

