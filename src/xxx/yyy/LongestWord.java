package xxx.yyy;

import java.util.Arrays;

public class LongestWord {

    public static void main(String[] args) {
        String[] array = new String[] { "cat", "banana", "dog", "nana", "walk", "walker", "dogwalker" };

        Arrays.sort(array);

        for (String s : array) {
            System.out.println(s);
        }
    }

    static String findLongestWord(String[] array) {
        return null;
    }
}
