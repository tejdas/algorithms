package xxx.yyy;

import java.util.Arrays;

public class BitWise {
    public static void main(String[] args) {
        removeDups("Tejeswar".toCharArray());
        removeDups("Aryaman".toCharArray());
        removeDups("ABBBBCCDDBBNNPPQ".toCharArray());
        removeDups("ababababa".toCharArray());
        removeDups("aaaaaabbb".toCharArray());
        removeDups("bbbbbb".toCharArray());
        removeDups("".toCharArray());
    }

    static void removeDups(char[] name) {
        boolean[] flag = new boolean[256];
        Arrays.fill(flag, false);

        int cur = 0;
        int pos = 0;
        while (pos < name.length) {
            char val = name[pos];
            if (!flag[val]) {
                flag[val] = true;
                name[cur++] = val;
            }
            pos++;
        }

        while (cur < name.length) {
            name[cur++] = '\0';
        }
        System.out.println(new String(name));
    }
}
