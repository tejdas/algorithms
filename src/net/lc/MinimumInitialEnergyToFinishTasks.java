package net.lc;

import java.util.Arrays;

/**
 * 1665
 * Greedy
 * Priority-Queue (sort)
 */
public class MinimumInitialEnergyToFinishTasks {
    public int minimumEffort(int[][] tasks) {

        Arrays.sort(tasks, (o1, o2) -> {
            int odelta = o2[1] - o2[0];
            int delta = o1[1] - o1[0];
            if (odelta == delta) {
                return Integer.compare(o2[0], o1[0]);
            }
            return Integer.compare(odelta, delta);
        });

        int minEffort = 0;
        int residual = 0;
        for (int[] task : tasks) {

            if (residual < task[1]) {
                int toAdd = task[1] - residual;
                minEffort += toAdd;
                residual = residual + toAdd - task[0];
            } else {
                residual -= task[0];
            }
        }
        return minEffort;
    }

    public static void main(String[] args) {

        {
            //8
            int[][] tasks = { { 1, 2 }, { 2, 4 }, { 4, 8 } };
            System.out.println(new MinimumInitialEnergyToFinishTasks().minimumEffort(tasks));
        }
        {
            //32
            int[][] tasks = {{1,3},{2,4},{10,11},{10,12},{8,9}};
            System.out.println(new MinimumInitialEnergyToFinishTasks().minimumEffort(tasks));
        }
        {
            //27
            int[][] tasks = {{1,7},{2,8},{3,9},{4,10},{5,11},{6,12}};
            System.out.println(new MinimumInitialEnergyToFinishTasks().minimumEffort(tasks));
        }

        {
            //11
            int[][] tasks = {{1,2},{1,7},{2,3},{5,9},{2,2}};
            System.out.println(new MinimumInitialEnergyToFinishTasks().minimumEffort(tasks));
        }
    }
}
