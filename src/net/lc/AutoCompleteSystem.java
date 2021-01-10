package net.lc;

import java.util.*;

/**
 * 642
 * Trie
 */
public class AutoCompleteSystem {
    static class ACSKey implements Comparable<ACSKey> {
        private final int count;
        private final  String sentence;

        public ACSKey(int count, String sentence) {
            this.count = count;
            this.sentence = sentence;
        }

        @Override
        public int compareTo(ACSKey o) {
            if (this.count == o.count) {
                return this.sentence.compareTo(o.sentence);
            }

            return Integer.compare(o.count, this.count);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o)
                return true;
            if (o == null || getClass() != o.getClass())
                return false;

            ACSKey acsKey = (ACSKey) o;

            if (count != acsKey.count)
                return false;
            return sentence != null ? sentence.equals(acsKey.sentence) : acsKey.sentence == null;
        }

        @Override
        public int hashCode() {
            int result = count;
            result = 31 * result + (sentence != null ? sentence.hashCode() : 0);
            return result;
        }
    }

    static class TrieNode {
        char c;
        int count = -1;
        Map<Character, TrieNode> children = new HashMap<>();
        public TrieNode(char c) {
            this.c = c;
        }
        Map<String, Integer> countMap = new HashMap<>();
        SortedSet<ACSKey> set = new TreeSet<>();
    }

    private final TrieNode root = new TrieNode('-');
    private StringBuilder sb = new StringBuilder();
    private TrieNode searchNode = root;

    public void addSentence(String sentence, int count) {
        char[] array = sentence.toCharArray();

        Map<String, Integer> map = new HashMap<>();
        addRecurse(root, 0, array, count, map);
    }

    private void addRecurse(TrieNode curNode, int index, char[] array, int count, Map<String, Integer> map) {
        if (index == array.length) {
            return;
        }

        char c = array[index];
        TrieNode child = curNode.children.get(c);

        if (child == null) {
            child = new TrieNode(c);
            curNode.children.put(c, child);
        }

        if (index == array.length-1) {
            if (child.count == -1) {
                child.count = count;
            } else {
                child.count += count;

            }
            map.put(new String(array), child.count);
        } else {
            addRecurse(child, index + 1, array, count, map);
        }
        compute(child, map);
    }

    private void compute(TrieNode curNode, Map<String, Integer> map) {
        if (curNode != root) {
            for (Map.Entry<String, Integer> entry : map.entrySet()) {
                if (curNode.countMap.containsKey(entry.getKey())) {
                    int curvalue = curNode.countMap.get(entry.getKey());
                    curNode.set.remove(new ACSKey(curvalue, entry.getKey()));

                    curNode.countMap.put(entry.getKey(), entry.getValue());
                    curNode.set.add(new ACSKey(entry.getValue(), entry.getKey()));

                } else {
                    curNode.countMap.put(entry.getKey(), entry.getValue());
                    curNode.set.add(new ACSKey(entry.getValue(), entry.getKey()));
                }
            }
        }
    }

    public AutoCompleteSystem(String[] sentences, int[] times) {
        for (int i = 0; i < sentences.length; i++) {
            addSentence(sentences[i], times[i]);
        }
    }

    public List<String> input(char c) {
        if (c == '#') {
            addSentence(sb.toString(), 1);
            sb = new StringBuilder();
        } else {
            sb.append(c);
        }

        List<String> output = null;
        if (searchNode != null && searchNode.children.containsKey(c)) {
            TrieNode child = searchNode.children.get(c);

            output = collectOutput(child);
            searchNode = child;
        } else {
            searchNode = null;
            output = new ArrayList<>();
        }

        if (c == '#') {
            searchNode = root;
        }
        return output;
    }

    private List<String> collectOutput(TrieNode cur) {
        List<String> output = new ArrayList<>();
        int count = 0;
        for (ACSKey key : cur.set) {
            output.add(key.sentence);
            count++;
            if (count == 3) break;
        }
        return output;
    }
}
