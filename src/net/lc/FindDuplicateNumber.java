package net.lc;

/**
 * Saw solution
 */
public class FindDuplicateNumber {
    public int findDuplicate(int[] nums) {
        int duplicate = 0;

        for (int i = 0; i < nums.length; i++) {
            int cur = Math.abs(nums[i]);
            if (nums[cur] < 0) return cur;

            nums[cur] = -nums[cur];
        }
        return 0;
    }

    public static void main(String[] args) {

        System.out.println(new FindDuplicateNumber().findDuplicate(new int[]{1,3,4,2,2}));
        System.out.println(new FindDuplicateNumber().findDuplicate(new int[]{3,1,3,4,2}));
        //System.out.println(new FindDuplicateNumber().findDuplicate(new int[]{1,1,2}));
        //System.out.println(new FindDuplicateNumber().findDuplicate(new int[]{1,1}));

        //System.out.println(new FindDuplicateNumber().findDuplicate(new int[]{2,1,2}));
    }
}
