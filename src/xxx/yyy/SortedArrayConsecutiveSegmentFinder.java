package xxx.yyy;

public class SortedArrayConsecutiveSegmentFinder {


    public static void main(final String[] args) {
        findConsecutiveSegments(new int[] {1, 3, 4, 5, 8, 9, 10, 11, 15, 16, 19});
    }

    static void findConsecutiveSegments(final int[] array) {
        if (array.length == 0) {
            return;
        }

        int startSegment = array[0];
        int endSegment = array[0];

        for (int i = 1; i < array.length; i++) {
            if (array[i] == array[i-1] + 1) {
                endSegment = array[i];
            } else {
                System.out.println(startSegment + "  " + endSegment);
                startSegment = array[i];
                endSegment = array[i];
            }
        }

        System.out.println(startSegment + "  " + endSegment);
    }
}
