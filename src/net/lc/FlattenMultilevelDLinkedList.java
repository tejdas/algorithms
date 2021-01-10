package net.lc;

/**
 * 430
 * LinkedList
 */
public class FlattenMultilevelDLinkedList {
    static class Node {
        public int val;
        public Node prev;
        public Node next;
        public Node child;
    }

    public Node flatten(Node head) {
        if (head == null) return null;
        flattenRecurse(head);
        return head;
    }

    private Node flattenRecurse(Node head) {
        //System.out.println("flattenRecurse: " + head.val);
        Node cur = head;
        while (cur.next != null) {
            Node nextNode = cur.next;
            if (cur.child != null) {
                Node lastChild = flattenRecurse(cur.child);
                cur.next = cur.child;
                cur.child.prev = cur;
                cur.child = null;
                lastChild.next = nextNode;
                nextNode.prev = lastChild;
            }
            cur = nextNode;
        }

        if (cur.child != null) {
            flattenRecurse(cur.child);
            cur.next = cur.child;
            cur.child.prev = cur;
            cur.child = null;
        }
        return cur;
    }
}
