package xxx.yyy;

import java.util.Random;

public class QueueUsingStack {
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
    }

    private static class MyQueue {
        public void enqueue(int val) {
            while (!main.isEmpty()) {
                reserved.push(main.pop());
            }
            main.push(val);
            while (!reserved.isEmpty()) {
                main.push(reserved.pop());
            }
        }

        public boolean isEmpty() {
            return main.isEmpty();
        }
        public int dequeue() {
            if (main.isEmpty()) {
                System.out.println("No elemeents");
                return 0;
            }
            return main.pop();
        }

        private final MyStack main = new MyStack();
        private final MyStack reserved = new MyStack();
    }


    public static void main(String[] args) {
        int count = 10;
        MyQueue q = new MyQueue();
        Random r = new Random();
        for (int i = 0; i < count; i++) {
            int val = r.nextInt(100) + 1;
            System.out.println("enqueuing: " + val);
            q.enqueue(val);
        }

        while (!q.isEmpty()) {
            System.out.println(q.dequeue());
        }
    }

}
