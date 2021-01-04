package net.lc;

/**
 * 11
 * Two-pointer
 */
public class ContainerWithMostWater {
    public int maxArea(int[] height) {
        int i = 0;
        int j = height.length-1;

        int maxArea = 0;

        while (i < j) {
            int curArea = Math.min(height[i], height[j]) * (j-i);
            if (curArea > maxArea) maxArea = curArea;

            /**
             * Next, pickup a higher line, so that it can retain more water
             */
            if (height[i] < height[j]) {
                i++;
            }
            else {
                j--;
            }
        }
        return maxArea;
    }
}
