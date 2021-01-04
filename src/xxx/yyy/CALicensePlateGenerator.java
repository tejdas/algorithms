package xxx.yyy;

import java.util.Random;

public class CALicensePlateGenerator {
    private static final Random r = new Random();
    private static final char[] CHARS =
        {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
    private static final char[] INTS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};

    public static String generateCALicensePlate() {
        StringBuilder lp = new StringBuilder();
        lp.append(INTS[r.nextInt(10)]);
        lp.append(CHARS[r.nextInt(26)]);
        lp.append(CHARS[r.nextInt(26)]);
        lp.append(CHARS[r.nextInt(26)]);
        lp.append(INTS[r.nextInt(10)]);
        lp.append(INTS[r.nextInt(10)]);
        lp.append(INTS[r.nextInt(10)]);
        return lp.toString();
    }
    public static void main(String[] args) {
        for (int i = 0; i < 10; i++)
            System.out.println(generateCALicensePlate());
    }

}
