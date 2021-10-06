package net.lc;

import java.util.*;

/**
 * https://leetcode.com/problems/length-of-longest-fibonacci-subsequence/submissions/
 * Dynamic Programming
 */
public class LongestFibSubsequence {

    static class Info {
        int fibLen;
        int lastNumber;

        public Info(int fibLen, int lastNumber) {
            this.fibLen = fibLen;
            this.lastNumber = lastNumber;
        }
    }

    public int lenLongestFibSubseq1(int[] array) {

        List[] res = new List[array.length];

        if (res.length < 3)
            return 0;
        res[0] = new ArrayList<>();
        res[1] = new ArrayList<>();

        int maxLen = 0;

        for (int i = 2; i < array.length; i++) {
            res[i] = new ArrayList<>();

            for (int j = i - 1; j >= 0; j--) {
                List<Info> jlists = res[j];

                for (Info jlist : jlists) {
                    if (jlist.fibLen >= 2) {

                        if (array[i] == array[j] + jlist.lastNumber) {
                            Info info = new Info(jlist.fibLen+1, array[j]);
                            res[i].add(info);
                            maxLen = Math.max(maxLen, info.fibLen);
                        }
                    }
                }
            }

            List<int[]> pairs = getPairSumTo(array, i);
            for (int[] pair : pairs) {
                Info info = new Info(3, pair[1]);
                maxLen = Math.max(maxLen, info.fibLen);
                res[i].add(info);
            }
        }
        return maxLen;
    }

    public int lenLongestFibSubseq2(int[] array) {


        List[] res = new List[array.length];

        if (res.length < 3)
            return 0;
        res[0] = new ArrayList<>();
        res[1] = new ArrayList<>();

        int maxLen = 0;


        Map<Integer, List<Integer>> posMap = new HashMap<>();
        for (int i = 0; i < array.length; i++) {
            List<Integer> l = posMap.computeIfAbsent(array[i], k -> new ArrayList<>());
            l.add(i);
        }

        for (int i = 0; i < array.length - 2; i++) {
            for (int j = i + 1; j < array.length - 1; j++) {
                int sum = array[i] + array[j];

                if (posMap.containsKey(sum)) {
                    List<Integer> l = posMap.get(sum);

                    for (int id = l.size() - 1; id >= 0; id--) {
                        int pid = l.get(id);
                        if (pid <= j)
                            break;

                        if (res[pid] == null) {
                            res[pid] = new ArrayList<>();
                        }

                        List<Integer> idlist = new ArrayList<>();
                        idlist.add(array[i]);
                        idlist.add(array[j]);
                        idlist.add(array[pid]);
                        res[pid].add(idlist);
                        maxLen = Math.max(maxLen, idlist.size());
                    }
                }
            }
        }


        for (int i = 2; i < array.length; i++) {
            if (res[i] == null) {
                res[i] = new ArrayList<>();
            }

            for (int j = i - 1; j >= 0; j--) {
                List<List<Integer>> jlists = res[j];

                for (List<Integer> jlist : jlists) {
                    if (jlist.size() >= 2) {
                        int jsize = jlist.size();
                        if (array[i] == jlist.get(jsize - 1) + jlist.get(jsize - 2)) {
                            List<Integer> ilist = new ArrayList<>();
                            ilist.addAll(jlist);
                            ilist.add(array[i]);
                            maxLen = Math.max(maxLen, ilist.size());
                            res[i].add(ilist);
                        }
                    }
                }
            }
/*
            List<int[]> pairs = getPairSumTo(array, i);
            for (int[] pair : pairs) {
                List<Integer> ilist = new ArrayList<>();
                ilist.add(array[pair[0]]);
                ilist.add(array[pair[1]]);
                ilist.add(array[i]);
                maxLen = Math.max(maxLen, ilist.size());
                res[i].add(ilist);
            }
            */
        }
        return maxLen;
    }

    private List<int[]> getPairSumTo(int[] array, int targetIndex) {
        List<int[]> res = new ArrayList<>();
        int i = 0;
        int j = targetIndex-1;
        while (i < j) {
            if (array[i] + array[j] == array[targetIndex]) {
                res.add(new int[] {i, j});
                i++;
                j--;
            } else if (array[i] + array[j] < array[targetIndex]) {
                i++;
            } else j--;
        }
        return res;
    }

    public int lenLongestFibSubseq(int[] array) {
        final Map<Integer, TreeSet<Integer>> map = new HashMap<>();

        for (int i = 0; i < array.length; i++) {
            TreeSet<Integer> l = map.computeIfAbsent(array[i], k -> new TreeSet<>());
            l.add(i);
        }

        int maxLen = 0;

        for (int i = 0; i < array.length-2; i++) {
            for (int j = i+1; j < array.length-1; j++) {
                int sum = array[i] + array[j];
                if (!map.containsKey(sum)) continue;

                int fiblen = 2;

                int prev = i;
                int cur = j;
                while (true) {
                    TreeSet<Integer> set = map.get(sum);
                    if (set == null) break;
                    Integer index = set.higher(j);
                    if (index == null) break;

                    fiblen++;
                    prev = cur;
                    cur = index;
                    sum = array[prev] + array[cur];
                }

                maxLen = Math.max(maxLen, fiblen);
            }
        }

        return maxLen;
    }

    public static void main(String[] args) {

        {
            int[] input = { 1, 2, 3, 4, 5, 6, 7, 8 };
            System.out.println(new LongestFibSubsequence().lenLongestFibSubseq(input));
        }

        {
            int[] input = { 1, 3, 7, 11, 12, 14, 18 };
            System.out.println(new LongestFibSubsequence().lenLongestFibSubseq(input));
        }

        {
            int[] input = { 2, 4, 7, 8, 9, 10, 14, 15, 18, 23, 32, 50 };
            System.out.println(new LongestFibSubsequence().lenLongestFibSubseq(input));
        }

        {
            int[] input = { 2, 4, 5, 6, 7, 8, 11, 13, 14, 15, 21, 22, 34 };
            System.out.println(new LongestFibSubsequence().lenLongestFibSubseq(input));
        }

        {
            int[] input = { 1,2,3,6,7,8,9,10,12,14,21,33 };
            System.out.println(new LongestFibSubsequence().lenLongestFibSubseq(input));
        }
    }
}
