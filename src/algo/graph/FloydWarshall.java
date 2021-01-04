package algo.graph;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class FloydWarshall {

    public static void main(String[] args) throws IOException {
        BufferedReader bfr = new BufferedReader(new InputStreamReader(System.in));
        String[] temp = bfr.readLine().split(" ");
        int N = Integer.parseInt(temp[0]);
        int M = Integer.parseInt(temp[1]);

        if (N < 2 || N > 400 || M < 1 || M > 90000) {
            return;
        }

        int[][] graph = new int[N + 1][N + 1];

        for (int i = 0; i < N + 1; i++) {
            for (int j = 0; j < N + 1; j++) {
                if (i == j) {
                    graph[i][j] = 0;
                } else
                    graph[i][j] = Integer.MAX_VALUE;
            }
        }

        for (int i = 0; i < M; i++) {

            temp = bfr.readLine().split(" ");
            int v1 = Integer.parseInt(temp[0]);
            int v2 = Integer.parseInt(temp[1]);
            int weight = Integer.parseInt(temp[2]);

            if (weight < 0 || weight > 350 || v1 < 1 || v2 < 1 || v1 > N || v2 > N) {
                return;
            }

            graph[v1][v2] = weight;
        }

        String s = bfr.readLine();
        int Q = Integer.parseInt(s);

        if (Q < 1 || Q > 100000) {
            return;
        }

        for (int k = 1; k < N + 1; k++) {
            for (int i = 1; i < N + 1; i++) {
                for (int j = 1; j < N + 1; j++) {
                    if (graph[i][k] == Integer.MAX_VALUE || graph[k][j] == Integer.MAX_VALUE)
                        continue;

                    if (graph[i][j] > graph[i][k] + graph[k][j])
                        graph[i][j] = graph[i][k] + graph[k][j];
                }
            }
        }

        for (int i = 0; i < N + 1; i++) {
            for (int j = 0; j < N + 1; j++) {
                if (graph[i][j] == Integer.MAX_VALUE)
                    graph[i][j] = -1;
            }
        }

        List<Integer> output = new ArrayList<>();

        for (int i = 0; i < Q; i++) {
            temp = bfr.readLine().split(" ");
            int v1 = Integer.parseInt(temp[0]);
            int v2 = Integer.parseInt(temp[1]);

            output.add(graph[v1][v2]);
        }

        for (int val : output) {
            System.out.println(val);
        }

        bfr.close();
    }
}
