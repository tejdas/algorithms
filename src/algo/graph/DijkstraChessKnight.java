package algo.graph;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Scanner;

/**
 * Dijkstra BFS algorithm to find shortest path from a startPos to endPos
 * Weight of an Edge is 1
 */
public class DijkstraChessKnight {

    static int dimension = 8;
    private static CPWeight[][] chessBoard = new CPWeight[dimension][dimension];

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        try {
            String str1 = in.nextLine();
            Pair startPos = parseInput(str1);

            String str2 = in.nextLine();
            Pair endPos = parseInput(str2);

            if (startPos == null || endPos == null) {
                System.out.println(0);
                return;
            }

            findSP(startPos);

            System.out.println(chessBoard[endPos.x][endPos.y].weight);
        } finally {
            in.close();
        }
    }

    static Pair parseInput(String str) {
        String g = str.substring(1, str.length() - 1);
        String[] arr = g.split(",");
        if (arr == null || arr.length > 2) {
            return null;
        }

        int x = Integer.valueOf(arr[0]);
        int y = Integer.valueOf(arr[1]);

        if (x < 0 || y < 0 || x >= dimension || y >= dimension) {
            return null;
        }

        return new Pair(x, y);

    }

    private static class CPWeight implements Comparable<CPWeight> {

        public CPWeight(int xPos, int yPos, int weight) {
            super();
            this.xPos = xPos;
            this.yPos = yPos;
            this.weight = weight;
        }

        final int xPos;
        final int yPos;
        int weight = 0;

        @Override
        public int compareTo(CPWeight o) {
            return Integer.compare(this.weight, o.weight);
        }
    }

    private static void findSP(Pair startPos) {

        int x = startPos.x;
        int y = startPos.y;

        final boolean[][] visited = new boolean[dimension][dimension];

        for (int i = 0; i < chessBoard.length; i++) {
            for (int j = 0; j < chessBoard.length; j++) {
                if (i == x && j == y) {
                    chessBoard[i][j] = new CPWeight(i, j, 0);
                } else {
                    chessBoard[i][j] = new CPWeight(i, j, Integer.MAX_VALUE);
                }

                visited[i][j] = false;
            }
        }

        final PriorityQueue<CPWeight> pq = new PriorityQueue<>();

        pq.add(chessBoard[x][y]);

        while (!pq.isEmpty()) {
            CPWeight cur = pq.poll();

            if (visited[cur.xPos][cur.yPos]) {
                continue;
            }

            visited[cur.xPos][cur.yPos] = true;

            List<Pair> adjNodes = getAdjacents(cur.xPos, cur.yPos);

            for (Pair adj : adjNodes) {
                CPWeight adjNode = chessBoard[adj.x][adj.y];

                if (adjNode.weight > cur.weight + 1) {
                    adjNode.weight = cur.weight + 1;
                }

                if (!visited[adj.x][adj.y]) {
                    pq.add(adjNode);
                }
            }
        }
    }

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
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            Pair other = (Pair) obj;
            if (x != other.x)
                return false;
            if (y != other.y)
                return false;
            return true;
        }

        public Pair(int x, int y) {
            super();
            this.x = x;
            this.y = y;
        }

        int x;
        int y;
    }

    static final Map<Pair, List<Pair>> adjacencyMap = new HashMap<>();

    private static List<Pair> getAdjacents(int x, int y) {
        Pair p = new Pair(x, y);
        if (adjacencyMap.containsKey(p)) {
            return adjacencyMap.get(p);
        }

        List<Pair> adj = populateEdges(x, y);
        adjacencyMap.put(p, adj);
        return adj;
    }

    static boolean isValidIndex(int x, int y) {
        return (x >= 0 && y >= 0 && x < dimension && y < dimension);
    }

    static List<Pair> populateEdges(int x, int y) {
        List<Pair> adj = new ArrayList<>();

        int i = 2;
        int j = 1;

        if (isValidIndex(x + i, y + j)) {
            adj.add(new Pair(x + i, y + j));
        }

        if (isValidIndex(x + i, y - j)) {
            adj.add(new Pair(x + i, y - j));
        }

        if (isValidIndex(x - i, y + j)) {
            adj.add(new Pair(x - i, y + j));
        }

        if (isValidIndex(x - i, y - j)) {
            adj.add(new Pair(x - i, y - j));
        }

        i = 1;
        j = 2;

        if (isValidIndex(x + i, y + j)) {
            adj.add(new Pair(x + i, y + j));
        }

        if (isValidIndex(x + i, y - j)) {
            adj.add(new Pair(x + i, y - j));
        }

        if (isValidIndex(x - i, y + j)) {
            adj.add(new Pair(x - i, y + j));
        }

        if (isValidIndex(x - i, y - j)) {
            adj.add(new Pair(x - i, y - j));
        }
        return adj;
    }

    static void printCB() {
        for (int i = 0; i < chessBoard.length; i++) {
            for (int j = 0; j < chessBoard.length; j++) {
                System.out.print(chessBoard[i][j].weight + "   ");
            }
            System.out.println();
        }
    }
}
