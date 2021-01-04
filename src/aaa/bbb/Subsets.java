package aaa.bbb;

import java.util.ArrayList;
import java.util.List;

public class Subsets {
    private List<List<Integer>> subsetOutput = new ArrayList<>();
    private int[] nums;
    private int k = 3;

    public static void main(String[] args) {
        Subsets s = new Subsets();
        List<List<Integer>> subsets = s.subsets(new int[] { 1, 2, 3, 4, 5 });
        for (List<Integer> l : subsets) {
            for (int i : l) {
                System.out.print(i + " ");
            }
            System.out.println();
        }
    }

    public List<List<Integer>> subsets(int[] nums) {
        this.nums = nums;
        subsetOutput.clear();
        int[] bitArray = new int[nums.length];
        generateSubsets(bitArray, nums.length-1, 0);
        return subsetOutput;
    }

    public void generateSubsets(int[] bitArr, int curIndex, int numOnesSoFar) {

        if (numOnesSoFar == k) {
            List<Integer> subset = new ArrayList<>();
            for (int i = curIndex; i < nums.length; i++) {
                if (bitArr[i] == 1) {
                    subset.add(nums[i]);
                }
            }
            subsetOutput.add(subset);
        }

        if (curIndex == 0) {
            return;
        } else {
            bitArr[curIndex] = 0;
            generateSubsets(bitArr, curIndex-1, numOnesSoFar);

            bitArr[curIndex] = 1;
            generateSubsets(bitArr, curIndex-1, numOnesSoFar+1);
            bitArr[curIndex] = 0;
        }
    }
}

