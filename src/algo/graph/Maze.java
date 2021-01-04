package algo.graph;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Stack;

import xxx.yyy.MatrixRotator;

public class Maze {

    static class Pair {

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + x;
            result = prime * result + y;
            return result;
        }

        @Override
        public boolean equals(final Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            final Pair other = (Pair) obj;
            if (x != other.x)
                return false;
            if (y != other.y)
                return false;
            return true;
        }

        @Override
        public String toString() {
            return "[" + x + ", " + y + "]";
        }
        int x;
        int y;
        Pair(final int x, final int y) {
            this.x = x;
            this.y = y;
        }
    }

    private static final int[][] maze = {{1, 0, 0, 0}, {1, 0, 1, 0}, {0, 1, 0, 1}, {1, 0, 1, 1}};
    //private static final int[][] maze = {{1, 0, 0}, {0, 1, 0}, {0, 0, 1}};

    static void fillMaze() {
        MatrixRotator.printMatrix(maze,  maze.length);
    }

    public static void main1(final String[] args) {
        fillMaze();
        findAllRoutes();
    }

    public static void main(final String[] args) {
        fillMaze();
        findRouteBFS();
    }

    static void findRoute() {
        final boolean[][] visited = new boolean[maze.length][maze.length];

        for (int i = 0; i < visited.length; i++) {
            Arrays.fill(visited[i], false);
        }

        final Stack<Pair> path = new Stack<>();
        final boolean flag = findRoute(0, 0, visited, path);
        if (flag) {
            for (final Pair coord : path) {
                System.out.print(coord.toString() + " ");
            }
            System.out.println();
        }
    }

    static boolean findRoute(final int x, final int y, final boolean[][] visited, final Stack<Pair> path) {
        visited[x][y] = true;
        path.push(new Pair(x, y));

        for (int i = x-1; i <= x+1; i++) {
            if (i < 0 || i >= maze.length) {
                continue;
            }
            for (int j = y-1; j <= y+1; j++) {
                if (j < 0 || j >= maze.length || (j==y && i==x)) {
                    continue;
                }

                if (maze[i][j] == 0) {
                    continue;
                }

                if (visited[i][j]) {
                    continue;
                }

                if (i == maze.length-1 && j == maze.length-1) {
                    path.push(new Pair(i, j));
                    return true;
                }

                if (findRoute(i, j, visited, path)) {
                    return true;
                }
            }
        }
        path.pop();
        return false;
    }

    static void findRouteBFS() {
        final boolean[][] visited = new boolean[maze.length][maze.length];

        for (int i = 0; i < visited.length; i++) {
            Arrays.fill(visited[i], false);
        }

        final Map<Pair, Pair> path = new HashMap<>();

        final Queue<Pair> q = new LinkedList<>();
        q.add(new Pair(0, 0));

        while (!q.isEmpty()) {
            final Pair node = q.poll();

            final int x = node.x;
            final int y = node.y;

            if (visited[x][y]) {
                continue;
            }

            visited[x][y] = true;

            for (int i = x-1; i <= x+1; i++) {
                if (i < 0 || i >= maze.length) {
                    continue;
                }
                for (int j = y-1; j <= y+1; j++) {
                    if (j < 0 || j >= maze.length || (j==y && i==x)) {
                        continue;
                    }

                    if (maze[i][j] == 0) {
                        continue;
                    }

                    if (visited[i][j]) {
                        continue;
                    }

                    Pair p = new Pair(i, j);
                    path.put(p, node);

                    if (i == maze.length-1 && j == maze.length-1) {
                        while (true) {
                            System.out.print(p.toString() + " ");
                            p = path.get(p);
                            if (p == null) {
                                return;
                            }
                        }
                    } else {
                        q.add(p);
                    }
                }
            }
        }
    }

    static void findAllRoutes() {
        final boolean[][] visited = new boolean[maze.length][maze.length];

        for (int i = 0; i < visited.length; i++) {
            Arrays.fill(visited[i], false);
        }

        final Stack<Pair> path = new Stack<>();
        findAllRoutes(0, 0, visited, path);

    }

    static void findAllRoutes(final int x, final int y, final boolean[][] visited, final Stack<Pair> path) {
        visited[x][y] = true;
        path.push(new Pair(x, y));

        for (int i = x-1; i <= x+1; i++) {
            if (i < 0 || i >= maze.length) {
                continue;
            }
            for (int j = y-1; j <= y+1; j++) {
                if (j < 0 || j >= maze.length || (j==y && i==x)) {
                    continue;
                }

                if (maze[i][j] == 0) {
                    continue;
                }

                if (visited[i][j]) {
                    continue;
                }

                if (i == maze.length-1 && j == maze.length-1) {
                    path.push(new Pair(i, j));
                    for (final Pair coord : path) {
                        System.out.print(coord.toString() + " ");
                    }
                    System.out.println();
                    path.pop();
                } else {
                    findAllRoutes(i, j, visited, path);
                }
            }
        }
        path.pop();
        visited[x][y] = false;
        return;
    }
}
