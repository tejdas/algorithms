package net.lc;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * https://leetcode.com/problems/maximum-frequency-stack/submissions/
 * Stack
 * Map
 */
public class FreqStack {

    int curMaxFreq = 0;
    Map<Integer, Integer> freqMap = new HashMap<>();
    private final Map<Integer, Stack<Integer>> freqStackMap = new HashMap<>();
    public FreqStack() {
    }

    public void push(int x) {
        if (!freqMap.containsKey(x)) {
            int xFreq = 1;
            freqMap.put(x, xFreq);
            Stack<Integer> stack = freqStackMap.computeIfAbsent(xFreq, k -> new Stack<>());
            stack.push(x);

            curMaxFreq = Math.max(curMaxFreq, xFreq);
        } else {
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
