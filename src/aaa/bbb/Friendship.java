package aaa.bbb;

import java.util.Arrays;
import java.util.List;

public class Friendship {
    public int solve(List<Integer> A) {
        int result = 0;
        for (int index = 0; index < A.size(); index++) {
            int curIndex = index+1;

            int bfCur = A.get(index);
            if (bfCur <= curIndex) continue;

            int bestFriendOfBfCur = A.get(bfCur-1);
            if (bestFriendOfBfCur == curIndex) {
                System.out.println(curIndex);
                result++;
            }
        }

        return result;
    }

    public static void main(String[] args) {
        List<Integer> input = Arrays.asList(10,28,9,25,8,25,9,27,23,6,13,4,1,13,11,10,21,7,7,13,9,28,8,28,10,24,11,28);
        System.out.println(input.size());
        System.out.println(new Friendship().solve(input));
    }
}
