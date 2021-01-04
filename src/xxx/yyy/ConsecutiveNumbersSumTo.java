package xxx.yyy;


public class ConsecutiveNumbersSumTo {

    public static void main(final String[] args) {
        findConsecutiveNumbersSumTo(new int[] {1, 2, 3, 5, 7, 9, 10}, 24);

        //findConsecutiveNumbersSumTo(new int[] {115, 141, 66, 76, 144, 111, 46, 148, 165, 60}, 376);


        //final int[] array = Util.generateRandomNumbers(new Random(),  10);
        //System.out.println(Arrays.toString(array));
    }

    static void findConsecutiveNumbersSumTo(final int[] array, final int val) {
        int begin = 0;
        int end = -1;

        int sum = 0; // array[begin];

        while (begin < array.length && end < array.length) {
            if (sum == val) {
                System.out.println("begin: " + begin + " end: " + end);
                return;
            } else if (sum < val) {
                end++;
                if (end >= array.length) {
                    return;
                }
                sum += array[end];
            } else if (sum > val) {
                sum -= array[begin];
                begin++;
            }
        }
    }
}
