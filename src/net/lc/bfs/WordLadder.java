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

        final Map<String, String> edgeTo = new HashMap<>();
        edgeTo.put(from, null);

        final Queue<String> queue = new LinkedList<>();
        queue.add(from);

        while (!queue.isEmpty()) {
            final String curNode = queue.remove();
            System.out.println(curNode);

            final char[] array = curNode.toCharArray();

            for (int i = 0; i < array.length; i++) {
                final char c = array[i];
                for (int j = 0; j < 26; j++) {
                    final char d = (char) ('a' + j);
                    if (c != d) {
                        array[i] = d;
                        final String word = new String(array);
                        if (isValidWord(word)) {

                            if (!edgeTo.containsKey(word)) {
                                edgeTo.put(word,  curNode);

                                if (word.equalsIgnoreCase(to)) {
                                    return printPath(edgeTo, word);
                                } else {
                                    queue.add(word);
                                }
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

    public static void main(String[] args) {
        List<String> words = Arrays.asList("hot","dog","dot");
        System.out.println(new WordLadder().ladderLength("hot", "dog", words));
    }
}
