package net.lc;

import java.math.BigInteger;
import java.util.*;

public class LongestStringChain {
    static final Integer[] mapping = {2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41,  43,  47,  53,  59,  61,  67,  71,  73,  79,  83,  89,  97,  101};
    private static final Set<Integer> set = new HashSet<>();
    static {
        set.addAll(Arrays.asList(mapping));
    }

    private final List<BigInteger> multList = new ArrayList<>();
    public int longestStrChain(String[] words) {
        if (words == null || words.length == 0) return 0;

        if (words.length == 1) return 1;

        SortedMap<Integer, Set<Integer>> lMap = new TreeMap<>();
        for (int i = 0; i < words.length; i++) {
            String word = words[i];
            Set<Integer> sset = lMap.computeIfAbsent(word.length(), k -> new HashSet<>());
            sset.add(i);
        }

        multList.clear();

        for (int i = 0; i < words.length; i++) {
            String word = words[i];
            char[] array = word.toCharArray();
            BigInteger prod = BigInteger.valueOf(1);
            for (char c : array) {
                int cval = c - 97;
                prod = prod.multiply(BigInteger.valueOf(mapping[cval]));
            }

            multList.add(prod);
        }

        Map<Integer, Integer> resultMap = new HashMap<>();

        int maxLen = 1;

        for (int lkey : lMap.keySet()) {
            Set<Integer> sset = lMap.get(lkey);
            Set<Integer> prevset = lMap.containsKey(lkey-1)? lMap.get(lkey-1) : null;

            if (prevset == null) {
                for (int index : sset) {
                    resultMap.put(index, 1);
                }
                continue;
            }

            for (int index : sset) {
                int resi = 1;

                BigInteger multi = multList.get(index);

                for (int pindex : prevset) {
                    BigInteger multj = multList.get(pindex);
                    BigInteger[] array = multi.divideAndRemainder(multj);

                    if (array[1].intValue() == 0) {
                        if (set.contains(array[0].intValue())) {
                            resi = Math.max(resi, 1 + resultMap.get(pindex));
                        }
                    }
                }

                resultMap.put(index, resi);
                maxLen = Math.max(resi, maxLen);
            }
        }

        return maxLen;
    }
}
