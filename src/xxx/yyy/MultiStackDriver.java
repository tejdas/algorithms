package xxx.yyy;

import java.util.Random;

public class MultiStackDriver {
    private static final int MAX_CAPACITY = 8;

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
        private int count = 0;

        public void push(int data) {
            Node newNode = new Node(data);
            newNode.next = top;
            top = newNode;
            count++;
        }

        public boolean isEmpty() {
            return (top==null);
        }

        public boolean isFull() {
            return (count==MAX_CAPACITY);
        }

        public int pop() {
            int data = top.data;
            top = top.next;
            count--;
            return data;
        }

        public int getCount() {
            return count;
        }
        public int popAt(int index) {
            if (index >= count) {
                throw new IllegalArgumentException("Cannot popAt: " + index + " total count: " + count);
            }

            if (index == 0) {
                return pop();
            }
            Node iter = top;
            Node prev = null;
            for (int i = 0; i < index; i++) {
                prev = iter;
                iter = iter.next;
            }

            prev.next = iter.next;
            count--;
            return iter.data;
        }

        public int popLast() {
            return popAt(count-1);
        }

        public void printItems() {
            Node curVal = top;
            while (curVal != null) {
                System.out.println(curVal.data);
                curVal = curVal.next;
            }
        }

        private MyStack nextStack = null;
        private MyStack prevStack = null;
    }

    private static class MultiStack {
        public void push(int val) {
            if (top.isFull()) {
                MyStack newTop = new MyStack();
                newTop.nextStack = top;
                top.prevStack = newTop;
                top = newTop;
            }

            top.push(val);
        }

        public int pop() {
            int val = top.pop();
            if (top.isEmpty() && (top.nextStack != null)) {
                top = top.nextStack;
                top.prevStack = null;
            }
            return val;
        }

        public int popAt(int index) {
            MyStack stackContainingVal = top;
            while (index >= stackContainingVal.getCount()) {
                index -= stackContainingVal.getCount();
                stackContainingVal = stackContainingVal.nextStack;
                if (stackContainingVal == null) {
                    throw new IllegalArgumentException("Not enough items");
                }
            }

            int val = stackContainingVal.popAt(index);

            MyStack current = stackContainingVal;
            while (current.prevStack != null) {
                MyStack prev = current.prevStack;
                current.push(prev.popLast());
                if (prev.isEmpty()) {
                    current.prevStack = null;
                    top = current;
                    break;
                }
                current = prev;
            }
            return val;
        }

        public void printItems() {
            MyStack currentStack = top;
            while (currentStack != null) {
                currentStack.printItems();
                currentStack = currentStack.nextStack;
            }
        }
        private MyStack top = new MyStack();
    }

    public static void main(String[] args) {
        MultiStack ms = new MultiStack();
        int count = 18;
        Random r = new Random();
        for (int i = 0; i < count; i++) {
            int val = r.nextInt(100) + 1;
            System.out.println("pushing: " + val);
            ms.push(val);
        }

        ms.printItems();
        System.out.println("----------");
        System.out.println(ms.popAt(10));
        System.out.println(ms.popAt(12));
    }
}
