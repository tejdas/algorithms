package aaa.bbb;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class FooBar {

    static class BingoCard {
        public void play() {}
    }

    public static void main2(String[] args) {
        BingoCard[] array = new BingoCard[8];
        for (int i = 0; i < array.length; i++) {
            array[i] = new BingoCard();
        }

        for (BingoCard bc : array) {
            System.out.println(bc);
        }

    }

    private static ConcurrentMap<String, Double> map = new ConcurrentHashMap<>();

    private static double get(String key) {
        return map.get(key);
    }
    public static void main3(String[] args) {

        map.put("aaa", 52.0);
        map.remove("aaa");
        double d = get("aaa");
        System.out.println(d);
    }

    public static void main(String[] args) {
        int[] val = {2, 10, 15, 34, 9, 12, 5, 47,  73, 19};

        PriorityQueue<Integer> pq = new PriorityQueue<>();

        for (int v : val) {
            pq.add(v);
            if (pq.size() > 3) {
                pq.remove();
            }
        }

        while (!pq.isEmpty()) {
            System.out.println(pq.remove());
        }
    }
}
