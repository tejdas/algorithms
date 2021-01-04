package xxx.yyy;

import java.util.HashSet;
import java.util.Set;

public class CircularArrayOfRelIndices {
    public boolean containsCycle(int[] array) {
        int length = array.length;

        Set<Integer> visited = new HashSet<>();

        int curPos = 0;
        visited.add(0);

        while (true) {
            int val = array[curPos];

            if (val == 0) return false;

            if (val > 0) {
                curPos = getForwardIndex(array, curPos, val);
            } else {
                curPos = getBackwardIndex(array, curPos, val);
            }

            if (curPos == 0) {
                return (visited.size() == length);
            }

            if (visited.contains(curPos)) return false;
        }
    }

    private int getForwardIndex(int[] array, int curIndex, int forwardJump) {
        return (curIndex + forwardJump) % array.length;
    }

    private int getBackwardIndex(int[] array, int curIndex, int backwardJump) {
        int relJump = backwardJump % array.length;
        int index = curIndex - relJump;
        if (index > 0) return index;
        else return index + array.length;
    }
}
