package net.lc;

import java.util.*;

public class LargestNumber {

    static class Num {
        private int[] array;
        private int number;
        private String s;
        public Num(int number) {
            this.number = number;
            s = String.valueOf(number);
            array = new int[s.length()];
            int val = number;
            int index = array.length-1;
            while (val > 0) {
                int rem = val % 10;
                array[index--] = rem;
                val = val / 10;
            }
        }
    }

    public String largestNumber(int[] nums) {

        List<Num> numList = new ArrayList<>();
        for (int num : nums) {
            numList.add(new Num(num));
        }

        Collections.sort(numList, (o1, o) -> {
            if (o.array.length == o1.array.length) {
                return Integer.compare(o.number, o1.number);
            }

            int len = Math.min(o1.array.length, o.array.length);

            int biggestNum = Integer.MIN_VALUE;
            int smallestNum = Integer.MAX_VALUE;
            for (int i = 0; i < len; i++) {
                int val = o1.array[i];
                int oval = o.array[i];

                if (oval != val) {
                    return Integer.compare(oval, val);
                } else {
                    biggestNum = Math.max(biggestNum, oval);

                    smallestNum = Math.min(smallestNum, oval);
                }
            }

            int[] checkArray = (o.array.length > o1.array.length)? o.array : o1.array;

            int biggestNumExtra = Integer.MIN_VALUE;
            int smallestNumExtra = Integer.MAX_VALUE;
            for (int i = len; i < checkArray.length; i++) {
                biggestNumExtra = Math.max(biggestNumExtra, checkArray[i]);

                smallestNumExtra = Math.min(smallestNumExtra, checkArray[i]);
            }

            if (biggestNum > biggestNumExtra) {
                return Integer.compare(o1.array.length, o.array.length);
            } else if (biggestNum < biggestNumExtra) {
                return Integer.compare(o.array.length, o1.array.length);
            } else {
                //System.out.println("hitting this case");

                if (smallestNumExtra < smallestNum) {
                    return Integer.compare(o1.array.length, o.array.length);
                } else {
                    return Integer.compare(o.array.length, o1.array.length);
                }

                //return Integer.compare(o1.array.length, o.array.length);
            }
        });

        Iterator<Num> it = numList.iterator();
        StringBuilder sb = new StringBuilder();
        while (it.hasNext()) {
            String s = it.next().s;
            //System.out.println(s);
            sb.append(s);
        }

        boolean allzeros = true;
        for (char c : sb.toString().toCharArray()) {
            if (c != '0') allzeros = false;
        }

        if (!allzeros) return sb.toString(); else return "0";
    }

    public static void main(String[] args) {
        /*
        {
            int[] nums = { 3, 30, 34, 5, 9, 56 };

            System.out.println(new LargestNumber().largestNumber(nums));
        }

        {
            int[] nums = { 27, 272 };

            System.out.println(new LargestNumber().largestNumber(nums));
        }

        {
            int[] nums = {111311, 1113 };

            System.out.println(new LargestNumber().largestNumber(nums));
        }

        {
            int[] nums = {8308,8308,830};

            System.out.println(new LargestNumber().largestNumber(nums));
        }

        {
            int[] nums = {0, 0};

            System.out.println(new LargestNumber().largestNumber(nums));
        }

        {
            int[] nums = {1440,7548,4240,6616,733,4712,883,8,9576};

            System.out.println(new LargestNumber().largestNumber(nums));
        }
*/
        {
            int[] nums = {43243, 432};

            System.out.println(new LargestNumber().largestNumber(nums));
        }

        {
            int[] nums = {432, 43243};

            System.out.println(new LargestNumber().largestNumber(nums));
        }

        {
            int[] nums = {3,30,34,5,9};

            System.out.println(new LargestNumber().largestNumber(nums));
        }

        {
            int[] nums = {3,30};

            System.out.println(new LargestNumber().largestNumber(nums));
        }
    }
}
