package net.lc;

import java.util.ArrayList;
import java.util.List;

/**
 * 849
 * Array
 */
public class MaxDistanceToClosest {
    public int maxDistToClosest11(int[] seats) {
        List<Integer> filledSeats = new ArrayList<>();

        for (int i = 0; i < seats.length; i++) {
            if (seats[i] == 1) filledSeats.add(i);
        }

        int closestDistance = 0;

        if (filledSeats.get(0) > 0) {
            closestDistance = Math.max(closestDistance, filledSeats.get(0));
        }

        if (filledSeats.get(filledSeats.size()-1) < seats.length) {
            closestDistance = Math.max(closestDistance, seats.length - 1 - filledSeats.get(filledSeats.size()-1));
        }

        for (int i = 1; i < filledSeats.size(); i++) {
            int dist = filledSeats.get(i) - filledSeats.get(i-1);
            closestDistance = Math.max(closestDistance, dist/2);
        }
        return closestDistance;
    }

    public int maxDistToClosest(int[] seats) {
        int closestDistance = 0;

        int lastFilled = -1;
        for (int i = 0; i < seats.length; i++) {
            if (seats[i] == 1) {
                if (lastFilled == -1) {
                    closestDistance = Math.max(closestDistance, i);
                } else {
                    int dist = i - lastFilled;
                    closestDistance = Math.max(closestDistance, dist/2);
                }
                lastFilled = i;
            }
        }

        closestDistance = Math.max(closestDistance, seats.length-1-lastFilled);
        return closestDistance;
    }

    public static void main(String[] args) {
        {
            int[] seats = { 1, 0, 0, 0, 1, 0, 1 };
            System.out.println(new MaxDistanceToClosest().maxDistToClosest(seats));
        }
        {
            int[] seats = { 1,0,0,0 };
            System.out.println(new MaxDistanceToClosest().maxDistToClosest(seats));
        }
        {
            int[] seats = { 0,1};
            System.out.println(new MaxDistanceToClosest().maxDistToClosest(seats));
        }

        {
            int[] seats = { 0,0,0,0,1,0,0,0,1,0,0,1,0,0,0,0,0,0 };
            System.out.println(new MaxDistanceToClosest().maxDistToClosest(seats));
        }
    }
}
