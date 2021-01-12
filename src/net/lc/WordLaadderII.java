package net.lc;

import java.util.*;

/**
 * 126
 * BFS
 * DFS
 * First do BFS to find the shortest path.
 * Then run all-path DFS along the shortest paths to find all paths.
 */
public class WordLaadderII {
    private static Set<String> words = new HashSet<>();

    private final Map<String, Integer> distanceMap = new HashMap<>();

    private final Map<String, Set<String>> adjMap = new HashMap<>();

    private final List<List<String>> result = new ArrayList<>();

    private final Set<String> dfsVisited = new HashSet<>();

    private static boolean isValidWord(final String word) {
        return words.contains(word);
    }

    public List<List<String>> findLadders(String beginWord, String endWord, List<String> wordList) {
        distanceMap.clear();
        adjMap.clear();
        dfsVisited.clear();
        words.clear();
        words.addAll(wordList);

        bfs(beginWord, endWord);

        Stack<String> path = new Stack<>();
        dfs(beginWord, endWord, path);
        return result;
    }

    private void bfs(final String from, final String to) {

        final Set<String> visited = new HashSet<>();

        distanceMap.put(from, 0);
        final Queue<String> queue = new LinkedList<>();
        queue.add(from);

        while (!queue.isEmpty()) {
            final String curNode = queue.remove();

            if (visited.contains(curNode)) continue;
            visited.add(curNode);
            adjMap.put(curNode, new HashSet<>());

            final char[] array = curNode.toCharArray();

            for (int i = 0; i < array.length; i++) {
                final char c = array[i];
                for (int j = 0; j < 26; j++) {
                    final char d = (char) ('a' + j);
                    if (c != d) {
                        array[i] = d;
                        final String word = new String(array);

                        if (isValidWord(word)) {

                            /**
                             * Build adjacency map
                             */
                            adjMap.get(curNode).add(word);

                            if (distanceMap.containsKey(word)) {
                                /**
                                 * reevaluate shortest path
                                 */
                                if (distanceMap.get(word) > 1 + distanceMap.get(curNode)) {
                                    distanceMap.put(word, 1 + distanceMap.get(curNode));
                                }
                            } else {
                                distanceMap.put(word, 1 + distanceMap.get(curNode));
                            }

                            if (word.equalsIgnoreCase(to)) {
                                /**
                                 * DO not stop BFS. Find all paths.
                                 */
                                continue;
                            }

                            if (!visited.contains(word)) {
                                queue.add(word);
                            }
                        }
                    }
                }
                array[i] = c;
            }
        }
    }

    /**
     * All-path DFS
     * @param cur
     * @param to
     * @param path
     */
    private void dfs(final String cur, final String to, Stack<String> path) {
        dfsVisited.add(cur);
        path.push(cur);
        int curD = distanceMap.get(cur);

        if (cur.equals(to)) {
            List<String> res = new ArrayList<>();
            for (String p : path) res.add(p);
            result.add(res);
            path.pop();
            dfsVisited.remove(cur);
            return;
        }

        Set<String> adjset = adjMap.get(cur);
        if (adjset != null) {
            for (String adj : adjset) {
                /**
                 * Follow the shortest path (computed during BFS)
                 */
                if (distanceMap.get(adj) != null && distanceMap.get(adj) == curD+1) {
                    if (!dfsVisited.contains(adj)) {
                        dfs(adj, to, path);
                    }
                }
            }
        }
        path.pop();
        dfsVisited.remove(cur); // enable all-path traversal
    }
}
