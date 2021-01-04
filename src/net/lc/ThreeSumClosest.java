package net.lc;

import java.util.Arrays;

public class ThreeSumClosest {
    public int threeSumClosest(int[] nums, int target) {
        Arrays.sort(nums);

        int diff = Integer.MAX_VALUE;
        int ans = 0;

        for (int anchor = 0; anchor < nums.length; anchor++) {
            int left = 0;
            int right = nums.length-1;

            while (left < right) {
                if (left == anchor) {
                    left++;
                    continue;
                } else if (right == anchor) {
                    right--;
                    continue;
                }

                int sum0 = nums[left] + nums[right];
                int sum = sum0 + nums[anchor];

                if (sum == target) {
                    return sum;
                }

                int curdiff = Math.abs(sum - target);

                if (curdiff < diff) {
                    diff = curdiff;
                    ans = sum;
                }

                if (sum > target) right--;
                else left++;
            }
        }

        return ans;
    }

    public static void main(String[] args) {
        int[] nums = {-55,-24,-18,-11,-7,-3,4,5,6,9,11,23,33};
        int target = 0;

        System.out.println(new ThreeSumClosest().threeSumClosest(nums, target));
    }
}
