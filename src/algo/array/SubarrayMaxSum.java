package algo.array;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class SubarrayMaxSum {
    public static void main(String[] args) throws IOException {

        BufferedReader bfr = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(bfr.readLine().trim());

        List<Integer> subArraySum = new ArrayList<>();
        List<Integer> subseq = new ArrayList<>();

        for (int i = 0; i < T; i++) {

            int N = Integer.parseInt(bfr.readLine().trim());
            int[] array = new int[N];
            String[] temp = bfr.readLine().split(" ");
            int index = 0;
            for (String s : temp) {
                array[index++] = Integer.parseInt(s);
            }

            int maxval = getMaxVal(array);

            if (maxval < 0) {
                subArraySum.add(maxval);
                subseq.add(maxval);
            } else {
                int subarraysum = findSubArrayMaxSum(array);
                int subseqsum = findSubseqSum(array);

                subArraySum.add(subarraysum);
                subseq.add(subseqsum);
            }
        }

        for (int i = 0; i < T; i++) {
            System.out.println(subArraySum.get(i) + " " + subseq.get(i));
        }
    }

    static int getMaxVal(int[] array) {
        int maxVal = Integer.MIN_VALUE;

        for (int val : array) {
            if (val > maxVal) {
                maxVal = val;
            }
        }

        if (maxVal < 0) {
            return maxVal;
        }

        return Integer.MAX_VALUE;
    }

    static int findSubseqSum(int[] array) {
        int maxVal = Integer.MIN_VALUE;

        for (int val : array) {
            if (val > maxVal) {
                maxVal = val;
            }
        }

        if (maxVal < 0) {
            return maxVal;
        }

        int sum = 0;
        for (int val : array) {
            if (val > 0) {
                sum += val;
            }
        }
        return sum;
    }

    static int findSubArrayMaxSum(int[] array) {
        int sum = 0;
        int maxSum = 0;
        for (int index = 0; index < array.length; index++) {
            if (sum == 0) {
                if (array[index] > 0) {
                    sum = array[index];
                    if (sum > maxSum) {
                        maxSum = sum;
                    }
                }
            } else {
                sum += array[index];
                if (sum > maxSum) {
                    maxSum = sum;
                } else if (sum <= 0) {
                    sum = 0;
                }
            }
        }
        return maxSum;
    }
}
