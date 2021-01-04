package xxx.yyy;

public class StackWithMin {
    private static class Node {
        public Node(int data) {
            super();
            this.data = data;
            this.next = null;
        }
        int data;
        public Node next = null;
    }

    private static class MyStack {
        private Node top = null;

        private void push(int data) {
            Node newNode = new Node(data);
            newNode.next = top;
            top = newNode;
        }

        private boolean isEmpty() {
            return (top==null);
        }

        private int pop() {
            int data = top.data;
            top = top.next;
            return data;
        }

        private int peek() {
            return top.data;
        }
    }

    private static class MyStackWithMin {
        private final MyStack entries = new MyStack();
        private final MyStack minVals = new MyStack();

        private void push(int data) {
            entries.push(data);
            if (minVals.isEmpty() || (data <= minVals.peek())) {
                minVals.push(data);
            }
        }

        private boolean isEmpty() {
            return entries.isEmpty();
        }

        private int pop() {
            if (isEmpty()) {
                throw new IllegalStateException();
            }

            int data = entries.pop();
            if (data == minVals.peek()) {
                minVals.pop();
            }
            return data;
        }

        private int minVal() {
            return minVals.peek();
        }
    }

    public static void main(String[] args) {
        int[] vals = {10, 8, 2, 9 , 15, 1};
        //int[] vals = {2, 2, 2, 2, 1, 1, 1};

        MyStackWithMin stack = new MyStackWithMin();
        for (int val : vals) {
            stack.push(val);
        }

        while (!stack.isEmpty()) {
            System.out.println("minval: " + stack.minVal());
            stack.pop();
        }
    }
}
