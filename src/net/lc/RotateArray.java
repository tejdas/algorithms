package net.lc;

import java.util.Arrays;

/**
 * 189
 */
public class RotateArray {
    public void rotate(int[] nums, int k) {
        if (k == nums.length) return;

        k = k % nums.length;

        int startPos = 0;
        int replaceCount = 0;
        while (replaceCount < nums.length) {
            int curPos = startPos;
            int temp = nums[curPos];
            while (true) {
                int nextPos = (curPos + k) % nums.length;
                int temp2 = nums[nextPos];
                nums[nextPos] = temp;
                curPos = nextPos;
                temp = temp2;
                replaceCount++;
                if (curPos == startPos) break;
            }
            startPos++;
        }
    }

    public static void main(String[] args) {
        {
            int[] array = new int[] { 1, 2, 3, 4, 5, 6, 7 };
            new RotateArray().rotate(array, 3);
            System.out.println(Arrays.toString(array));
        }

        {
            int[] array = new int[] { 1,2,3,4 };
            new RotateArray().rotate(array, 2);
            System.out.println(Arrays.toString(array));
        }
    }
}
