package net.lc;

import java.util.HashMap;
import java.util.Map;

/**
 * 211
 * Trie
 */
public class WordDictionary {
    static class TrieNode {
        char c;
        boolean terminating = false;
        Map<Character, TrieNode> children = new HashMap<>();

        public TrieNode(char c) {
            this.c = c;
        }
    }

    private final TrieNode root = new TrieNode('-');

    public WordDictionary() {
    }


    public void addWord(String word) {
        char[] array = word.toCharArray();
        addRecurse(root, 0, array);
    }

    public boolean search(String word) {
        char[] array = word.toCharArray();
        return searchRecurse(root, 0, array);
    }

    private void addRecurse(TrieNode curNode, int index, char[] array) {
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
            child.terminating = true;
        }

        addRecurse(child, index+1, array);
    }

    private boolean searchRecurse(TrieNode curNode, int index, char[] array) {
        if (index == array.length) {
            return curNode.terminating || curNode.children.isEmpty();
        }

        char c = array[index];

        if (c != '.') {
            if (!curNode.children.containsKey(c)) {
                return false;
            }
            TrieNode child = curNode.children.get(c);
            return searchRecurse(child, index+1, array);
        }

        // If c == . then search on all children (matching all characters)
        for (TrieNode child : curNode.children.values()) {
            if (searchRecurse(child, index+1, array)) {
                return true;
            }
        }
        return false;
    }
}
