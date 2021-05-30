package net.lc.unionfind;

/**
 * 323
 * Union-Find
 */
public class NumberOfConnectedComponents {
    public int countComponents(int n, int[][] edges) {
        /**
         * Root of a vertex
         */
        int[] roots = new int[n];
        int numConnected = n;
        // initially every vertex is an island
        for(int i = 0; i < n; i++) roots[i] = i;

        for(int[] e : edges) {
            int v1 = e[0];
            int v2 = e[1];

            int root1 = find(roots, v1);
            int root2 = find(roots, v2);
            if(root1 != root2) {
                /**
                 * currently belong to different island.
                 * Unite the islands.
                 */
                roots[root1] = root2;  // union
                numConnected--;
            }
        }
        return numConnected;
    }

    private int find(int[] roots, int id) {
        while(roots[id] != id) {
            id = roots[id];
        }
        return id;
    }
}
