package net.lc.concurrency;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * https://leetcode.com/problems/design-bounded-blocking-queue/
 * MT
 * 1188
 */
public class BoundedBlockingQueue {
    private final Queue<Integer> queue = new LinkedList<>();
    private int queueSize = 0;
    private final int maxCapacity;
    private final ReentrantLock lock = new ReentrantLock();
    private final Condition notEmptyCondition = lock.newCondition();
    private final Condition notFullCondition = lock.newCondition();

    public BoundedBlockingQueue(int capacity) {
        this.maxCapacity = capacity;
    }

    public void enqueue(int element) throws InterruptedException {
        lock.lock();
        try {
            while (queueSize >= maxCapacity) {
                notFullCondition.await();
            }
            queue.add(element);
            final boolean wasEmpty = (queueSize == 0);
            queueSize++;
            if (wasEmpty) {
                notEmptyCondition.signal();
            }
        } finally {
            lock.unlock();
        }

    }

    public int dequeue() throws InterruptedException {
        lock.lock();
        try {
            while (queueSize == 0) {
                notEmptyCondition.await();
                if (queueSize == 0) {
                    return -1;
                }
            }
            final boolean wasFull = (queueSize == maxCapacity);
            queueSize--;
            final int s = queue.remove();
            if (wasFull) {
                notFullCondition.signal();
            }
            return s;
        } finally {
            lock.unlock();
        }
    }

    public int size() {
        lock.lock();
        try {
            return queueSize;
        } finally {
            lock.unlock();
        }
    }
}