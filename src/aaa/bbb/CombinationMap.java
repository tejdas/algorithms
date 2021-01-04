package aaa.bbb;

import java.util.HashMap;
import java.util.Map;

public class CombinationMap {

    static class Key {
        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + n;
            result = prime * result + r;
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            final Key other = (Key) obj;
            if (n != other.n)
                return false;
            if (r != other.r)
                return false;
            return true;
        }
        public Key(int n, int r) {
            this.n = n;
            this.r = r;
        }

        int n;
        int r;
    }

    public static void main(String[] args) {

        final int[] array = { 7, 3, 9, 4 };

        int largestVal = -1;

        for (final int val : array) {
            if (val > largestVal) {
                largestVal = val;
            }
        }
        final CombinationMap cm = new CombinationMap();
        cm.generateCombinations(largestVal, array);
    }

    public void generateCombinations(int input, int[] array) {
        final Map<Key, Long> map = new HashMap<>();

        for (int i = 0; i <= input; i++) {
            map.put(new Key(i, i), 1L);
            map.put(new Key(i, 0), 1L);
        }

        for (int i = 1; i <= input; i++) {
            for (int j = 1; j < (i / 2) + 1; j++) {
                final long val1 = map.get(new Key(i - 1, j));
                final long val2 = map.get(new Key(i - 1, j - 1));

                map.put(new Key(i, j), (val1 + val2));
                map.put(new Key(i, (i - j)), (val1 + val2));
            }
        }

        for (final int key : array) {
            for (int j = 0; j <= key; j++) {
                final long val = map.get(new Key(key, j));
                System.out.print(val % 1000000000L + " ");
            }
            System.out.println();
        }
    }
}
