package net.lc.recurse;

import java.util.List;

/**
 * 364
 * 339
 * Recursion
 */
public class NestedListWeightSum {
    interface NestedInteger {
        //=cnstructor initializes an empty nested list



        // Constructor initializes a single integer.

        // @return true if this NestedInteger holds a single integer, rather than a nested list.
        public boolean isInteger();

        // @return the single integer that this NestedInteger holds, if it holds a single integer
        // Return null if this NestedInteger holds a nested list
        public Integer getInteger();

        // Set this NestedInteger to hold a single integer.
        public void setInteger(int value);

        // Set this NestedInteger to hold a nested list and adds a nested integer to it.
        public void add(NestedInteger ni);

        // @return the nested list that this NestedInteger holds, if it holds a nested list
        // Return null if this NestedInteger holds a single integer
        public List<NestedInteger> getList();
    }

    public int depthSumInverse(List<NestedInteger> nestedList) {
        int depth = findDepth(nestedList);
        //System.out.println(depth);

        return getSumInverse(nestedList, depth, 0);
    }

    private int findDepth(List<NestedInteger> niList) {
        int depth = 1;

        for (NestedInteger nii : niList) {
            if (!nii.isInteger())
                depth = Math.max(depth, 1 + findDepth(nii.getList()));
        }
        return depth;
    }

    private int getSumInverse(List<NestedInteger> list, int depth, int curLevel) {
        int sum = 0;
        for (NestedInteger ni : list) {
            if (ni.isInteger())
                sum += ni.getInteger() * (depth - curLevel);
            else {
                sum += getSumInverse(ni.getList(), depth, curLevel + 1);
            }
        }
        return sum;
    }

    public int depthSum(List<NestedInteger> nestedList) {
        return getSum(nestedList, 1);
    }

    private int getSum(List<NestedInteger> list, int curLevel) {
        int sum = 0;
        for (NestedInteger ni : list) {
            if (ni.isInteger())
                sum += ni.getInteger() * curLevel;
            else {
                sum += getSum(ni.getList(), curLevel + 1);
            }
        }
        return sum;
    }
}
