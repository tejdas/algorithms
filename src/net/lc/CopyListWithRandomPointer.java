package net.lc;

/**
 * 138
 * https://leetcode.com/problems/copy-list-with-random-pointer/submissions/
 */
class CopyListWithRandomPointer {
    static class Node {
        public Node(final int val) {
            this.val = val;
        }
        int val;
        Node next;
        Node random;
    }
    public Node copyRandomList(Node head) {
        Node clonedHead = null;

        Node cur = head;
        Node clonedPrev = null;
        /*
         * Iteration 1
         * Clone the nodes such that:
         * a. cloned-node's postPointer points to orig-node's postPointer.
         * b. orig-node's postPointer points to cloned-node.
         * c. Link all the cloned-nodes via next pointer(s).
         */
        while (cur != null) {
            final Node clonedCur = new Node(cur.val);
            clonedCur.random = cur.random;
            cur.random = clonedCur;
            if (clonedHead == null) {
                clonedHead = clonedCur;
            }

            if (clonedPrev != null) {
                clonedPrev.next = clonedCur;
            }
            clonedPrev = clonedCur;
            cur = cur.next;
        }

        /*
         * Iteration 2. Fix cloned list's post-pointers
         * a. orig-node's postPointer still points to corresponding cloned-node. (vertically down)
         * b. orig-node's next pointers still intact.
         * c. cloned-node's next-pointer points to previous value of cloned-node's postPointer (orig-node's postPointer)
         * d. cloned-node's post-pointer points to the correct post-pointer node.
         *
         */
        cur = head;
        while (cur != null) {
            final Node clonedCur = cur.random;
            final Node temp = clonedCur.random;
            clonedCur.next = temp;
            if (temp != null) {
                clonedCur.random = temp.random;
            }
            cur = cur.next;
        }

        /*
         * Iteration 3. Reset cloned-list's next-pointers and orig-list's post-pointers.
         * a. Get corresponding cloned-node (orig-node's post-pointer)
         * b. Set orig-node's post-pointer to cloned-node's next-pointer.
         * c. Fix cloned-node's next from behind the list, as the iteration moves forward.
         */
        cur = head;
        clonedPrev = null;
        while (cur != null) {
            final Node clonedCur = cur.random;
            cur.random = clonedCur.next;
            clonedCur.next = null;
            if (clonedPrev != null) {
                clonedPrev.next = clonedCur;
            }
            clonedPrev = clonedCur;
            cur = cur.next;
        }

        return clonedHead;
    }
}