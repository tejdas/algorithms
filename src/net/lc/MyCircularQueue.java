package net.lc;

/**
 * 622
 * Queue
 */
public class MyCircularQueue {
    private int size;
    private int curSize = 0;
    private int[] array;
    private int lastEnqueuedIndex = -1;
    private int nextDequeueIndex = -1;
    public MyCircularQueue(int k) {
        size = k;
        array = new int[size];
        lastEnqueuedIndex = -1;
        nextDequeueIndex = 0;
    }

    public boolean enQueue(int value) {
        if (curSize == size) return false;
        lastEnqueuedIndex = (lastEnqueuedIndex + 1) % size;
        array[lastEnqueuedIndex] = value;
        curSize++;
        return true;
    }

    public boolean deQueue() {
        if (curSize == 0) return false;
        curSize--;
        nextDequeueIndex = (nextDequeueIndex + 1) % size;
        return true;
    }

    public int Front() {
        if (curSize == 0) return -1;
        return array[nextDequeueIndex];
    }

    public int Rear() {
        if (curSize == 0) return -1;
        return array[lastEnqueuedIndex];
    }

    public boolean isEmpty() {
        return (curSize == 0);
    }

    public boolean isFull() {
        return (curSize == size);
    }

    public static void main(String[] args) {
        MyCircularQueue obj = new MyCircularQueue(3);
        System.out.println(obj.enQueue(1));
        System.out.println(obj.enQueue(2));
        System.out.println(obj.enQueue(3));
        System.out.println(obj.enQueue(4));

        System.out.println(obj.Rear());
        System.out.println(obj.isFull());
        System.out.println(obj.deQueue());
        System.out.println(obj.enQueue(4));
        System.out.println(obj.Rear());
    }
}
