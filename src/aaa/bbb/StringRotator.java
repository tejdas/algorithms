package aaa.bbb;

public class StringRotator {

    private static final int LEFT_BOUNDARY = (int) 'A';
    private static final int RIGHT_BOUNDARY = (int) 'Z';

    private static char rotate(char input, int offset) {
        int baseOffset = offset % 26;
        int cval = (int) input;

        cval += baseOffset;
        if (cval > RIGHT_BOUNDARY) {
            int delta = cval - RIGHT_BOUNDARY - 1;
            cval =LEFT_BOUNDARY + delta;
        } else if (cval < LEFT_BOUNDARY) {
            int delta = LEFT_BOUNDARY - cval - 1;
            cval = RIGHT_BOUNDARY - delta;
        }
        return (char) cval;
    }

    public String rotateWord(String input, int offset) {
        char[] array = input.toCharArray();
        StringBuilder sb = new StringBuilder();
        for (char c : array) {
            sb.append(rotate(c, offset));
        }
        return sb.toString();
    }

    public static void main(String[] args) {

        System.out.println(new StringRotator().rotateWord("WORD", 56));
        System.out.println(new StringRotator().rotateWord("WORD", -72));
        System.out.println(new StringRotator().rotateWord("BOSTON", -83));
        System.out.println(new StringRotator().rotateWord("BOSTON", 26));
    }

    public static void main2(String[] args) {
        for (char c = 'A'; c <= 'Z'; c++) {
            System.out.print(rotate(c, 7));
            System.out.print("   ");
        }
        System.out.println();

        for (char c = 'A'; c <= 'Z'; c++) {
            System.out.print(rotate(c, -7));
            System.out.print("   ");
        }
        System.out.println();
    }
}
