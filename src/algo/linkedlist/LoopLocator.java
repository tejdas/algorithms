package algo.linkedlist;

import java.util.Random;


public class LoopLocator {
    private static class Node {
        public Node(final int data) {
            super();
            this.data = data;
            this.next = null;
        }
        int data;
        public Node next = null;
    }

    private static Node createList(final int count, final int loopIndex) {
        final Random r = new Random();
        Node head = null;
        Node loopStartNode = null;
        Node curNode = null;
        Node newNode = null;
        for (int i = 0; i < count; i++) {
            newNode = new Node(r.nextInt(100));
            if (i == loopIndex) {
                loopStartNode = newNode;
            }
            if (curNode == null) {
                head = newNode;
                curNode = newNode;
            } else {
                curNode.next = newNode;
                curNode = newNode;
            }
        }
        newNode.next = loopStartNode;
        return head;
    }

    private static void printList(Node head, final int itemCount) {
        int count = 0;
        while (head != null) {
            System.out.println(head.data);
            count++;
            if (count == itemCount) {
                return;
            }
            head = head.next;
        }
    }

    private static int loopSize(final Node loopStart) {
        int count = 0;
        Node cur = loopStart;
        do {
            count++;
            cur = cur.next;
        } while (cur != loopStart);
        return count;
    }

    private static int getLoopLength(final Node head) {
        Node first = head;
        Node second = head;
        while (first != null && second != null) {
            first = first.next;
            if (second.next == null) {
                break;
            }
            second = second.next.next;
            if (first!=null && first==second) {
                System.out.println("loop");
                return loopSize(first);
            }
        }
        System.out.println("no loop");
        return 0;
    }

    private static Node findLoopStart(final Node head, final int loopLen) {
        Node first = head;
        Node second = head;
        for (int i = 0; i < loopLen; i++) {
            second = second.next;
        }
        while (first != second) {
            first = first.next;
            second = second.next;
        }
        return first;
    }

    /**
     * Locate join point of two Linked Lists that are conjoined
     * to form a Y shape.
     * @param headA
     * @param headB
     * @return
     */
    static Node locateJoinPoint(final Node headA, final Node headB) {
    	if (headA == headB)
    		return headA;

    	/*
    	 * Find tails
    	 */
    	Node tailA = headA;
    	while (tailA.next != null)
    		tailA = tailA.next;

    	Node tailB = headB;
    	while (tailB.next != null)
    		tailB = tailB.next;

    	if (tailA != tailB)
    		return null;

    	final Node commonTail = tailA;
    	/*
    	 * Create loop
    	 */
    	tailA.next = headB;
    	final int loopSize = loopSize(headB);
    	final Node loopJoinPoint = findLoopStart(headA, loopSize);

    	/*
    	 * Restore
    	 */
    	commonTail.next = null;
    	return loopJoinPoint;
    }

    public static void main(final String[] args) {
        final Node head = createList(10, 4);
        printList(head, 15);
        final int len = getLoopLength(head);
        if (len > 0) {
            final Node loopHead = findLoopStart(head, len);
            System.out.println("Loop Head: " + loopHead.data);
        }
    }
}
