package net.lc.greedy;

/**
 * 11
 * Greedy
 * Two-pointer
 */
public class ContainerWithMostWater {
    /**
     * The idea here is: higher walls retain more water.
     * Also the more distance between the walls, the more water.
     * Take a greedy approach.
     * Pick the two farthest walls (max distance).
     * Then keep on moving towards each other (shrinking distance)
     * while always moving past (ignoring) smaller wall, (and pick up the higher wall).
     * @param height
     * @return
     */
    public int maxArea(int[] height) {
        int i = 0;
        int j = height.length-1;

        int maxArea = 0;

        while (i < j) {
            int curArea = Math.min(height[i], height[j]) * (j-i);
            maxArea = Math.max(maxArea, curArea);

            /**
             * Next, pick up a new adjacent line, while sticking to whichever is the current higher line,
             * so that it can retain more water.
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
