package net.lc;

import java.util.*;

/**
 * https://leetcode.com/problems/snapshot-array/
 */
public class SnapshotArray {
    private int nextSnapshotId = 0;

    static class Info {
        final List<Integer> ssIdList = new ArrayList<>();
        final List<Integer> values = new ArrayList<>();
    }

    private final Map<Integer, Info> map = new HashMap<>();
    private final Map<Integer, Integer> delta = new HashMap<>();

    public SnapshotArray(int length) {
    }

    public void set(int index, int val) {
        delta.put(index, val);
    }

    public int snap() {
        int curSnapshot = nextSnapshotId;

        for (Map.Entry<Integer, Integer> entry : delta.entrySet()) {
            int index = entry.getKey();
            Info info = map.get(index);
            if (info == null) {
                info = new Info();
                map.put(index, info);
            }
            info.ssIdList.add(curSnapshot);
            info.values.add(entry.getValue());
        }
        delta.clear();
        nextSnapshotId++;
        return curSnapshot;
    }

    public int get(int index, int snapId) {
        if (!map.containsKey(index)) {
            return 0;
        }

        Info info = map.get(index);
        int snapIndex = findValueLessThanOrEq(info.ssIdList, snapId);
        if (snapIndex == -1) return 0;
        return info.values.get(snapIndex);
    }

    int findValueLessThanOrEq(List<Integer> list, int ssId) {

        if (list.isEmpty()) return -1;

        int left = 0;
        int right = list.size()-1;

        if (ssId < list.get(left)) return -1;
        if (ssId == list.get(left)) return left;
        if (ssId >= list.get(right)) return right;

        while (left < right) {
            if (left == right-1) {
                if (ssId == list.get(right)) return right; else return left;
            }

            int mid = left + (right-left)/2;

            if (ssId == list.get(mid)) return mid;

            if (ssId > list.get(mid)) {
                if (mid+1 <= right && ssId < list.get(mid+1)) {
                    return mid;
                } else {
                    left = mid + 1;
                }
            } else {
                if (mid-1 >= left && ssId > list.get(mid-1)) {
                    return mid-1;
                } else
                    right = mid-1;
            }
        }

        if (left == right) return left;
        return -1;
    }

    public static void main(String[] args) {
        SnapshotArray ssa = new SnapshotArray(10000);

        //List<Integer> input = Arrays.asList(1, 2, 5, 7, 9, 10, 12);
        List<Integer> input = Arrays.asList(0,1,2,3,4,5);
        System.out.println(ssa.findValueLessThanOrEq(input, 3));

        ssa.set(0, 8445);
        ssa.snap();
        System.out.println(ssa.get(0,0));

        ssa.set(0, 4206);
        ssa.snap();
        System.out.println(ssa.get(0,0));

        ssa.set(0, 5113);
        ssa.snap();
        System.out.println(ssa.get(0,1));

        ssa.set(0, 7838);
        ssa.snap();
        System.out.println(ssa.get(0,1));

        ssa.set(0, 4766);
        ssa.snap();
        System.out.println(ssa.get(0,2));

        ssa.set(0, 9082);
        ssa.snap();
        System.out.println(ssa.get(0,3));

        ssa.set(0, 2818);
        ssa.snap();
        //System.out.println(ssa.get(0,5));
    }
}
