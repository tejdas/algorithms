package net.lc;

import java.util.*;

/**
 * https://leetcode.com/problems/shortest-path-with-alternating-colors/submissions/
 * BFS
 */
public class ShortestPathAlternateColors {
    public int[] shortestAlternatingPaths(int n, int[][] red_edges, int[][] blue_edges) {
        buildDG(n, red_edges, blue_edges);
        return bfs(n);
    }

    static class Edge {
        int to;
        int color; //unknown = -1, blue = 0, red = 1, both = 2;

        public Edge(int to, int color) {
            this.to = to;
            this.color = color;
        }
    }

    private final Map<Integer, Map<Integer, Integer>> adjMap = new HashMap<>();
    private int[] selfEdges;
    private void buildDG(int n, int[][] red_edges, int[][] blue_edges) {
        selfEdges = new int[n];
        Arrays.fill(selfEdges, -1);
        for (int i = 0; i < n; i++) {
            adjMap.put(i, new HashMap<>());
        }

        for (int[] blueEdge : blue_edges) {
            int from = blueEdge[0];
            int to = blueEdge[1];

            if (from == to) {
                if (selfEdges[from] == -1) selfEdges[from] = 0;
                else selfEdges[from] = 2;
                continue;
            }

            Map<Integer, Integer> edgeMap = adjMap.get(from);
            if (!edgeMap.containsKey(to)) {
                edgeMap.put(to, 0);
            } else {
                int color = edgeMap.get(to);
                if (color == 2) continue;
                else if (color == 1) edgeMap.put(to, 2);
            }
        }

        for (int[] redEdge : red_edges) {
            int from = redEdge[0];
            int to = redEdge[1];

            if (from == to) {
                if (selfEdges[from] == -1) selfEdges[from] = 1;
                else selfEdges[from] = 2;
                continue;
            }

            Map<Integer, Integer> edgeMap = adjMap.get(from);
            if (!edgeMap.containsKey(to)) {
                edgeMap.put(to, 1);
            } else {
                int color = edgeMap.get(to);
                if (color == 2) continue;
                else if (color == 0) edgeMap.put(to, 2);
            }
        }
    }

    static class Pair {
        int from;
        int node;
        int incomingColor;

        public Pair(int from, int node, int incomingColor) {
            this.from = from;
            this.node = node;
            this.incomingColor = incomingColor;
        }
    }
    private int[] bfs(int n) {
        boolean[] rvisited = new boolean[n];
        Arrays.fill(rvisited, false);

        boolean[] bvisited = new boolean[n];
        Arrays.fill(bvisited, false);

        int[] blueDistance = new int[n];
        Arrays.fill(blueDistance, Integer.MAX_VALUE);
        blueDistance[0] = 0;

        int[] redDistance = new int[n];
        Arrays.fill(redDistance, Integer.MAX_VALUE);
        redDistance[0] = 0;

        Queue<Pair> queue = new LinkedList<>();
        queue.add(new Pair(-1, 0, -1));

        while (!queue.isEmpty()) {
            Pair p = queue.remove();

            if (p.node == 0) {
                if (rvisited[p.node]) continue;
                rvisited[p.node] = true;
                bvisited[p.node] = true;
            } else {
                if (p.incomingColor == 0) {
                    if (bvisited[p.node]) continue;
                    bvisited[p.node] = true;
                } else {
                    if (rvisited[p.node]) continue;
                    rvisited[p.node] = true;
                }
            }

            Map<Integer, Integer> edgeMap = adjMap.get(p.node);

            for (Map.Entry<Integer, Integer> entry : edgeMap.entrySet()) {
                int to = entry.getKey();
                int color = entry.getValue();

                if (p.incomingColor == 0) {
                    if (color == 1 || color == 2) {
                        if (blueDistance[p.node] + 1 < redDistance[to]) {
                            redDistance[to] = blueDistance[p.node] + 1;
                        }

                        if (!rvisited[to])
                            queue.add(new Pair(p.node, to, 1));
                    } else {
                        if (selfEdges[p.node] == 1 || selfEdges[p.node] == 2) {
                            // incoming blue, self-loop red, outgoing blue
                            if (color == 0 || color == 2) {
                                if (blueDistance[p.node] + 2 < blueDistance[to]) {
                                    blueDistance[to] = blueDistance[p.node] + 2;
                                }

                                if (!bvisited[to])
                                    queue.add(new Pair(p.node, to, 0));
                            }
                        }
                    }
                } else if (p.incomingColor == 1) {
                    if (color == 0 || color == 2) {
                        if (redDistance[p.node] + 1 < blueDistance[to]) {
                            blueDistance[to] = redDistance[p.node] + 1;
                        }

                        if (!bvisited[to])
                            queue.add(new Pair(p.node, to, 0));
                    }  else {
                        if (selfEdges[p.node] == 0 || selfEdges[p.node] == 2) {
                            // incoming red, self-loop blue, outgoing red
                            if (color == 1 || color == 2) {
                                if (redDistance[p.node] + 2 < redDistance[to]) {
                                    redDistance[to] = redDistance[p.node] + 2;
                                }

                                if (!rvisited[to])
                                    queue.add(new Pair(p.node, to, 1));
                            }
                        }
                    }
                } else {
                    if (color == 0 || color == 2) {
                        if (redDistance[p.node] + 1 < blueDistance[to]) {
                            blueDistance[to] = redDistance[p.node] + 1;
                        }
                        if (!bvisited[to]) queue.add(new Pair(p.node, to, 0));
                    }

                    if (color == 1 || color == 2) {
                        if (blueDistance[p.node] + 1 < redDistance[to]) {
                            redDistance[to] = blueDistance[p.node] + 1;
                        }
                        if (!rvisited[to]) queue.add(new Pair(p.node, to, 1));
                    }
                }
            }
        }

        int[] result = new int[n];
        for (int i = 0; i < result.length; i++) {
            if (blueDistance[i] == Integer.MAX_VALUE && redDistance[i] == Integer.MAX_VALUE) {
                result[i] = -1;
            } else {
                result[i] = Math.min(blueDistance[i], redDistance[i]);
            }
        }
        return result;
    }

