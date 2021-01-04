package algo.recurse;

public class StringReverser {
    private static void reverse(char[] array, int x, int y) {
        if (x >= y) {
            return;
        }
        char temp = array[x];
        array[x] = array[y];
        array[y] = temp;
        reverse(array, x+1, y-1);
    }
    public static void main(String[] args) {
        String input = "Tejeswar Das";
        char[] inputChars = input.toCharArray();
        reverse(inputChars, 0, inputChars.length-1);
        String output = new String(inputChars);
        System.out.println(output);
    }
}
