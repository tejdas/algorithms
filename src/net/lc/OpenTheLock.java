package net.lc;

import java.util.*;

/**
 * 752
 * https://leetcode.com/problems/open-the-lock/submissions/
 * BFS
 */
public class OpenTheLock {
    private final Set<String> deadendSet = new HashSet<>();
    private final Map<String, List<String>> neighborsMap = new HashMap<>();
    public int openLock(String[] deadends, String target) {
        for (String s : deadends) deadendSet.add(s);

        if (deadendSet.contains("0000")) return -1;

        Set<String> visited = new HashSet<>();

        Queue<String> queue = new LinkedList<>();

        queue.add("0000");

        Map<String, Integer> distanceMap = new HashMap<>();
        distanceMap.put("0000", 0);

        while (!queue.isEmpty()) {
            String curNode = queue.remove();

            if (visited.contains(curNode)) continue;
            visited.add(curNode);

            int curD = distanceMap.get(curNode);

            List<String> neighbors = buildNeighbors(curNode);
            for (String s : neighbors) {
                if (distanceMap.containsKey(s)) {
                    int neighD = distanceMap.get(s);
                    if (neighD > curD+1) {
                        distanceMap.put(s, curD+1);
                    }
                } else {
                    distanceMap.put(s, curD+1);
                }

                if (s.equals(target)) return distanceMap.get(s);

                if (!visited.contains(s)) queue.add(s);
            }
        }
        return -1;
    }

    private List<String> buildNeighbors(String curNode) {
        if (neighborsMap.containsKey(curNode)) {
            return neighborsMap.get(curNode);
        }
        List<String> neighbors = new ArrayList<>();

        char[] array = curNode.toCharArray();
        for (int i = 0; i < array.length; i++) {
            char c = array[i];
            int val = Character.digit(c, 10);

            if (val == 0) {
                {
                    char cr = Character.forDigit(val + 1, 10);
                    array[i] = cr;
                    String s = new String(array);
                    if (!deadendSet.contains(s))
                        neighbors.add(s);
                }

                {
                    char cr = Character.forDigit(9, 10);
                    array[i] = cr;
                    String s = new String(array);
                    if (!deadendSet.contains(s))
                        neighbors.add(s);
                }
            } else {
                {
                    char cr = Character.forDigit((val + 1) % 10, 10);
                    array[i] = cr;
                    String s = new String(array);
                    if (!deadendSet.contains(s))
                        neighbors.add(s);
                }

                {
                    char cr = Character.forDigit((val - 1) % 10, 10);
                    array[i] = cr;
                    String s = new String(array);
                    if (!deadendSet.contains(s))
                        neighbors.add(s);
                }
            }

            array[i] = c;

        }
        neighborsMap.put(curNode, neighbors);
        return neighbors;
    }

    public static void main(String[] args) {
        {
            String[] deadends = { "0201", "0101", "0102", "1212", "2002" };
            System.out.println(new OpenTheLock().openLock(deadends, "0202"));
        }

        {
            String[] deadends = { "8888" };
            System.out.println(new OpenTheLock().openLock(deadends, "0009"));
        }

        {
            String[] deadends = { "8887","8889","8878","8898","8788","8988","7888","9888"};
            System.out.println(new OpenTheLock().openLock(deadends, "8888"));
        }

        {
            String[] deadends = { "0000"};
            System.out.println(new OpenTheLock().openLock(deadends, "8888"));
        }
    }
}
