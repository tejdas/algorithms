package net.lc.map;

import java.util.*;

/**
 * 49
 * map
 * strings
 */
public class GroupAnagrams {
    private List<Integer> primes = new ArrayList<>();
    private Map<Character, Integer> map = new HashMap<>();

    private void init() {

        generatePrime();

        for (int i = 0; i < 26; i++) {
            char d = (char) ('a' + i);
            map.put(d, primes.get(i));
        }
    }

    private void generatePrime() {
        primes.addAll(Arrays.asList(2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41,  43,  47,  53,  59,  61,  67,  71,  73,  79,  83,  89,  97,  101));

        int nextNum = 37;
        while (primes.size() < 26) {
            if (isPrime(nextNum)) {
                primes.add(nextNum);
            }
            nextNum++;
        }
    }

    private boolean isPrime(int num) {
        int sqrt = (int) Math.sqrt((double) num);

        for (int i = 2; i <= sqrt; i++) {
            if (num % i == 0) {
                return false;
            }
        }

        return true;
    }

    private long compute(String str) {
        char[] array = str.toCharArray();

        long mult = 1;
        for (char c : array) {
            mult = mult * map.get(c);
        }
        return mult;
    }

    private final Map<Long, List<String>> anagrams = new HashMap<>();

    public List<List<String>> groupAnagrams(String[] strs) {

        if (strs == null || strs.length == 0) {
            return new ArrayList<>();
        }

        init();

        List<List<String>> output = new ArrayList<>();

        for (String s : strs) {
            long anval = compute(s);
            List<String> list = anagrams.computeIfAbsent(anval, k-> new ArrayList<>());
            list.add(s);
        }

        for (List<String> list : anagrams.values()) {
            output.add(list);
        }
        return output;
    }
}
