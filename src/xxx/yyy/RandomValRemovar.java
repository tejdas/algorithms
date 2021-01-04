package xxx.yyy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class RandomValRemovar<V> {
    private final Random r = new Random();
    private final List<V> values = new ArrayList<>();
    private final Map<V, Integer> positionMap = new HashMap<>();

    public synchronized void add(final V val) {
        final int size = values.size();
        values.add(val);
        positionMap.put(val,  size);
    }

    public synchronized V removeValue(final V value) {
        if (values.isEmpty()) {
            return null;
        }

        final int size = values.size();
        final Integer pos = positionMap.remove(value);
        if (pos == null) {
            return null;
        }
        if (pos == size-1) {
            values.remove(pos);
        } else {
            final V updatedKey = values.remove(size-1);
            values.set(pos, updatedKey);
            positionMap.put(updatedKey, pos);
        }
        return value;
    }

    public synchronized V removeRandom() {
        if (values.isEmpty()) {
            return null;
        }

        final int size = values.size();
        final int randomPos = r.nextInt(size);
        final V value = values.get(randomPos);
        positionMap.remove(value);
        if (randomPos == size-1) {
            values.remove(randomPos);
        } else {
            final V updatedKey = values.remove(size-1);
            values.set(randomPos, updatedKey);
            positionMap.put(updatedKey, randomPos);
        }
        return value;
    }

    public static void main(final String[] args) {
        final RandomValRemovar<String> rr = new RandomValRemovar<>();
        for (int i = 0; i < 50; i++) {
            rr.add("value"+i);
        }

        while (true) {
            final String randomVal = rr.removeRandom();
            if (randomVal == null) {
                break;
            }
            System.out.println(randomVal);
        }
    }
}

