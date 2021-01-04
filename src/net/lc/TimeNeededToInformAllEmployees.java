package net.lc;

import java.util.Arrays;

/**
 * 1376
 * Recursion
 */
public class TimeNeededToInformAllEmployees {
    int maxTime = 0;
    int[] timeNeeded;

    public int numOfMinutes(int n, int headID, int[] manager, int[] informTime) {
        timeNeeded = new int[n];
        Arrays.fill(timeNeeded, -1);
        timeNeeded[headID] = 0;

        for (int i = 0; i < manager.length; i++) {
            getTime(i, manager, informTime);
        }
        return maxTime;
    }

    private int getTime(int employeeIndex, int[] manager, int[] informTime) {
        if (timeNeeded[employeeIndex] != -1) {
            return timeNeeded[employeeIndex];
        }

        int managerIndex = manager[employeeIndex];
        int mgrTime = getTime(managerIndex, manager, informTime);

        int employeeTime = mgrTime + informTime[managerIndex];
        timeNeeded[employeeIndex] = employeeTime;

        if (informTime[employeeIndex] == 0) {
            // no subordinates
            maxTime = Math.max(maxTime, employeeTime);
        }
        return employeeTime;
    }

    public static void main(String[] args) {
        System.out.println(new TimeNeededToInformAllEmployees().numOfMinutes(1, 0, new int[] {-1}, new int[] {0}));

        System.out.println(new TimeNeededToInformAllEmployees().numOfMinutes(6, 2,
            new int[] {2,2,-1,2,2,2},
            new int[] {0,0,1,0,0,0}));

        System.out.println(new TimeNeededToInformAllEmployees().numOfMinutes(7, 6,
            new int[] {1,2,3,4,5,6,-1},
            new int[] {0,6,5,4,3,2,1}));

        System.out.println(new TimeNeededToInformAllEmployees().numOfMinutes(15, 0,
            new int[] {-1,0,0,1,1,2,2,3,3,4,4,5,5,6,6},
            new int[] {1,1,1,1,1,1,1,0,0,0,0,0,0,0,0}));
    }
}
