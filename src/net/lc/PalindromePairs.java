package net.lc;

import java.util.*;

/**
 * 336
 * Trie
 */
public class PalindromePairs {
    static class Pair {
        int x;
        int y;

        public Pair(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o)
                return true;
            if (o == null || getClass() != o.getClass())
                return false;

            Pair pair = (Pair) o;

            if (x != pair.x)
                return false;
            return y == pair.y;
        }

        @Override
        public int hashCode() {
            int result = x;
            result = 31 * result + y;
            return result;
        }
    }

    static class TrieNode {
        char c;
        Map<Character, TrieNode> children = new HashMap<>();
        int terminatingIndex = -1;

        public TrieNode(char c) {
            //System.out.println("created TrieNode: " + c);
            this.c = c;
        }

        public void print() {
            System.out.println("curNode: " + c + "  terminatingIndex: " + terminatingIndex);
            if (!children.isEmpty()) {
                for (char c : children.keySet()) System.out.print(c + "  ");
                System.out.println();
            }
        }
    }

    private TrieNode root = new TrieNode('-');
    private final Set<Pair> resultSet = new HashSet<>();
    private final List<List<Integer>> result = new ArrayList<>();
    public List<List<Integer>> palindromePairs(String[] words) {
        resultSet.clear();
        result.clear();
        insertWords(words);

        //printTrie(root);

        Set<Integer> set = new HashSet<>();
        for (int i = 0; i < words.length; i++) {
            String word = words[i];
            if (!word.isEmpty() && isPalindrome(word)) {
                set.add(i);
            }
        }

        for (int i = 0; i < words.length; i++) {
            String word = words[i];
            if (word.isEmpty()) {
                for (int val : set) {
                    resultSet.add(new Pair(i, val));
                    resultSet.add(new Pair(val, i));
                }
                continue;
            }

            TrieNode node = searchWord(word, i);
            if (node != null) {
                //System.out.println("found node for word: " + word);

                if (node.terminatingIndex != -1 && node.terminatingIndex != i) {
                    resultSet.add(new Pair(i, node.terminatingIndex));
                }

                List<Integer> indexList = new ArrayList<>();
                for (TrieNode child : node.children.values()) {

                    Stack<Character> path = new Stack<>();
                    searchMatchingNodes(child, indexList, i, path);

                    for (int index : indexList) {
                        resultSet.add(new Pair(i, index));
                    }
                }
            }
        }

        for (Pair p : resultSet) {
            List<Integer> l = new ArrayList<>();
            l.add(p.x);
            l.add(p.y);
            result.add(l);
        }
        return result;
    }

    private static boolean isPalindrome(String s) {
        if (s.isEmpty()) return true;
        char[] array = s.toCharArray();
        int i = 0;
        int j = s.length()-1;
        while (i < j) {
            if (array[i] != array[j]) {
                return false;
            }
            i++;
            j--;
        }
        return true;
    }

    private static boolean isPalindrome(Stack<Character> stack) {
        char[] array = new char[stack.size()];
        int index = 0;
        for (char c : stack) array[index++] = c;

        //System.out.println(new String(array));

        int i = 0;
        int j = array.length-1;
        while (i < j) {
            if (array[i] != array[j]) {
                return false;
            }
            i++;
            j--;
        }
        return true;
    }

    private void insertWords(String[] words) {
        for (int i = 0; i < words.length; i++) {
            if (words[i].isEmpty()) continue;
            char[] array = words[i].toCharArray();
            insertRecurse(array, array.length - 1, i, root);
        }
    }

    private void insertRecurse(char[] array, int pos, int wordIndex, TrieNode cur) {
        char c = array[pos];
        TrieNode child = cur.children.get(c);
        if (child == null) {
            cur.children.put(c, new TrieNode(c));
            child = cur.children.get(c);
        }

        if (pos == 0) {
            child.terminatingIndex = wordIndex;
        } else if (pos > 0) {
            insertRecurse(array, pos-1, wordIndex, child);
        }
    }

    private TrieNode searchWord(String s, int index) {
        if (s.isEmpty()) return null;
        return searchWordRecurse(s.toCharArray(), 0, root, index);
    }

    private TrieNode searchWordRecurse(char[] array, int pos, TrieNode cur, int index) {

        if (cur.terminatingIndex != -1) {
            // check whether remaining is palindrome
            char[] ar = new char[array.length-pos];
            System.arraycopy(array, pos, ar, 0, ar.length);
            if (isPalindrome(new String(ar))) {
                resultSet.add(new Pair(index, cur.terminatingIndex));
            }
        }

        char c = array[pos];
        if (!cur.children.containsKey(c)) {
            return null;
        }

        if (pos == array.length-1) {
            return cur.children.get(c);
        }

        return searchWordRecurse(array, pos+1, cur.children.get(c), index);
    }

    private void searchMatchingNodes(TrieNode cur, List<Integer> indexList, int matchingwordIndex, Stack<Character> path) {
        path.push(cur.c);
        if (cur.terminatingIndex != -1 && cur.terminatingIndex != matchingwordIndex) {
            if (isPalindrome(path)) {
                indexList.add(cur.terminatingIndex);
            }
        }
        for (TrieNode child : cur.children.values()) {
            searchMatchingNodes(child, indexList, matchingwordIndex, path);
        }

        path.pop();
    }
}
