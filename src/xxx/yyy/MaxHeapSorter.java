package xxx.yyy;

import java.util.Random;

public class MaxHeapSorter {
    private int heapSize = 0;
    private int[] heap;

    public MaxHeapSorter() {
    }

    public void build(int[] input) {
        heap = new int[input.length+1];
        heapSize = heap.length;
        heap[0] = -1;
        System.arraycopy(input, 0, heap, 1, input.length);

        for (int pos = heap.length/2; pos >=1; pos--) {
            heapify(pos);
        }
    }

    public int removeAndGetLargest() {
        final int largest = heap[1];
        heap[1] = heap[heapSize-1];
        heapSize--;
        heapify(1);
        return largest;
    }

    private void heapify(int pos) {
        while (pos < heapSize) {
            final int val = heap[pos];
            final int valLChild = (pos*2 < heapSize)? heap[pos*2] : Integer.MIN_VALUE;
            final int valRChild = (pos*2 + 1 < heapSize)? heap[pos*2 + 1] : Integer.MIN_VALUE;
            if (val < valLChild || val < valRChild) {
                if (valLChild > valRChild) {
                    heap[pos] =  valLChild;
                    heap[pos*2] = val;
                    pos = pos*2;
                } else {
                    heap[pos] =  valRChild;
                    heap[pos*2 + 1] = val;
                    pos = pos*2 + 1;
                }
            } else {
                break;
            }
        }
    }
    public static void main(String[] args) {
        final Random r = new Random();
        final int len = 15;
        final int[] array = // {2,  175,  4,  37,  52,  160,  72,  148,  180,  164};
                Util.generateRandomNumbers(r, len);
        final MaxHeapSorter heap = new MaxHeapSorter();
        heap.build(array);
        Util.printArray(heap.heap);
        for (int i = 0; i < len; i++) {
            System.out.println(heap.removeAndGetLargest());
        }
        Util.printArray(heap.heap);
    }
}
