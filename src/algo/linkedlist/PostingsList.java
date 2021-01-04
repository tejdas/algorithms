package algo.linkedlist;

import java.util.Random;


public class PostingsList {

    static class RandomListNode {
        public RandomListNode(final int val) {
            this.label = val;
        }
        int label;
        RandomListNode next;
        RandomListNode random;

        public static RandomListNode deserialize(String s) {

            s = s.substring(1, s.length()-1);
            if (s.isEmpty()) {
                return null;
            }

            String[] array = s.split(",");
            if (array == null || array.length < 2) {
                return null;
            }

            RandomListNode head = new RandomListNode(Integer.parseInt(array[0]));
            RandomListNode prev = head;

            for (int index = 1; index < array.length-1; index++) {

                RandomListNode cur = new RandomListNode(Integer.parseInt(array[index]));
                prev.next = cur;
                prev = cur;
            }

            return head;
        }

        public static String serialize(RandomListNode head) {
            if (head == null) {
                return "{}";
            }

            StringBuilder sb = new StringBuilder();
            sb.append("{");

            RandomListNode cur = head;
            while (cur != null) {
                sb.append(cur.label);
                sb.append(",");
                cur = cur.next;
            }

            sb.append("#");
            sb.append("}");
            return sb.toString();
        }
    }

    static RandomListNode buildLinkedList(final int[] array) {
        RandomListNode cur = null;
        RandomListNode head = null;

        for (final int val : array) {
            final RandomListNode node = new RandomListNode(val);
            if (head == null) head = node;
            if (cur != null) cur.next = node;
            cur = node;
        }
        return head;
    }

    static RandomListNode getRandomNode(final RandomListNode head, final int count, final Random r) {
        final int index = r.nextInt(count);
        RandomListNode node = head;
        for (int i = 0; i < index; i++) {
            node = node.next;
        }
        return node;
    }
    static void buildPostingPointers(final RandomListNode head, final int count) {
        final Random r = new Random();
        RandomListNode cur = head;
        while (cur != null) {
            final RandomListNode random = getRandomNode(head, count, r);
            cur.random = random;
            cur = cur.next;
        }
    }

    static void display(final RandomListNode head) {
        RandomListNode cur = head;
        while (cur != null) {
            final String s = String.format("[%d %d]",  cur.label, cur.random.label);
            System.out.print(s + "  ");
            cur = cur.next;
        }
        System.out.println();
    }

    /**
     * Clone a PostingsList
     * @param head
     * @return
     */
    public RandomListNode copyRandomList(RandomListNode head) {
        RandomListNode clonedHead = null;

        RandomListNode cur = head;
        RandomListNode clonedPrev = null;
        /*
         * Iteration 1
         * Clone the nodes such that:
         * a. cloned-node's postPointer points to orig-node's postPointer.
         * b. orig-node's postPointer points to cloned-node.
         * c. Link all the cloned-nodes via next pointer(s).
         */
        while (cur != null) {
            final RandomListNode clonedCur = new RandomListNode(cur.label);
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
            final RandomListNode clonedCur = cur.random;
            final RandomListNode temp = clonedCur.random;
            clonedCur.next = temp;
            clonedCur.random = temp.random;
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
            final RandomListNode clonedCur = cur.random;
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

    public static void main0(final String[] args) {
        final int[] array = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
        final RandomListNode head = buildLinkedList(array);
        buildPostingPointers(head, array.length);
        display(head);

        final RandomListNode head1 = new PostingsList().copyRandomList(head);

        display(head);
        display(head1);
    }

    public static void main(final String[] args) {
        final int[] array = {1};
        final RandomListNode head = buildLinkedList(array);
        buildPostingPointers(head, array.length);
        display(head);

        final RandomListNode head1 = new PostingsList().copyRandomList(head);

        display(head);
        display(head1);
    }
}
