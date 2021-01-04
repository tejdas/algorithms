package xxx.yyy;

import java.util.Arrays;

/**
 * A Circular FIFO queue implementation using Array, that also grows by a size of 16, when the FIFO queue size reaches the Array length.
 */
public class ResizeableCircularFIFOQueue {

    private int size = 8;
    private int curSize = 0;
    private int head = 0;
    private int tail = -1;

    private int[] array = new int[size];

    public void enqueue(final int val) {
        if (curSize < array.length) {
            tail = (tail+1) % size;
            array[tail] = val;
            curSize++;
        } else {
            size += 16;
            final int[] newArray = new int[size];

            if (head < tail) {
                System.arraycopy(array, head, newArray, 0, curSize);
            } else {
                System.arraycopy(array, head, newArray, 0, curSize-head);
                System.arraycopy(array, 0, newArray, curSize-head, head);
            }

            head = 0;
            tail = curSize-1;
            array = newArray;
            array[++tail] = val;
            curSize++;
        }
    }

    public int dequeue() {
        if (curSize == 0) {
            throw new IllegalStateException("Empty CircularQueue");
        }

        final int val = array[head];
        array[head] = 0;
        head = (head+1) % size;
        curSize--;
        return val;
    }

    public void dump() {
        System.out.println(Arrays.toString(array));
    }
    public static void main(final String[] args) {
        final ResizeableCircularFIFOQueue queue = new ResizeableCircularFIFOQueue();
        for (int i = 0; i < 8; i++) {
            queue.enqueue(i);
        }

        queue.dump();

        for (int i = 0; i < 3; i++) {
            System.out.println(queue.dequeue());
        }

        for (int i = 8; i < 20; i++) {
            queue.enqueue(i);
        }

        queue.dump();

        while (true) {
            final int val = queue.dequeue();
            if (val == -1) {
                break;
            }
            System.out.println(val);
        }

        queue.dump();
    }
}
