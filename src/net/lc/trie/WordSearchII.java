package net.lc.trie;

import java.util.*;

/**
 * 212
 * Trie
 * DFS (All-path)
 *
 */
public class WordSearchII {
    private static final int[][] narray = {{0, 1}, {0, -1}, {-1, 0}, {1, 0}};
    static class TrieNode {
        char c;
        boolean isTerminating = false;
        private final TrieNode[] children = new TrieNode[26];

        public TrieNode(char c) {
            this.c = c;
        }
    }

    private boolean[][] visited = null;
    private int rows  = 0;
    private int cols = 0;
    char[][] board = null;
    Set<String> result = new HashSet<>();

    public List<String> findWords(char[][] board, String[] words) {
        result.clear();

        if (words.length == 0) return new ArrayList<>();

        this.board = board;
        rows = board.length;
        cols = board[0].length;

        Set<String> wordset = new HashSet<>();
        for (String word : words) wordset.add(word);
        buildTrie(wordset);

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                visited = new boolean[rows][cols];
                Stack<Character> stack = new Stack<>();
                dfs(i, j, root, stack);
            }
        }
        List<String> l = new ArrayList<>(result);
        return l;
    }

    private final TrieNode root = new TrieNode('-');

    private void dfs(int curx, int cury, TrieNode curNode, Stack<Character> stack) {
        visited[curx][cury] = true;

        char c = board[curx][cury];
        int cindex = c - 'a';

        if (curNode.children[cindex] == null) {
            visited[curx][cury] = false;
            return;
        }

        stack.push(c);
        TrieNode childNode = curNode.children[cindex];

        if (childNode.isTerminating) {
            StringBuilder sb = new StringBuilder();
            for (char s : stack) sb.append(s);
            result.add(sb.toString());
        }

        for (int[] n : narray) {
            if (curx + n[0] >= 0 && curx + n[0] < rows && cury + n[1] >= 0 && cury + n[1] < cols) {
                int nx = curx + n[0];
                int ny = cury + n[1];

                if (!visited[nx][ny]) {
                    dfs(nx, ny, childNode, stack);
                }
            }
        }
        stack.pop();
        // enable all-path traversal
        visited[curx][cury] = false;
    }

    private void buildTrie(Set<String> words) {
        for (String word : words) {
            char[] array = word.toCharArray();
            insertIntoTrie(root, array, 0);
        }
    }

    private void insertIntoTrie(TrieNode curNode, char[] array, int pos) {
        if (pos == array.length) {
            curNode.isTerminating = true;
            return;
        }
        char c = array[pos];
        int cindex = c - 'a';
        if (curNode.children[cindex] == null) {
            TrieNode t = new TrieNode(c);
            curNode.children[cindex] = t;
        }
        insertIntoTrie(curNode.children[cindex], array, pos+1);
    }
}
