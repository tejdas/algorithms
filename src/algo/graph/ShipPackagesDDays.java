package algo.graph;

/**
 * https://leetcode.com/problems/capacity-to-ship-packages-within-d-days/submissions/
 * Binary-search on possible answers
 * Got idea from hint and explanation
 */
public class ShipPackagesDDays {
    int[] weights;
    int D;
    public int shipWithinDays(int[] weights, int D) {
        this.weights = weights;
        this.D = D;

        int maxWeight = Integer.MIN_VALUE;

        int sum = 0;
        for (int w : weights) {
            maxWeight = Math.max(maxWeight, w);
            sum += w;
        }

        if (weights.length == D) return maxWeight;

        int avg = (int) sum/D;

        int min = Math.max(maxWeight, avg);
        int max = sum;

        // binary search between min and max

        return search(min, max);
    }

    private int search(int min, int max) {
        if (isPossible(weights, D, min)) return min;

        int left = min;
        int right = max;

        int maxIsPossible = right;
        while (left <= right) {
            int mid = (left + right)/2;

            boolean flag = isPossible(weights, D, mid);
            if (flag) {
                maxIsPossible = Math.min(mid, maxIsPossible);

                right = mid-1;
            } else {
                left = mid+1;
            }
        }

        return maxIsPossible;
    }

    private static boolean isPossible(int[] weights, int D, int capacity) {
        int daysum = 0;
        int numDays = 0;
        int index = 0;

        while (index < weights.length) {
            int w = weights[index];
            if ((daysum + w) <= capacity) {
                index++;

                daysum += w;
                if (daysum == capacity) {
                    numDays++;

                    int remainingDays = D - numDays;
                    int remainingItems = weights.length - index;

                    if (remainingDays == remainingItems) return true;
                    daysum = 0;
                } else {
                    int remainingDays = D - (numDays+1);
                    int remainingItems = weights.length - index;

                    if (remainingDays == remainingItems) return true;
                }
            } else {
                daysum = 0;
                numDays++;

                int remainingDays = D - numDays;
                int remainingItems = weights.length - index;

                if (remainingDays == remainingItems) return true;
            }
        }

        return false;
    }

    public static void main(String[] args) {
        {
            int[] weights = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
            int D = 5;

            System.out.println(new ShipPackagesDDays().shipWithinDays(weights, D));
        }

        {
            int[] weights = { 3,2,2,4,1,4 };
            int D = 3;

            System.out.println(new ShipPackagesDDays().shipWithinDays(weights, D));
        }

        {
            int[] weights = { 1,2,3,1,1 };
            int D = 4;

            System.out.println(new ShipPackagesDDays().shipWithinDays(weights, D));
        }
    }

}
