package xxx.yyy;

public class AscendDescendArrayPeak {

    static int findPeak(final int[] array, final int i, final int j) {
        if (i > j) {
            return -1;
        }

        if (i == j) {
            return array[i];
        }

        final int mid = (i+j)/2;

        if (mid == 0) {
            return array[mid];
        }

        if (mid == array.length-1) {
            return array[mid];
        }

        if (array[mid] > array[mid-1] && array[mid] > array[mid+1]) {
            return array[mid];
        } else if (array[mid] > array[mid-1] && array[mid] < array[mid+1]) {
            return findPeak(array, mid+1, j);
        } else if (array[mid] < array[mid-1] && array[mid] > array[mid+1]) {
            return findPeak(array, i, mid-1);
        }
        return -1;
    }
    public static void main(final String[] args) {
        final int[] array = {2, 5, 8, 10, 12, 16, 18, 14, 10, 6};
        final int peak = findPeak(array, 0, array.length-1);
        System.out.println(peak);
    }
}
