package net.lc.greedy;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * https://leetcode.com/problems/cinema-seat-allocation/submissions/
 * Bit-manipulation
 * Greedy
 * 1386
 */
public class CinemaSeatAllocation {
    public int maxNumberOfFamilies(int n, int[][] reservedSeats) {

        short[] array = new short[10];
        short[] fliparray = new short[10];
        short val = 1;
        for (int index = 0; index < 10; index++) {
            short val2 = (short) (1023-val);
            array[index] = val2;
            fliparray[index] = val;
            val *= 2;
        }

        int leftBitMark = fliparray[1] | fliparray[2] | fliparray[3] | fliparray[4];
        int centerBitMark = fliparray[3] | fliparray[4] | fliparray[5] | fliparray[6];
        int rightBitMark = fliparray[5] | fliparray[6] | fliparray[7] | fliparray[8];

        int familyCount = 0;

        Map<Integer, Short> hall = new HashMap<>();

        for (int[] seat : reservedSeats) {
            int row = seat[0];
            int s = seat[1];

            short seating = hall.computeIfAbsent(row-1, k -> (short) 1023);
            seating = (short) (seating & array[s-1]);
            hall.put(row-1, seating);
        }

        for (short seatRow : hall.values()) {
            //tobits(seatRow);
            if (seatRow == 0) continue;

            boolean isLeft = (seatRow & leftBitMark) == leftBitMark;
            boolean isRight = (seatRow & rightBitMark) == rightBitMark;

            if (isLeft && isRight) {
                familyCount += 2;
            } else if (isLeft) {
                familyCount++;
            } else if (isRight) {
                familyCount++;
            } else {
                boolean isCenter = (seatRow & centerBitMark) == centerBitMark;
                if (isCenter) familyCount++;
            }
        }

        int fullSeats = n - hall.size();

        return familyCount + (fullSeats*2);
    }

    public static void main(String[] args) {
        {
            int n = 3;
            int[][] reservedSeats = { { 1, 2 }, { 1, 3 }, { 1, 8 }, { 2, 6 }, { 3, 1 }, { 3, 10 } };

            //System.out.println(new CinemaSeatAllocation().maxNumberOfFamilies(n, reservedSeats));
        }
        {
            int n = 2;
            int[][] reservedSeats = {{2,1},{1,8},{2,6}};

            //System.out.println(new CinemaSeatAllocation().maxNumberOfFamilies(n, reservedSeats));
        }
        {
            int n = 4;
            int[][] reservedSeats = {{4,3},{1,4},{4,6},{1,7}};

            System.out.println(new CinemaSeatAllocation().maxNumberOfFamilies(n, reservedSeats));
        }
    }



    private static void tobits(int val) {
        while (val > 0) {
            int rem = val % 2;
            val = val/2;
            System.out.print(rem + " ");
        }
        System.out.println();
    }

    public static void main2(String[] args) {

        int[] array = new int[10];
        int val = 1;
        for (int index = 0; index < 10; index++) {
            array[index] = val;
            //tobits(val);
            int val2 = 1023-val;
            tobits(val2);
            val *= 2;
        }

        int leftBitMark = array[1] | array[2] | array[3] | array[4];
        int centerBitMark = array[3] | array[4] | array[5] | array[6];
        int rightBitMark = array[5] | array[6] | array[7] | array[8];


        System.out.println(Arrays.toString(array));

        System.out.println("left: " + leftBitMark);
        System.out.println("center: " + centerBitMark);
        System.out.println("right: " + rightBitMark);

        tobits(1023);

        //1,000,000,000
    }
}