    public static void main(String[] args) {
        {
            int n = 3;
            int[][] re = { { 0, 1 } };
            int[][] be = { { 1, 2 } };

            //int[] res = new ShortestPathAlternateColors().shortestAlternatingPaths(n, re, be);
            //System.out.println(Arrays.toString(res));
        }

        {
            int n = 3;
            int[][] re = { { 0, 1 }, {0,2} };
            int[][] be = { { 1, 0 } };

            //int[] res = new ShortestPathAlternateColors().shortestAlternatingPaths(n, re, be);
            //System.out.println(Arrays.toString(res));
        }

        {
            int n = 3;
            int[][] re = { { 1,0 } };
            int[][] be = { { 2,1 } };

            //int[] res = new ShortestPathAlternateColors().shortestAlternatingPaths(n, re, be);
            //System.out.println(Arrays.toString(res));
        }

        {
            int n = 5;
            int[][] re = { { 0,1 },{1,2},{2,3},{3,4} };
            int[][] be = { { 1,2 },{2,3},{3,1} };

            //int[] res = new ShortestPathAlternateColors().shortestAlternatingPaths(n, re, be);
            //System.out.println(Arrays.toString(res));
        }

        {
            int n = 5;
            int[][] re = { {3,2},{4,1},{1,4},{2,4} };
            int[][] be = {{2,3},{0,4},{4,3},{4,4},{4,0},{1,0}};

            //int[] res = new ShortestPathAlternateColors().shortestAlternatingPaths(n, re, be);
            //System.out.println(Arrays.toString(res));
        }

        {
            int n = 5;
            int[][] re = {{2,2},{0,4},{4,2},{4,3},{2,4},{0,0},{0,1},{2,3},{1,3}};
            int[][] be = {{0,4},{1,0},{1,4},{0,0},{4,0}};

            //int[] res = new ShortestPathAlternateColors().shortestAlternatingPaths(n, re, be);
            //System.out.println(Arrays.toString(res));
        }

        {
            int n = 6;
            int[][] re =  {{4,1},{3,5},{5,2},{1,4},{4,2},{0,0},{2,0},{1,1}};
            int[][] be = {{5,5},{5,0},{4,4},{0,3},{1,0}};

            int[] res = new ShortestPathAlternateColors().shortestAlternatingPaths(n, re, be);
            System.out.println(Arrays.toString(res));
        }

        {
            int n = 6;
            int[][] re =  {{1,5},{2,2},{5,5},{3,0},{4,5},{2,4},{4,1},{1,0},{1,2},{5,2},{2,3},{0,1}};
            int[][] be =  {{4,4},{2,5},{1,1},{5,4},{3,3}};


            int[] res = new ShortestPathAlternateColors().shortestAlternatingPaths(n, re, be);
            System.out.println(Arrays.toString(res));
        }
    }
}
