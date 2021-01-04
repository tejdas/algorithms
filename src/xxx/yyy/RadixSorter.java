package xxx.yyy;

import java.util.Arrays;

public class RadixSorter {
    public static void radixSort(String[] licensePlates, int plateLen) {
        int R = 256;
        int counts[] = new int[R+1];

        String[] aux = new String[licensePlates.length];

        for (int i = plateLen-1; i >= 0; i--) {
            Arrays.fill(counts, 0);
            for (String s : licensePlates) {
                counts[s.charAt(i) + 1]++;
            }

            for (int j = 0; j < R; j++) {
                counts[j+1] += counts[j];
            }

            for (int k = 0; k < licensePlates.length; k++) {
                int c = licensePlates[k].charAt(i);
                aux[counts[c]] = licensePlates[k];
                counts[c] += 1;
            }
            for (int l = 0; l < licensePlates.length; l++) {
                licensePlates[l] = aux[l];
            }
        }
    }
    public static void main(String[] args) {
        int numPlates = 20;
        String[] licensePlates = new String[numPlates];
        for (int i = 0; i < numPlates; i++) {
            licensePlates[i] = CALicensePlateGenerator.generateCALicensePlate();
        }

        radixSort(licensePlates, 7);
        for (String s : licensePlates) {
            System.out.println(s);
        }
    }
}
