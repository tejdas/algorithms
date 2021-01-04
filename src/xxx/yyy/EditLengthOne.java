package xxx.yyy;

/**
 * Determines if two string are different by just one character,
 * that is: one string can be converted to another string by simply
 * adding, removing or editing ONE character.
 *
 */
public class EditLengthOne {

    public static void main(final String[] args) {
        System.out.println(isEditLengthOne("ABCD", "ABEF"));
        System.out.println(isEditLengthOne("ABCD", "ABED"));
        System.out.println(isEditLengthOne("ABCD", "ABCD"));

        System.out.println(isEditLengthOne("ABCD", "ABCDE"));
        System.out.println(isEditLengthOne("ABCD", "ABCDEF"));
        System.out.println(isEditLengthOne("ABCD", "ABEDF"));
        System.out.println(isEditLengthOne("ABCD", "FABCD"));
    }

    static boolean isEditLengthOne(final String s1, final String s2) {
        final int len1 = s1.length();
        final int len2 = s2.length();

        if (len1 == len2) {
            return isEditLengthOneEqualString(s1, s2);
        } else if (Math.abs(len1-len2) > 1) {
            return false;
        } else {
            if (len1 > len2) {
                return isEditLengthOneUnequalString(s1, s2);
            } else {
                return isEditLengthOneUnequalString(s2, s1);
            }
        }
    }

    static boolean isEditLengthOneEqualString(final String s1, final String s2) {
        final char[] s1array = s1.toCharArray();
        final char[] s2array = s2.toCharArray();

        boolean foundDifferentCharacter = false;

        for (int i = 0; i < s1array.length; i++) {
            if (s1array[i] != s2array[i]) {
                if (foundDifferentCharacter) {
                    return false;
                } else {
                    foundDifferentCharacter = true;
                }
            }
        }
        return foundDifferentCharacter;
    }

    /*
     * s1 > s2
     */
    static boolean isEditLengthOneUnequalString(final String s1, final String s2) {
        final char[] s1array = s1.toCharArray();
        final char[] s2array = s2.toCharArray();

        boolean foundDifferentCharacter = false;

        int iter1 = 0;
        int iter2 = 0;

        while (iter1 < s1array.length && iter2 < s2array.length) {
            if (s1array[iter1] == s2array[iter2]) {
                iter1++;
                iter2++;
                continue;
            } else {
                if (foundDifferentCharacter) {
                    return false;
                } else {
                    foundDifferentCharacter = true;
                    iter1++;
                }
            }
        }
        return true;
    }
}
