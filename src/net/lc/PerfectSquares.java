package net.lc;

import java.util.HashMap;
import java.util.Map;

/**
 * 279
 * Dynamic Programming
 */
public class PerfectSquares {
    Map<Integer, Integer> memoized = new HashMap<>();
    private int sqrt;

    public int numSquares(int n) {
        memoized.clear();

        sqrt = (int) Math.sqrt(n);
        return getTotal(n);
    }

    private int getTotal(int n) {

        if (n == 0) return 0;
        if (n == 1) return 1;
        if (memoized.containsKey(n)) {
            return memoized.get(n);
        }

        int result = Integer.MAX_VALUE;

        for (int i = sqrt; i >= 1; i--) {
            int val = i*i;

            if (val > n) continue;

            if (i == n) {
                memoized.put(n, 1);
                return 1;
            }


            int divres = n / val;
            int remainder = n % val;

            int res = (remainder > 0)? getTotal(remainder) : 0;
            res += divres;
            result = Math.min(result, res);
        }

        memoized.put(n, result);
        return result;
    }
}
