package net.lc;

import java.util.ArrayList;
import java.util.List;

/**
 * 1156
 * Run-Length Encoding
 */
public class SwapForLongestRepeatedCharSubstring {
    static class RLENode {
        char c;
        int count;

        public RLENode(char c, int count) {
            this.c = c;
            this.count = count;
        }
    }

    int[] countArray = new int[26];
    public int maxRepOpt1(String text) {

        char[] array = text.toCharArray();

        List<RLENode> rleList = new ArrayList<>();

        int left = 0;
        int right = 0;

        countArray[array[0] - 'a']++;

        for (int i = 1; i < array.length; i++) {
            countArray[array[i] - 'a']++;
            if (array[i] == array[i-1]) {
                right++;
            } else {
                RLENode rleNode = new RLENode(array[i-1], right-left+1);
                rleList.add(rleNode);
                left = i;
                right = i;
            }
        }

        RLENode rleNode = new RLENode(array[array.length-1], array.length-left);
        rleList.add(rleNode);

        if (rleList.size() == 1) return array.length;

        int max = 0;
        for (RLENode rleNode1 : rleList) {
            if (countArray[rleNode1.c - 'a'] > rleNode1.count) {
                max = Math.max(max, rleNode1.count+1);
            } else {
                max = Math.max(max, rleNode1.count);
            }
        }

        for (int i = 1; i < rleList.size()-1; i++) {
            RLENode rn = rleList.get(i);
            if (rn.count == 1) {
                RLENode prn = rleList.get(i-1);
                RLENode nrn = rleList.get(i+1);

                if (prn.c == nrn.c) {
                    int count = prn.count + nrn.count;
                    if (countArray[prn.c - 'a'] > count) {
                        max = Math.max(max, count+1);
                    } else if (countArray[prn.c - 'a'] == count) {
                        max = Math.max(max, count);
                    }
                }
            }
        }

        return max;
    }

    public static void main(String[] args) {
        System.out.println(new SwapForLongestRepeatedCharSubstring().maxRepOpt1("ababa"));
        System.out.println(new SwapForLongestRepeatedCharSubstring().maxRepOpt1("aaabaaa"));
        System.out.println(new SwapForLongestRepeatedCharSubstring().maxRepOpt1("aaabbaaa"));
        System.out.println(new SwapForLongestRepeatedCharSubstring().maxRepOpt1("aaaaa"));
    }
}
