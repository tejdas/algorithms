package aaa.bbb;

public class HeapSortAlgorithm {
    public static void main(String[] args) {
        HeapSorter heapSorter = new MinHeapSorter();

        //int[] input = {3, 5, 4, 1, 2};
        int[] input = {3, 5, 1, 10, 19, 7, 11, 9, 4, 12};

        heapSorter.buildHeap(input);

        while (true) {
            int val = heapSorter.remove();
            if (val == -1) {
                break;
            }

            System.out.println(val);
        }
    }
}
