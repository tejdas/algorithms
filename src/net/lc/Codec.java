package net.lc;

import java.util.ArrayList;
import java.util.List;

/**
 * 271
 */
public class Codec {
    // Encodes a list of strings to a single string.
    public String encode(List<String> strs) {
        StringBuilder sb = new StringBuilder();
        sb.append(intToString(strs.size()));

        for (String str : strs) {
            sb.append(intToString(str.length()));
            sb.append(str);
        }
        return sb.toString();
    }

    // Decodes a single string to a list of strings.
    public List<String> decode(String s) {
        List<String> result = new ArrayList<>();

        char[] array = s.toCharArray();

        int index = 0;
        int listLen = stringToInt(array, index);
        index += 4;

        while (index < array.length) {
            int len = stringToInt(array, index);
            index += 4;
            String ss = String.valueOf(array, index, len);
            //System.out.println("str: " + ss);
            result.add(ss);
            index += len;
        }

        return result;
    }

    static String intToString(int slen) {
        char[] bytes = new char[4];
        bytes[0] = (char) (slen >> 24 & 0xff);
        bytes[1] = (char) (slen >> 16 & 0xff);
        bytes[2] = (char) (slen >> 8 & 0xff);
        bytes[3] = (char) (slen & 0xff);
        return new String(bytes);
    }

    static int stringToInt(char[] array, int offset) {
        int result = 0;

        for (int i = offset; i < offset+4; i++) {
            char b = array[i];
            result = (result << 8) + (int)b;
        }
        return result;
    }
}
