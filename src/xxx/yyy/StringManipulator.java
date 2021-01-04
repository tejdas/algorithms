package xxx.yyy;

public class StringManipulator {
    private static String replaceEmptySpace(final String input, final String replace) {
        final char[] inputVal = input.toCharArray();
        final char[] replaceArray = replace.toCharArray();
        int numSpaces = 0;
        for (final char c : inputVal) {
            if (c == ' ') {
                numSpaces++;
            }
        }

        final int lenOfReplace = replaceArray.length;
        final int newLen = inputVal.length + (numSpaces* (lenOfReplace-1));
        final char[] outputVal = new char[newLen];
        int outputIndex = newLen-1;
        for (int inputIndex = inputVal.length-1; inputIndex >= 0; inputIndex--) {
            final char inputC = inputVal[inputIndex];
            if (inputC != ' ') {
                outputVal[outputIndex--] = inputC;
            } else {
            	for (int i = lenOfReplace-1; i >= 0; i--) {
            		outputVal[outputIndex--] = replaceArray[i];
            	}
            }
        }
        return new String(outputVal);
    }

    public static void main(final String[] args) {
        final String out = replaceEmptySpace("My name is  Aryaman", "SARADA");
        System.out.println(out);
    }
}
