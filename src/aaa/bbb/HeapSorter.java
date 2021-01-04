package aaa.bbb;

public interface HeapSorter {

    /**
     * Initialize the Heap. Build it from the given input.
     * @param input
     */
    void buildHeap(int[] input);

    /**
     * Peek the top element of the Heap.
     * @return
     */
    int peek();

    /**
     * Remove and return the top element of the Heap.
     * @return
     */
    int remove();

    /**
     * Add an element to the Heap.
     * @param val
     */
    void add(int val);
}
