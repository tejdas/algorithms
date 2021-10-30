package net.lc.stack;

import java.util.Stack;

/**
 * https://leetcode.com/problems/remove-duplicate-letters/
 * 316
 * 1081
 * Stack
 */
public class RemoveDuplicateLetters {
    public String removeDuplicateLetters(String str) {
        /**
         * count of characters. We scan the array and everytime we find
         * a character, we will decrement the count in countArray.
         * So, countArray basically contains a count of unseen characters.
         */
        int[] countArray = new int[26];

        /**
         * Maintain a boolean array to indicate if a character is processed, or it
         * needs to be (re)processed.
         * A character might need to be reprocessed (visited true->false), if we found
         * a smaller character later, but there are still at-least one more instance of
         * the current character later in the String.
         */
        boolean[] visited = new boolean[26];

        char[] array = str.toCharArray();
        for(char c : array){
            countArray[c-'a']++;
        }

        Stack<Character> stack = new Stack<>();

        for (char c : array) {

            int index = c-'a';
            countArray[index]--;
            if(visited[index]) {
                continue;
            }

            /**
             * Current character is not processed (in-stack yet).
             * while the current character is smaller than the top-char in stack, and top-char has more occurrences later,
             * pop the top-char.
             */
            while (!stack.isEmpty() && c < stack.peek() && countArray[stack.peek()-'a'] > 0) {
                char popped = stack.pop();
                visited[popped -'a']= false;
            }

            stack.push(c); //add current character and mark it as visited
            visited[index] = true;
        }

        StringBuilder sb = new StringBuilder();
        while(!stack.isEmpty()){
            sb.insert(0, stack.pop());
        }
        return sb.toString();
    }
}
