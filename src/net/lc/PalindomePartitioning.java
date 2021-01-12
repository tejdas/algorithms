package net.lc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

/**
 * 131
 * https://leetcode.com/problems/palindrome-partitioning/submissions/
 * Back-tracking (DFS)
 */
public class PalindomePartitioning {
    /**
     * memoMap[x][y]
     * -1 // not computed
     * 1 // palindrome
     * 0 // not palindrome
     */
    private int[][] memoMap;
    char[] array;
    String s;

    private final List<List<String>> result = new ArrayList<>();

    public List<List<String>> partition(String s) {
        if (s == null || s.isEmpty()) return result;

        this.s = s;
        memoMap = new int[s.length()][s.length()];
        for (int i = 0; i < memoMap.length; i++) {
            Arrays.fill(memoMap[i], -1);
        }

        array = s.toCharArray();

        Stack<String> stack = new Stack<>();

        dfs(0, stack);
        return result;
    }

    private void dfs(int curIndex, Stack<String> stack) {
        if (curIndex == array.length) {
            List<String> l = new ArrayList<>();
            for (String val : stack) l.add(val);
            result.add(l);
        }
        for (int index = curIndex; index < array.length; index++) {
            if (isPalindrome(curIndex, index)) {
                stack.push(s.substring(curIndex, index+1));
                dfs(index+1, stack);
                stack.pop();
            }
        }
    }

    private boolean isPalindrome(int x, int y) {
        if (memoMap[x][y] != -1) {
            return (memoMap[x][y] == 1);
        }

        if (x == y) {
            memoMap[x][y] = 1;
            return true;
        }

        if (array[x] != array[y]) {
            memoMap[x][y] = 0;
            return false;
        }

        if (memoMap[x + 1][y - 1] != -1) {
            if (memoMap[x + 1][y - 1] == 0) {
                memoMap[x][y] = 0;
                return false;
            } else {
                memoMap[x][y] = 1;
                return true;
            }
        }

        int left = x, right = y;
        while (array[left] == array[right]) {
            left++;
            right--;
            if (left >= right)
                break;
        }

        if (left < right) {
            memoMap[x][y] = 0;
            return false;
        }

        memoMap[x][y] = 1;
        return true;
    }

    public static void main(String[] args) {
        String input = "aab";
        List<List<String>> result = new PalindomePartitioning().partition(input);

        for (List<String> l : result) {
            System.out.println(Arrays.toString(l.toArray()));
        }
    }
}
