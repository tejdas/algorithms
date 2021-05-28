package net.lc.stack;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * https://leetcode.com/problems/maximum-frequency-stack/submissions/
 * Stack
 * Map
 */
public class MaximumFreqStack {

    /**
     * keep track of the highest frequency.
     */
    int curMaxFreq = 0;
    /**
     * Element -> Frequency map
     */
    Map<Integer, Integer> freqMap = new HashMap<>();
    /**
     * Key is frequency
     * Frequency -> stack of all elements of that frequency.
     */
    private final Map<Integer, Stack<Integer>> freqStackMap = new HashMap<>();
    public MaximumFreqStack() {
    }

    public void push(int x) {
        if (!freqMap.containsKey(x)) {
            int xFreq = 1;
            freqMap.put(x, xFreq);
            Stack<Integer> stack = freqStackMap.computeIfAbsent(xFreq, k -> new Stack<>());
            stack.push(x);

            curMaxFreq = Math.max(curMaxFreq, xFreq);
        } else {
            /**
             * increment x's frequency, and push it to stack of higher frequency.
             * Do NOT remove x from lower frequency stack.
             */
            freqMap.put(x, 1 + freqMap.get(x));
            int xFreq = freqMap.get(x);
            Stack<Integer> stack = freqStackMap.computeIfAbsent(xFreq, k -> new Stack<>());
            stack.push(x);
            curMaxFreq = Math.max(curMaxFreq, xFreq);
        }
    }

    public int pop() {
        Stack<Integer> stack = freqStackMap.get(curMaxFreq);

        int toReturn = stack.pop();
        if (stack.isEmpty()) {
            freqStackMap.remove(curMaxFreq);
            curMaxFreq--;
        }

        if (freqMap.get(toReturn) > 1) {
            freqMap.put(toReturn, freqMap.get(toReturn) - 1);
        } else {
            freqMap.remove(toReturn);
        }

        return toReturn;
    }
}
