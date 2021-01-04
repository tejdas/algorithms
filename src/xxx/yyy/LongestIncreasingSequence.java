package xxx.yyy;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LongestIncreasingSequence {
    static int seqMinIndex = 0;
    static int seqMaxIndex = 0;
    static int curMinIndex = 0;
    static int curMaxIndex = -1;

    static void showSequence(int count) {
        List<Integer> numbers = new ArrayList<>();
        Random r = new Random();
        for (int i = 0; i < count; i++) {
            int val = r.nextInt(90);
            int lastVal = (numbers.isEmpty()) ? 0 : numbers.get(numbers.size()-1);
            numbers.add(val);
            if (val < lastVal) {
                curMinIndex = numbers.size()-1;
                curMaxIndex = numbers.size()-1;
            } else {
                curMaxIndex++;
                if (curMaxIndex-curMinIndex > seqMaxIndex-seqMinIndex) {
                    seqMinIndex = curMinIndex;
                    seqMaxIndex = curMaxIndex;
                }
            }
            for (int j : numbers) System.out.print(j + " ");
            System.out.println("min: " + seqMinIndex + " max: " + seqMaxIndex);
            System.out.println();
        }
    }
    public static void main(String[] args) {
        showSequence(60);
    }
}
