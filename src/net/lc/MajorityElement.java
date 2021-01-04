package net.lc;

/**
 * 169
 * Boyer-Moore Voting Algorithm
 */
public class MajorityElement {
    public int majorityElement(int[] nums) {
        int majElem = nums[0];
        int rep = 1;

        for (int i = 1; i < nums.length; i++) {
            if (nums[i] == majElem) {
                rep++;
            } else {
                rep--;
                if (rep == 0) {
                    majElem = nums[i];
                    rep = 1;
                }
            }
        }
        return majElem;
    }
}
