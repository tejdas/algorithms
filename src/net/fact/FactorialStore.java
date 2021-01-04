package net.fact;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class FactorialStore {

    private Map<Long, BigInteger> valueMap = new HashMap<>();
    private Map<Long, Integer> countMap = new HashMap<>();

    private final Lock lock = new ReentrantLock();

    public void storeValue(long factorialInput, BigInteger value) {
        lock.lock();
        try {
            valueMap.put(factorialInput, value);
            if (countMap.containsKey(factorialInput)) {
                countMap.put(factorialInput, 1 + countMap.get(factorialInput));
            } else {
                countMap.put(factorialInput, 1);
            }
        } finally {
            lock.unlock();
        }
    }

    public BigInteger getValue(long factorialInput) {
        lock.lock();
        try {
            if (valueMap.containsKey(factorialInput)) {
                return valueMap.get(factorialInput);
            } else {
                return BigInteger.ZERO;
            }
        } finally {
            lock.unlock();
        }
    }

    public int getExecutionCount(long factorialInput) {
        lock.lock();

        try {
            if (countMap.containsKey(factorialInput)) {
                return countMap.get(factorialInput);
            } else {
                return 0;
            }
        } finally {
            lock.unlock();
        }
    }

    public void printInventory() {
        lock.lock();
        try {
            for (long key : countMap.keySet()) {
                System.out.print(key + " ");
            }
            System.out.println();
        } finally {
            lock.unlock();
        }
    }
}
