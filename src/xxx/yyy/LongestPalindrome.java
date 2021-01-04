package xxx.yyy;

import java.util.Stack;

public class LongestPalindrome {
    static class Entry {
        public Entry(char c, int pos) {
            this.c = c;
            this.pos = pos;
        }

        final char c;

        final int pos;
    }

    static boolean isPalindrome(char[] array) {
        int i = 0;
        int j = array.length-1;

        while (i < j) {
            if (array[i] != array[j]) {
                return false;
            }
            i++;
            j--;
        }

        return true;
    }

    public String longestPalindrome(String s) {
        Stack<Entry> stack = new Stack<>();
        char[] array = s.toCharArray();

        if (isPalindrome(array)) {
            return s;
        }

        int minPos = 0;
        int maxPos = 0;
        boolean inProgress = false;
        int index = 0;

        while (index < array.length) {
            char c = array[index];
            if (stack.isEmpty()) {
                for (int j = 0; j < index; j++) {
                    stack.push(new Entry(array[j], j));
                }
                stack.push(new Entry(c, index));
                index++;
                continue;
            }

            if (inProgress) {
                if (c == stack.peek().c) {
                    Entry e = stack.pop();
                    if ((index - e.pos) > maxPos - minPos) {
                        minPos = e.pos;
                        maxPos = index;
                    }
                    index++;
                    continue;
                } else {
                    inProgress = false;
                    stack.clear();
                    for (int j = 0; j < index; j++) {
                        stack.push(new Entry(array[j], j));
                    }
                    continue;
                }
            }

            // inProgress = false
            if (c == stack.peek().c) {
                Entry e = stack.pop();
                if ((index - e.pos) > maxPos - minPos) {
                    minPos = e.pos;
                    maxPos = index;
                }
                inProgress = true;
                index++;
                continue;
            }

            if (stack.size() > 1) {
                Entry top = stack.pop();
                if (c == stack.peek().c) {
                    Entry e = stack.pop();

                    if ((index - e.pos) > maxPos - minPos) {
                        minPos = e.pos;
                        maxPos = index;
                    }
                    inProgress = true;
                    index++;
                    continue;
                } else {
                    stack.push(top);
                }
            }

            stack.push(new Entry(c, index));
            index++;
        }
        //System.out.println(minPos + "  " + maxPos);
        //System.out.println(s.substring(minPos, maxPos + 1));
        return s.substring(minPos, maxPos + 1);
    }

    public static void main(String[] args) {
        // findLongestPalindrome("abcdossodskjmalayyalamdefabcddcbasq");
        // findLongestPalindrome("malayalam");
        LongestPalindrome lp = new LongestPalindrome();
        System.out.println(lp.longestPalindrome("abcdossodskjmalayalamdefabcddcbasq"));
        System.out.println(lp.longestPalindrome("mnababcbaabcbaxyz"));
        System.out.println(lp.longestPalindrome("ababbaba"));
        //findLongestPalindrome("ababarttrabbabbar");
        //findLongestPalindrome("cdabcbarttrabmn");
        System.out.println(lp.longestPalindrome("babcbabcbaccba"));
        System.out.println(lp.longestPalindrome("abaccddccefe"));
        System.out.println(lp.longestPalindrome("HYTBCABADEFGHABCDEDCBAGHTFYW1234567887654321ZWETYGDE"));
        System.out.println(lp.longestPalindrome("cccc"));
    }
}
