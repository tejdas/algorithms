package net.lc.design;

import java.util.*;

/**
 * 381
 * Insert Delete GetRandom O(1) Duplicated allowed
 */
public class RandomizedCollection {
    private int size = 0;
    private List<Integer> list = new ArrayList<>();
    private final Random r = new Random();

    private final Map<Integer, Pos> map = new HashMap<>();

    static class Pos {
        int size;
        List<Integer> posList = new ArrayList<>();
        Map<Integer, Integer> posMap = new HashMap<>();

        public void addPos(int pos) {
            int curIndex = size;
            if (posList.size() > size) {
                posList.set(curIndex, pos);
            } else {
                posList.add(pos);
            }
            size++;

            posMap.put(pos, curIndex);
        }

        public void removePos(int pos) {
            int posIndex = posMap.remove(pos);
            if (posIndex < size-1) {
                int valToRelocate = posList.get(size-1);
                posList.set(posIndex, valToRelocate);
                posMap.put(valToRelocate, posIndex);
            }

            size--;
        }

        public int removeLastPos() {
                int valToRemove = posList.get(size-1);
                posMap.remove(valToRemove);
                size--;
                return valToRemove;
        }

        public boolean isEmpty() {return size == 0;}

        public void print() {
            System.out.println("----------------");
            System.out.println("PosList: " + Arrays.toString(posList.toArray()));
            System.out.println("Size: " + size);
            System.out.println("Map keys: " + Arrays.toString(posMap.keySet().toArray()));
        }
    }

    /** Initialize your data structure here. */
    public RandomizedCollection() {

    }

    /** Inserts a value to the collection. Returns true if the collection did not already contain the specified element. */
    public boolean insert(int val) {

        boolean flag = map.containsKey(val);

        int insertIndex = size;

        if (list.size() > size) {
            list.set(insertIndex, val);
        } else {
            list.add(val);
        }
        size++;

        Pos pos = map.computeIfAbsent(val, k -> new Pos());
        pos.addPos(insertIndex);

        return !flag;
    }

    /** Removes a value from the collection. Returns true if the collection contained the specified element. */
    public boolean remove(int val) {
        Pos pos = map.get(val);
        if (pos == null) return false;

        int removeIndex = pos.removeLastPos();
        if (pos.isEmpty()) {
            map.remove(val);
        }

        if (removeIndex < size-1) {
            int origIndex = size-1;
            int valToRelocate = list.get(size-1);
            list.set(removeIndex, valToRelocate);

            Pos relocatedPos = map.get(valToRelocate);
            relocatedPos.removePos(origIndex);
            relocatedPos.addPos(removeIndex);
        }

        size--;
        return true;
    }

    /** Get a random element from the collection. */
    public int getRandom() {
        int randIndex = r.nextInt(size);
        return list.get(randIndex);
    }

    public static void main1(String[] args) {
        Pos pos = new Pos();
        pos.addPos(4);
        pos.addPos(5);
        pos.addPos(3);
        pos.addPos(6);

        pos.print();

        pos.removePos(5);

        pos.print();
        pos.removePos(6);

        pos.print();
        pos.addPos(7);
        pos.addPos(1);
        pos.print();

    }

    public static void main(String[] args) {
        RandomizedCollection rc = new RandomizedCollection();
        /*
        ["RandomizedCollection", "insert", "insert", "insert", "getRandom", "remove", "getRandom"]
[[], [1], [1], [2], [], [1], []]
Output
[null, true, false, true, 2, true, 1]
         */

        System.out.println(rc.insert(1));
        System.out.println(rc.insert(1));
        System.out.println(rc.insert(2));
        System.out.println(rc.getRandom());
        System.out.println(rc.remove(1));
        System.out.println(rc.getRandom());
    }
}
