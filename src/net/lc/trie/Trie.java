package net.lc.trie;

/**
 * 208
 */
public class Trie {
    static class TrieNode {
        char c;
        /**
         * Indicates whether a word terminates in the current TrieNode.
         */
        boolean terminating = false;
        boolean hasChildren = false;
        TrieNode[] children = new TrieNode[26];

        public TrieNode(char c) {
            this.c = c;
        }

        public void addChild(int index, TrieNode child) {
            children[index] = child;
            hasChildren = true;
        }
    }

    private final TrieNode root = new TrieNode('-');

    public void insert(String word) {
        char[] array = word.toCharArray();
        addRecurse(root, 0, array);
    }

    public boolean search(String word) {
        char[] array = word.toCharArray();
        return searchRecurse(root, 0, array, false);
    }

    public boolean startsWith(String word) {
        char[] array = word.toCharArray();
        return searchRecurse(root, 0, array, true);
    }

    private void addRecurse(TrieNode curNode, int index, char[] array) {
        if (index == array.length) {
            return;
        }

        char c = array[index];
        int cindex = c - 'a';
        TrieNode child = curNode.children[cindex];

        if (child == null) {
            child = new TrieNode(c);
            curNode.addChild(cindex, child);
        }

        if (index == array.length-1) {
            child.terminating = true;
        } else {
            addRecurse(child, index+1, array);
        }
    }

    private boolean searchRecurse(TrieNode curNode, int index, char[] array, boolean isPrefixSearch) {
        if (index == array.length) {
            if (isPrefixSearch) return true;
            else {
                return curNode.terminating || !curNode.hasChildren;
            }
        }

        char c = array[index];
        int cindex = c - 'a';
        if (curNode.children[cindex] == null) {
            return false;
        }
        TrieNode child = curNode.children[cindex];
        return searchRecurse(child, index+1, array, isPrefixSearch);
    }
}
