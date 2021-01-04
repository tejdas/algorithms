package aaa.bbb;

public class MinHeapSorter implements HeapSorter {
    private int[] heapArray;
    private int heapMaxIndex = -1;

    @Override
    public void buildHeap(int[] input) {
        if (input.length == 0) {
            return;
        }

        heapArray = new int[input.length+1];
        System.arraycopy(input, 0, heapArray, 1, input.length);
        heapMaxIndex = input.length;

        for (int i = heapMaxIndex/2; i >= 1; i--) {
            heapify(i);
        }
    }

    @Override
    public int peek() {
        return heapArray[1];
    }

    @Override
    public int remove() {
        if (heapMaxIndex == 0) {
            return -1;
        }

        int val = heapArray[1];

        int lastElem = heapArray[heapMaxIndex];
        heapMaxIndex--;
        heapArray[1] = lastElem;
        heapify(1);
        return val;
    }

    @Override
    public void add(int val) {
    }

    private void heapify1(int curIndex) {
        while (curIndex < heapMaxIndex) {
            int largerChildIndex = getBiggerChildIndex(curIndex);
            if (largerChildIndex != -1) {
                swap(curIndex, largerChildIndex);
                curIndex = largerChildIndex;
            } else {
                break;
            }
        }
    }

    private void heapify(int curIndex) {
        if (curIndex > heapMaxIndex) {
            return;
        }
        int largerChildIndex = getBiggerChildIndex(curIndex);

        if (largerChildIndex == -1) {
            return;
        }

        swap(curIndex, largerChildIndex);
        heapify(largerChildIndex);
    }

    private void swap(int index1, int index2) {
        int temp = heapArray[index1];
        heapArray[index1] = heapArray[index2];
        heapArray[index2] = temp;
    }

    private int getBiggerChildIndex(int curIndex) {
        if (curIndex * 2 > heapMaxIndex) {
            // no child
            return -1;
        }

        if (curIndex*2 + 1 > heapMaxIndex) {
            // only left
            if (heapArray[curIndex*2] > heapArray[curIndex]) {
                return curIndex*2;
            } else {
                return -1;
            }
        }

        // both left and right child exist
        int bigChildIndex = heapArray[curIndex*2] > heapArray[curIndex*2+1]? curIndex*2 : curIndex*2 + 1;

        if (heapArray[bigChildIndex] > heapArray[curIndex]) {
            return bigChildIndex;
        }

        return -1;
    }
}
