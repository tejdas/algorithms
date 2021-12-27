package net.lc;

/**
 * 1095
 * Binary Search
 * https://leetcode.com/problems/find-in-mountain-array/submissions/
 */
interface MountainArray {
    int get(int index);
    int length();
}

public class FindInMountainArray {
    static class MountainArrayImpl implements MountainArray {
        int[] array;

        public MountainArrayImpl(int[] array) {
            this.array = array;
        }

        @Override
        public int get(int index) {
            return array[index];
        }

        @Override
        public int length() {
            return array.length;
        }
    }

    public int findInMountainArray(int target, MountainArray mountainArr) {
        // find peak
        int left = 0;
        int right = mountainArr.length()-1;
        int origright = right;

        int peakIndex = -1;

        /**
         * Find peak-index using binary-search
         */
        while (left < right) {
            int mid = (left+right)/2;

            int midval = mountainArr.get(mid);
            int lval = mountainArr.get(mid-1);
            int rval = mountainArr.get(mid+1);

            if (midval > lval && midval > rval) {
                peakIndex = mid;
                break;
            }

            if (lval < midval && midval < rval) {
                left = mid;
            } else {
                right = mid;
            }
        }

        if (peakIndex == -1) return -1;

        System.out.println(peakIndex);
        if (mountainArr.get(peakIndex) == target) return peakIndex;

        /**
         * Target might occur twice on the left and right of peakIndex.
         * Now binary-search between 0 and peakIndex-1 for target
         */
        left = 0;
        right = peakIndex-1;

        int targetIndex = getIndex(left, right, target, mountainArr, true);
        if (targetIndex != -1) return targetIndex;

        /**
         * Binary-search between peakIndex+1 and right for target
         */
        left = peakIndex+1;
        right = origright;

        return getIndex(left, right, target, mountainArr, false);
    }

    private int getIndex(int left, int right, int target, MountainArray mountainArr, boolean increasing) {

        if (target == mountainArr.get(right)) return right;
        if (target == mountainArr.get(left)) return left;
        if (increasing) {
            if (target > mountainArr.get(right) || target < mountainArr.get(left))
                return -1;
            while (left <= right) {
                int mid = (left + right) / 2;
                int midval = mountainArr.get(mid);
                if (midval == target) {
                    return mid;
                } else if (midval > target) {
                    right = mid-1;
                } else {
                    left = mid+1;
                }
            }
        } else {
            if (target < mountainArr.get(right) || target > mountainArr.get(left))
                return -1;
            while (left <= right) {
                int mid = (left + right) / 2;
                int midval = mountainArr.get(mid);
                if (midval == target) {
                    return mid;
                } else if (midval > target) {
                    left = mid+1;
                } else {
                    right = mid-1;
                }
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        {
            int[] array = { 0, 1, 2, 4, 2, 1 };
            //int index = new FindInMountainArray().findInMountainArray(3, new MountainArrayImpl(array));
            //System.out.println(index);
        }

        {
            int[] array = { 0, 5,3, 1 };
            int index = new FindInMountainArray().findInMountainArray(2, new MountainArrayImpl(array));
            System.out.println(index);
        }
    }
}
