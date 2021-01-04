package xxx.yyy;

import java.util.Random;

public class MinHeapSorter {
    private int heapSize = 0;
    private int[] heap = null;

    public MinHeapSorter() {
    }

    public void build(final int[] input) {
        heapSize = input.length;
        heap = new int[heapSize + 1];
        heap[0] = -1;
        System.arraycopy(input, 0, heap, 1, input.length);

        for (int pos = heap.length/2; pos >=1; pos--) {
            heapify(pos);
        }
    }

    public int removeAndGetSmallest() {
        final int smallest = heap[1];
        heap[1] = heap[heapSize-1];
        heapSize--;
        heapify(1);
        return smallest;
    }

    private void heapify(int pos) {
        while (pos < heapSize) {
            final int val = heap[pos];
            final int valLChild = (pos*2 < heapSize)? heap[pos*2] : Integer.MAX_VALUE;
            final int valRChild = (pos*2 + 1 < heapSize)? heap[pos*2 + 1] : Integer.MAX_VALUE;
            if (val > valLChild || val > valRChild) {
                if (valLChild < valRChild) {
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
    public static void main(final String[] args) {
        final Random r = new Random();
        final int len = 15;
        final int[] array = Util.generateRandomNumbers(r, len);
        final MinHeapSorter heap = new MinHeapSorter();
        heap.build(array);
        Util.printArray(heap.heap, 1, heap.heapSize);
        for (int i = 0; i < len; i++) {
            System.out.println(heap.removeAndGetSmallest());
        }
        //Util.printArray(heap.heap, 1, heap.heapSize);
    }
}

