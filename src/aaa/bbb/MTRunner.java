package aaa.bbb;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MTRunner {
    static class FactorialCalculator implements Runnable {
        private long inputNumber;
        private BigInteger result = BigInteger.ZERO;
        private boolean isFinished = false;

        private final AtomicBoolean finished = new AtomicBoolean(false);

        private final Object lock = new Object();
        private final Lock rlock = new ReentrantLock();

        private final CountDownLatch startLatch;
        private final CountDownLatch endLatch;

        public FactorialCalculator(long inputNumber, CountDownLatch startLatch, CountDownLatch endLatch) {
            this.inputNumber = inputNumber;
            this.startLatch = startLatch;
            this.endLatch = endLatch;
        }

        @Override
        public void run() {
            try {
                startLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("ThreadId of calculator thread: " + Thread.currentThread().getId() + " time: " + System.nanoTime());
            this.result = factorial(inputNumber);
            setFinished(true);
            finished.set(true);

            endLatch.countDown();
        }

        public BigInteger factorial(long n) {
            BigInteger tempResult = BigInteger.ONE;

            for (long i = n; i > 0; i--) {
                tempResult = tempResult.multiply(new BigInteger((Long.toString(i))));
            }
            return tempResult;
        }

        public BigInteger getResult() {
            return result;
        }

        private void setFinished(boolean f) {
            rlock.lock();
            try {
                isFinished = f;
            } finally {
                rlock.unlock();
            }
        }

        public boolean isFinished() {
            rlock.lock();
            try {
                return isFinished;
            } finally {
                rlock.unlock();
            }
        }

        public boolean hasFinished() {
            return finished.get();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        long[] input = {1000, 2000, 3000, 4000, 5000, 6000, 7000 };
        int numThreads = input.length;

        CountDownLatch startLatch = new CountDownLatch(1);
        CountDownLatch endLatch = new CountDownLatch(numThreads);

        List<FactorialCalculator> calculatorList = new ArrayList<>();
        System.out.println("ThreadId of main thread: " + Thread.currentThread().getId() + " time: " + System.nanoTime());

        for (long i : input) {
            FactorialCalculator calculator = new FactorialCalculator(i, startLatch, endLatch);
            calculatorList.add(calculator);
            new Thread(calculator).start();
        }

        System.out.println("Signalling all calculators to start");
        startLatch.countDown();

        System.out.println("Waiting for all calculators to finish");
        endLatch.await(30, TimeUnit.MILLISECONDS);

        for (FactorialCalculator calculator : calculatorList) {
            if (calculator.isFinished()) {
                System.out.println("result : " + calculator.getResult());
            } else {
                System.out.println("not yet finished");
            }
        }

    }
}
