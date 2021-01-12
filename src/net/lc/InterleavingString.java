package net.lc;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 97
 * Dynamic Programming
 * bottom-up
 */
public class InterleavingString {
    static class SPair {
        int p1;
        int p2;

        public SPair(int p1, int p2) {
            this.p1 = p1;
            this.p2 = p2;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o)
                return true;
            if (o == null || getClass() != o.getClass())
                return false;

            SPair sPair = (SPair) o;

            if (p1 != sPair.p1)
                return false;
            return p2 == sPair.p2;
        }

        @Override
        public int hashCode() {
            int result = p1;
            result = 31 * result + p2;
            return result;
        }
    }
    public boolean isInterleave(String s1, String s2, String s3) {

        char[] s1Array = s1.toCharArray();
        char[] s2Array = s2.toCharArray();
        char[] s3Array = s3.toCharArray();

        if (s3Array.length != s1Array.length + s2Array.length) {
            return false;
        }

        if (s1.isEmpty()) {
            return s2.equalsIgnoreCase(s3);
        }

        if (s2.isEmpty()) {
            return s1.equalsIgnoreCase(s3);
        }

        Map<Integer, Set<SPair>> result = new HashMap<>();

        for (int i = 0; i < s3Array.length; i++) {
            result.put(i, new HashSet<>());

            if (i == 0) {
                if (s3Array[0] == s1Array[0]) {
                    result.get(i).add(new SPair(0, -1));
                }

                if (s3Array[0] == s2Array[0]) {
                    result.get(i).add(new SPair(-1, 0));
                }
            } else {
                Set<SPair> prevset = result.get(i-1);

                for (SPair p : prevset) {
                    int p1 = p.p1;
                    int p2 = p.p2;

                    if (p1+1 < s1Array.length) {
                        if (s3Array[i] == s1Array[p1 + 1]) {
                            result.get(i).add(new SPair(p1 + 1, p2));
                        }
                    }

                    if (p2+1 < s2Array.length) {
                        if (s3Array[i] == s2Array[p2 + 1]) {
                            result.get(i).add(new SPair(p1, p2 + 1));
                        }
                    }
                }
            }

            if (result.get(i).isEmpty()) return false;
        }

        return true;
    }
}
