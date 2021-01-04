package algo.dp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Select appointments with the constraint that two consecutive appointments
 * cannot be selected, and the highest total number of minutes can be covered.
 *
 * Dynamic programming
 */
public class AppointmentRequests {

    public static void main(String[] args) {
        computeAppointmentRequests(new int[] { 30, 15, 60, 75, 45, 15, 15, 45 });
    }

    static void computeAppointmentRequests(int[] appointments) {

        computeMaxAppointment(appointments);

        //int sum = getMaxAppointment(0, appointments);
        System.out.println(map.get(appointments.length-1));

        List<Integer> list = output.get(appointments.length-1);

        for (int i = list.size() - 1; i >= 0; i--) {
            System.out.print(list.get(i) + "  ");
        }
        System.out.println();
    }

    static Map<Integer, Integer> map = new HashMap<>();
    static Map<Integer, List<Integer>> output = new HashMap<>();

    static int getMaxAppointment(int index, int[] array) {

        if (map.containsKey(index)) {
            return map.get(index);
        }

        if (index == array.length - 1) {
            map.put(index, array[index]);
            output.put(index, new ArrayList<>(Arrays.asList(array[index])));
            return array[index];
        }

        if (index == array.length - 2) {
            int sum = Math.max(array[index], array[index + 1]);
            map.put(index, sum);
            output.put(index, new ArrayList<>(Arrays.asList(sum)));
            return sum;
        }

        int sum1 = getMaxAppointment(index + 1, array);
        int sum2 = array[index] + getMaxAppointment(index + 2, array);

        if (sum1 > sum2) {
            map.put(index, sum1);
            List<Integer> list = output.get(index + 1);
            List<Integer> newlist = new ArrayList<>(list);
            output.put(index, newlist);
            return sum1;
        } else {
            map.put(index, sum2);
            List<Integer> list = output.get(index + 2);
            List<Integer> newlist = new ArrayList<>(list);
            newlist.add(array[index]);
            output.put(index, newlist);
            return sum2;
        }
    }

    private static void computeMaxAppointment(int[] array) {
        map.put(0, array[0]);
        output.put(0, Arrays.asList(array[0]));

        map.put(1, array[1]);
        output.put(1, Arrays.asList(array[1]));

        for (int i = 2; i < array.length; i++) {
            int sum1 = array[i] + map.get(i-2);
            int sum2 = map.get(i-1);

            if (sum1 > sum2) {
                map.put(i, sum1);
                output.put(i, new ArrayList<>(output.get(i-2)));
                output.get(i).add(array[i]);
            } else {
                map.put(i, sum2);
                output.put(i, new ArrayList<>(output.get(i-1)));
            }
        }
    }
}
