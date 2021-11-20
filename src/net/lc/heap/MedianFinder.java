package net.lc.heap;

import java.util.Collections;
import java.util.PriorityQueue;

/**
 * 295
 * Heap
 * PriorityQueue
 */
public class MedianFinder {
    /**
     * Stores top-half of numbers, with smallest at the top
     */
    private final PriorityQueue<Integer> maxHeap = new PriorityQueue<>();
    /**
     * Stores bottom-half of numbers, with largest at the top
     */
    private final PriorityQueue<Integer> minHeap = new PriorityQueue<>(Collections.reverseOrder());

    /**
     * Size(maxHeap) - Size(minHeap) <= 1 and >= 0
     */

    public MedianFinder() {
    }

    public void addNum(int num) {
        if (maxHeap.isEmpty() && minHeap.isEmpty()) {
            maxHeap.add(num);
        } else {
            if (num > maxHeap.peek()) {
                maxHeap.add(num);
            } else {
                minHeap.add(num);
            }

            if (minHeap.size() > maxHeap.size()) {
                maxHeap.add(minHeap.remove());
            } else if (maxHeap.size() - minHeap.size() > 1) {
                minHeap.add(maxHeap.remove());
            }
        }
    }

    public double findMedian() {
        if (minHeap.isEmpty() && maxHeap.isEmpty()) return 0;

        if (minHeap.size() == maxHeap.size()) {
            return 0.5 * ( minHeap.peek() + maxHeap.peek());
        }

        return (double) maxHeap.peek();
    }

    public static void main(String[] args) {
        MedianFinder mf = new MedianFinder();
        for (int i = 0; i < 10; i++) {
            mf.addNum(i);
        }

        System.out.println("Max heap contents");
        while (!mf.maxHeap.isEmpty()) {
            System.out.print(mf.maxHeap.remove().intValue() +  " ");
        }
        System.out.println();

        System.out.println("Min heap contents");
        while (!mf.minHeap.isEmpty()) {
            System.out.print(mf.minHeap.remove().intValue() +  " ");
        }
        System.out.println();
    }
}
