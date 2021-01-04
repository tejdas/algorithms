package net.lc;

public class PathWithMaxMinValue {
    int[][] array;
    int rows;
    int cols;
    boolean[][] visited;
    int maxValue = Integer.MIN_VALUE;

    static int[][] dir = {{0,1}, {0,-1}, {1,0}, {-1,0}};

    int count = 0;

    public int maximumMinimumPath(int[][] array) {
        this.array = array;
        rows = array.length;
        cols = array[0].length;

        System.out.println("rows: " + rows + " cols: " + cols);

        visited = new boolean[rows][cols];
        dfs(new int[]{0,0}, Integer.MAX_VALUE);
        System.out.println("count:" + count);
        return maxValue;
    }

    private void dfs(int[] cur, int minval) {
        int row = cur[0];
        int col = cur[1];
        System.out.println("dfs: " + row + "  :  " + col);

        count++;
        //if (count >= 50) return;
        int newMinVal = Math.min(minval, array[row][col]);
        if (row == rows-1 && col == cols-1) {
            System.out.println("htting terminal");
            maxValue = Math.max(maxValue, newMinVal);
            return;
        }
        if (visited[row][col]) return;

        visited[row][col] = true;

        for (int[] adj : dir) {
            int adjx = row+adj[0];
            int adjy = col+adj[1];

            if (adjx >= 0 && adjx < rows && adjy >= 0 && adjy < cols) {
                if (!visited[adjx][adjy]) {
                    dfs(new int[] {adjx, adjy}, newMinVal);
                }
            }
        }
        visited[row][col] = false;
    }

    public static void main(String[] args) {

        {
            int[][] array = { { 5, 4, 5 }, { 1, 2, 6 }, { 7, 4, 6 } };
            System.out.println(new PathWithMaxMinValue().maximumMinimumPath(array));
        }
        /*
        {
            int[][] array = {{2,2,1,2,2,2},{1,2,2,2,1,2}};
            System.out.println(new PathWithMaxMinValue().maximumMinimumPath(array));
        }

        {
            int[][] array = {{3,4,6,3,4},{0,2,1,1,7},{8,8,3,2,7},{3,2,4,9,8},{4,1,2,0,0},{4,6,5,4,3}};
            System.out.println(new PathWithMaxMinValue().maximumMinimumPath(array));
        }


        {
            int[][] array = {{0,0,0,0,0,1,0},{0,1,0,1,1,0,1},{0,1,1,1,1,0,0},{0,1,0,0,0,0,0},{1,0,0,0,0,0,0},{1,0,0,0,0,0,1}};
            System.out.println(new PathWithMaxMinValue().maximumMinimumPath(array));
        }
        */
    }
}
