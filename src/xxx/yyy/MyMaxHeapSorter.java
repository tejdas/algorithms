package xxx.yyy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MyMaxHeapSorter {

    private int arrayLen;

    public static void main(String[] args) {
        final MyMaxHeapSorter hs = new MyMaxHeapSorter();
        final int[] array = { 6, 3, 14, 21, 15, 10, 1, 18 };
        final int[] resultArray = hs.sortArray(array);
        System.out.println(Arrays.toString(resultArray));
    }

    private void swap(int[] arr, int a, int b) {
        final int temp = arr[a];
        arr[a] = arr[b];
        arr[b] = temp;
    }

    private int getBiggerChildIndex(int[] array, int parent) {

        final int left = 2 * parent;
        final int right = 2 * parent + 1;

        if (right >= arrayLen) {
            return left;
        }

        if (array[right] > array[left]) {
            return right;
        } else {
            return left;
        }
    }

    private void maxheap(int[] array, int i) {

        while (i < arrayLen) {
            final int biggerChild = getBiggerChildIndex(array, i);
            if (biggerChild < arrayLen) {
                if (array[biggerChild] > array[i]) {
                    swap(array, biggerChild, i);
                    i = biggerChild;
                } else {
                    return;
                }
            } else {
                return;
            }
        }
    }

    private void heapify(int[] array) {
        for (int i = (arrayLen) / 2; i > 0; i--) {
            maxheap(array, i);
        }
    }

    public int[] sortArray(int[] array) {

        if (array == null || array.length == 0) {
            return null;
        }

        final int[] heapArray = new int[array.length + 1];
        System.arraycopy(array, 0, heapArray, 1, array.length);
        final List<Integer> resultArray2 = new ArrayList<>();
        arrayLen = heapArray.length;
        heapify(heapArray);

        final int largestVal = heapArray[1];
        resultArray2.add(largestVal);
        while (resultArray2.size() < array.length) {
            swap(heapArray, arrayLen - 1, 1);
            arrayLen--;
            maxheap(heapArray, 1);
            resultArray2.add(heapArray[1]);
        }

        final int[] resultArray = new int[resultArray2.size()];
        for (int i = 0; i < resultArray2.size(); i++) {
            resultArray[i] = resultArray2.get(i);
        }

        return resultArray;
    }
}
