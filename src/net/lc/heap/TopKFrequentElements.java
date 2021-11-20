package net.lc.heap;

import java.util.*;

/**
 * 347
 */
public class TopKFrequentElements {
    static class WordPair implements Comparable<WordPair> {
        private int frequency;
        private int element;

        public WordPair(int frequency, int word) {
            this.frequency = frequency;
            this.element = word;
        }

        @Override
        public int compareTo(WordPair o) {
            if (this.frequency == o.frequency) {
                return Integer.compare(this.element, o.element);
            }

            return (Integer.compare(this.frequency, o.frequency));
        }
    }
    public int[] topKFrequent(int[] elements, int k) {
        if (elements == null || elements.length == 0) return null;

        Map<Integer, Integer> map = new HashMap<>();
        for (int word : elements) {
            if (map.containsKey(word)) {
                map.put(word, 1 + map.get(word));
            } else {
                map.put(word, 1);
            }
        }

        PriorityQueue<WordPair> pq = new PriorityQueue<>();
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            pq.add(new WordPair(entry.getValue(), entry.getKey()));
            if (pq.size() > k) pq.remove();
        }

        int[] result = new int[pq.size()];

        int index = 0;
        while (!pq.isEmpty()) {
            result[index++] = pq.remove().element;
        }
        return result;
    }

    public static void main(String[] args) {
        int[] input = {1,1,1,2,2,3};
        int[] result = new TopKFrequentElements().topKFrequent(input, 2);
        System.out.println(Arrays.toString(result));
    }
}
