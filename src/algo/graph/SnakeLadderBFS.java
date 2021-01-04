package algo.graph;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public class SnakeLadderBFS {
    public static void main(String[] args) throws IOException {
        BufferedReader bfr = new BufferedReader(new InputStreamReader(System.in));

        int numTests = Integer.parseInt(bfr.readLine());

        for (int i = 0; i < numTests; i++) {
            adjMap.clear();
            ladderMap.clear();
            snakeMap.clear();

            int numLadders = Integer.parseInt(bfr.readLine());
            for (int j = 0; j < numLadders; j++) {
                String[] temp = bfr.readLine().split(" ");
                int key = Integer.parseInt(temp[0]);
                int val = Integer.parseInt(temp[1]);
                ladderMap.put(key, val);
            }

            int numSnakes = Integer.parseInt(bfr.readLine());
            for (int j = 0; j < numSnakes; j++) {
                String[] temp = bfr.readLine().split(" ");
                int key = Integer.parseInt(temp[0]);
                int val = Integer.parseInt(temp[1]);
                snakeMap.put(key, val);
            }

            constructGraph();
            int minHop = bfs();
            System.out.println(minHop);
        }
        bfr.close();
    }

    static Map<Integer, List<Integer>> adjMap = new HashMap<>();
    static Map<Integer, Integer> ladderMap = new HashMap<>();
    static Map<Integer, Integer> snakeMap = new HashMap<>();

    static void constructGraph() {
        for (int i = 1; i <= 99; i++) {

            if (ladderMap.containsKey(i) || snakeMap.containsKey(i)) {
                continue;
            }

            List<Integer> adjList = new ArrayList<>();
            adjMap.put(i, adjList);
            for (int j = 6; j >= 1; j--) {
                int adjNode = i + j;
                if (adjNode > 100) {
                    continue;
                }

                if (ladderMap.containsKey(adjNode)) {
                    adjList.add(ladderMap.get(adjNode));
                } else if (snakeMap.containsKey(adjNode)) {
                    adjList.add(snakeMap.get(adjNode));
                } else {
                    adjList.add(adjNode);
                }
            }
        }
    }

    static int bfs() {
        int source = 1;

        Set<Integer> visited = new HashSet<>();
        Queue<Integer> queue = new LinkedList<>();
        queue.add(source);

        Map<Integer, List<Integer>> edgeTo = new HashMap<>();

        for (int i = 1; i <= 100; i++) {
            edgeTo.put(i, new ArrayList<>());
        }

        edgeTo.get(source).add(source);

        while (!queue.isEmpty()) {
            int curNode = queue.poll();
            if (visited.contains(curNode)) {
                continue;
            }

            visited.add(curNode);

            List<Integer> adjList = adjMap.get(curNode);
            if (adjList == null || adjList.isEmpty()) {
                continue;
            }

            for (int adjNode : adjList) {
                if (!visited.contains(adjNode)) {
                    if (adjNode == 100) {
                        return edgeTo.get(curNode).size();
                    }

                    edgeTo.get(adjNode).clear();
                    edgeTo.get(adjNode).addAll(edgeTo.get(curNode));
                    edgeTo.get(adjNode).add(adjNode);
                    queue.add(adjNode);
                }
            }
        }

        return -1;
    }
}
