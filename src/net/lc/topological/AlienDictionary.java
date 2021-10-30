package net.lc.topological;

import java.util.*;

/**
 * 269
 * Topological sort
 */
public class AlienDictionary {
    static class CycleDetectedException extends RuntimeException {

    }

    private static int maxLen(String[] words) {
        int maxLen = 0;
        for (String word : words) {
            if (word.length() > maxLen) {
                maxLen = word.length();
            }
        }

        return maxLen;
    }

    static class GNode {
        Character val;
        Set<Character> adjNodes = new HashSet<>();

        public GNode(Character val) {
            this.val = val;
        }
    }

    static final Map<Character, GNode> map = new HashMap<>();

    static void processColRange(char[][] matrix, int col, int start, int end) {

        //System.out.println("processColRange: col: " + col + "  start: " + start + "  end: " + end);
        LinkedHashSet<Character> set = new LinkedHashSet<>();
        List<Character> list = new ArrayList<>();

        for (int i = start; i <= end; i++) {
            if (col < matrix[i].length) {
                char c = matrix[i][col];

                if (set.contains(c)) {
                    char lastchar = list.get(list.size()-1);

                    if (lastchar == c) {
                        // we are good
                        list.add(c);
                    } else {
                        throw new CycleDetectedException();
                    }
                } else {
                    set.add(c);
                    list.add(c);
                }
            }
        }

        if (set.size() == 0) {
            return;
        }

        //System.out.println(Arrays.toString(set.toArray()));

        final Character[] carray = set.toArray(new Character[set.size()]);

        map.computeIfAbsent(carray[0], k -> new GNode(carray[0]));


        for (int i = 1; i < carray.length; i++) {
            final int idx = i;
            map.computeIfAbsent(carray[i], k -> new GNode(carray[idx]));
            map.get(carray[i-1]).adjNodes.add(carray[i]);
        }
    }

    static void processColN(char[][] matrix, int col) {

        int start = -1;
        int end = -1;

        for (int cur = 0; cur < matrix.length; cur++) {
            if (col >= matrix[cur].length) {
                if (start != -1) {
                    processColRange(matrix, col+1, start, end);
                }
                start = -1;
                end = -1;
            } else {
                if (start == -1) {
                    start = cur;
                    end = cur;
                } else {
                    if (matrix[cur][col] == matrix[start][col]) {
                        end++;
                    } else {
                        processColRange(matrix, col+1, start, end);
                        start = cur;
                        end = cur;
                    }
                }
            }
        }

        if (start != -1) {
            processColRange(matrix, col+1, start, matrix.length-1);
        }
    }

    static Set<Character> visited = new HashSet<>();
    static Set<Character> getStartNodes() {
        Set<Character> adjNodes = new HashSet<>();

        for (GNode gNode : map.values()) {
            adjNodes.addAll(gNode.adjNodes);
        }

        Set<Character> allNodes = new HashSet<>(map.keySet());
        allNodes.removeAll(adjNodes);

        return allNodes;
    }

    static List<Character> dfs(Set<Character> startNodes) {
        Stack<Character> topological = new Stack<>();

        for (char startNode : startNodes) {
            dfsRecurse(startNode, topological);
        }

        List<Character> topList = new ArrayList<>();
        while (!topological.isEmpty()) {
            topList.add(topological.pop());
        }

        return topList;
    }

    static void dfsRecurse(Character cur, Stack<Character> topologicalStack) {
        visited.add(cur);

        GNode curNode = map.get(cur);
        for (Character c : curNode.adjNodes) {
            if (!visited.contains(c)) {
                dfsRecurse(c, topologicalStack);
            }
        }

        topologicalStack.push(cur);
    }

    public String alienOrder(String[] words) {
        map.clear();
        visited.clear();
        if (words == null || words.length == 0) {
            return "";
        }

        char[][] matrix = new char[words.length][];

        for (int i = 0; i < words.length; i++) {
            matrix[i] = words[i].toCharArray();
        }

        int maxLen = maxLen(words);

        int rows = words.length;

        try {
            processColRange(matrix, 0, 0, rows - 1);

            for (int col = 0; col < maxLen - 1; col++) {
                processColN(matrix, col);
            }
        } catch (CycleDetectedException ex) {
            return "";
        }

        Set<Character> startNodes = getStartNodes();
        if (startNodes.isEmpty()) {
            return "";
        }

        List<Character> topList = dfs(startNodes);
        StringBuilder sb = new StringBuilder();
        for (Character c : topList) {
            sb.append(c);
        }

        return sb.toString();
    }
}
