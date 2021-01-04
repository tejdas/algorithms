package xxx.yyy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class RandomRemover<K, V> {
    private final Random r = new Random();
    private final List<K> keysList = new ArrayList<>();
    private final Map<K, V> map = new HashMap<>();
    private final Map<K, Integer> positionMap = new HashMap<>();

    public synchronized void add(K key, V val) {
        map.put(key, val);
        int size = keysList.size();
        keysList.add(key);
        positionMap.put(key,  size);
    }

    public synchronized V removeRandom() {
        if (keysList.isEmpty()) {
            return null;
        }

        int size = keysList.size();
        int randomPos = r.nextInt(size);
        K key = keysList.get(randomPos);
        V value = map.remove(key);
        positionMap.remove(key);
        updatePositionMapAndList(randomPos, size);
        return value;
    }

    public synchronized V remove(K key) {
        if (!map.containsKey(key)) {
            return null;
        }

        V val = map.remove(key);
        int pos = positionMap.remove(key);
        int size = keysList.size();

        updatePositionMapAndList(pos, size);

        return val;
    }

    private void updatePositionMapAndList(int pos, int size) {
        if (pos == size-1) {
            keysList.remove(pos);
        } else {
            K updatedKey = keysList.remove(size-1);
            keysList.set(pos, updatedKey);
            positionMap.put(updatedKey, pos);
        }
    }

    public static void main(String[] args) {
        {
            RandomRemover<String, String> rr = new RandomRemover<>();
            for (int i = 0; i < 50; i++) {
                rr.add("key" + i, "value" + i);
            }

            while (true) {
                String randomVal = rr.removeRandom();
                if (randomVal == null) {
                    break;
                }
                System.out.println(randomVal);
            }
        }

        System.out.println("----------------------");

        {
            List<String> keyList = new ArrayList<>();
            RandomRemover<String, String> rr = new RandomRemover<>();
            for (int i = 0; i < 50; i++) {
                rr.add("key" + i, "value" + i);
                keyList.add("key" + i);
            }

            Random r = new Random();
            while (!keyList.isEmpty()) {
                String key = keyList.remove(r.nextInt(keyList.size()));
                String val = rr.remove(key);
                System.out.println(val);
            }
        }
    }
}
