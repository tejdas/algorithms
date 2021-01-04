package xxx.yyy;


public class LookAndSaySequence {

    static String getNextSequence(final String s) {
        final char[] input = s.toCharArray();

        final StringBuilder output = new StringBuilder();

        int occurances = 1;
        for (int i = 1; i < input.length; i++) {
            if (input[i] == input[i-1]) {
                occurances++;
            } else {
                output.append(String.valueOf(occurances));
                output.append(input[i-1]);
                occurances = 1;
            }
        }

        output.append(String.valueOf(occurances));
        output.append(input[input.length-1]);

        return output.toString();
    }

    public static void main(final String[] args) {
        String input = "1";

        for (int i = 0; i < 10; i++) {
            input = getNextSequence(input);
            System.out.println(input);
        }
    }
}
