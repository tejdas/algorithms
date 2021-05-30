package net.lc.bfs;

import java.util.*;

/**
 * 127
 * BFS
 * Directed Graph
 */
public class WordLadder {
    private static Set<String> words = new HashSet<>();

    private static boolean isValidWord(final String word) {
        return words.contains(word);
    }

    public int ladderLength(String beginWord, String endWord, List<String> wordList) {
        words.clear();
        words.addAll(wordList);
        return convertWord(beginWord, endWord);
    }

    private int convertWord(final String from, final String to) {
        final Set<String> visited = new HashSet<>();

        /**
         * Map of toNode -> FromNode
         */
        final Map<String, String> edgeTo = new HashMap<>();

        visited.add(from);

        final Queue<String> nodes = new LinkedList<>();
        nodes.add(from);

        while (!nodes.isEmpty()) {
            final String curNode = nodes.remove();

            final char[] array = curNode.toCharArray();

            for (int i = 0; i < array.length; i++) {
                final char c = array[i];
                for (int j = 0; j < 26; j++) {
                    final char d = (char) ('a' + j);
                    if (c != d) {
                        array[i] = d;
                        final String word = new String(array);
                        if (isValidWord(word)) {
                            if (word.equalsIgnoreCase(to)) {
                                //System.out.println("found: " + to);
                                edgeTo.put(word,  curNode);

                                return printPath(edgeTo, word);
                            }

                            if (!visited.contains(word)) {
                                visited.add(word);
                                nodes.add(word);
                                edgeTo.put(word, curNode);
                            }
                        }
                    }
                }
                array[i] = c;
            }
        }
        return 0;
    }

    private int printPath(final Map<String, String> edgeTo, final String word) {
        int length = 1;
        String temp = word;
        while (true) {
            final String fromTemp = edgeTo.get(temp);
            if (fromTemp == null) {
                break;
            }
            //System.out.println(fromTemp);
            length++;
            temp = fromTemp;
        }
        return length;
    }
}
